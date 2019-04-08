package movies.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@TypeAlias("User")
@Document(collection = "movie")
data class User(@Id var id: Long?,
                var email: String?,
                var full_name: String?,
                var phone_number: String?,
                var favorite_movies: List<Movie>? = emptyList())

fun User.checkIfDuplicateMovie(movieId: Long?): Boolean = favorite_movies?.find { it.movie_id == movieId } != null