package movies.service

import movies.model.AddFavoriteMovieRequest
import movies.model.Movie
import movies.model.User
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val GET_ALL_FAVORITE_MOVIES_ENDPOINT = "/get-fav-movies"
private const val ADD_FAVORITE_MOVIE_ENDPOINT = "/add-fav-movie"
private const val DELETE_FAVORITE_MOVIE_ENDPOINT = "/delete-fav-movie"

interface FavoriteMovieService {
    @GetMapping("$GET_ALL_FAVORITE_MOVIES_ENDPOINT/{userId}")
    fun loadAllFavoriteMovie(
        @RequestHeader httpHeaders: HttpHeaders,
        @PathVariable("userId") userId: Long
    ): ResponseEntity<List<Movie>>

    @GetMapping("$GET_ALL_FAVORITE_MOVIES_ENDPOINT/{userId}/{movieIds}")
    fun loadAllFavoriteMovieByIds(
        @RequestHeader httpHeaders: HttpHeaders,
        @PathVariable("userId") userId: Long,
        @PathVariable("movieIds") movieIds: String
    ): ResponseEntity<List<Movie>>

    @PostMapping(ADD_FAVORITE_MOVIE_ENDPOINT)
    fun saveFavoriteMovie(
        @RequestHeader httpHeaders: HttpHeaders,
        @RequestBody addFavoriteMovieRequest: AddFavoriteMovieRequest
    ): ResponseEntity<Map<String, String>>

    @PostMapping(DELETE_FAVORITE_MOVIE_ENDPOINT)
    fun deleteFavoriteMovie(
        @RequestHeader httpHeaders: HttpHeaders,
        @RequestBody movieId: Long
    ): ResponseEntity<User>
}