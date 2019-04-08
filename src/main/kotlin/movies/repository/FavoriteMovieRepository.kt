package movies.repository

import movies.model.AddFavoriteMovieRequest
import movies.model.Movie
import movies.model.User
import org.springframework.http.ResponseEntity

interface FavoriteMovieRepository {
    fun getFavoriteMovieList(userId: Long, movieIds: String = ""): ResponseEntity<List<Movie>>
    fun addFavoriteMovie(addFavoriteMovieRequest: AddFavoriteMovieRequest): ResponseEntity<Map<String, String>>
    fun removeFavoriteMovie(userId: Long, movieId: Long): ResponseEntity<User>
}