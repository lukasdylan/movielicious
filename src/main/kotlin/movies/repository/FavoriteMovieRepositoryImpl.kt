package movies.repository

import movies.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class FavoriteMovieRepositoryImpl : FavoriteMovieRepository {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    override fun getFavoriteMovieList(userId: Long, movieIds: String): ResponseEntity<List<Movie>> {
        val query = Query(Criteria.where("_id").`is`(userId))
        val data: User? = mongoTemplate.find(query, User::class.java).firstOrNull()
        data?.let {
            return when (movieIds.isNotBlank()) {
                true -> {
                    val listOfId = movieIds.split(",").map { it.toLongOrNull() }.toList()
                    ResponseEntity(it.favorite_movies?.filter { it.movie_id in listOfId }.orEmpty(), HttpStatus.OK)
                }
                false -> ResponseEntity(it.favorite_movies.orEmpty(), HttpStatus.OK)
            }
        } ?: kotlin.run {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No User found with id: $userId")
        }
    }

    override fun addFavoriteMovie(addFavoriteMovieRequest: AddFavoriteMovieRequest): ResponseEntity<Map<String, String>> {
        addFavoriteMovieRequest.validateOrException()
        val query = Query(Criteria.where("_id").`is`(addFavoriteMovieRequest.user_id))
        val user = mongoTemplate.find(query, User::class.java).firstOrNull()
        user?.let {
            val isDuplicate = it.checkIfDuplicateMovie(addFavoriteMovieRequest.movie_id)
            if (isDuplicate) {
                throw ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Movie '${addFavoriteMovieRequest.movie_name}' already exist in user favorites"
                )
            } else {
                val update = Update().apply {
                    val movie = with(addFavoriteMovieRequest) {
                        Movie(movie_id, movie_name, movie_image_url)
                    }
                    set("favorite_movies", it.favorite_movies.orEmpty().plus(movie))
                }
                val result = mongoTemplate.updateFirst(query, update, User::class.java)
                return when (result.wasAcknowledged()) {
                    true -> {
                        ResponseEntity(
                            mapOf(
                                Pair(
                                    "message",
                                    "Successfully added for movie: ${addFavoriteMovieRequest.movie_name}"
                                )
                            ), HttpStatus.OK
                        )
                    }
                    false -> throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY)
                }
            }
        } ?: kotlin.run {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "No User found with id: ${addFavoriteMovieRequest.user_id}"
            )
        }
    }

    override fun removeFavoriteMovie(userId: Long, movieId: Long): ResponseEntity<User> {
        throw ResponseStatusException(HttpStatus.NOT_IMPLEMENTED)
    }
}