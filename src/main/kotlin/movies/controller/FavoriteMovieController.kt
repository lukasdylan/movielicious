package movies.controller

import movies.model.AddFavoriteMovieRequest
import movies.model.Movie
import movies.model.User
import movies.repository.FavoriteMovieRepository
import movies.service.FavoriteMovieService
import movies.utility.readAllHeaders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
@Suppress("unused")
class FavoriteMovieController : FavoriteMovieService {

    @Autowired
    lateinit var favoriteMovieRepository: FavoriteMovieRepository

    override fun loadAllFavoriteMovie(httpHeaders: HttpHeaders, userId: Long): ResponseEntity<List<Movie>> {
        httpHeaders.readAllHeaders()
        return favoriteMovieRepository.getFavoriteMovieList(userId)
    }

    override fun loadAllFavoriteMovieByIds(
        httpHeaders: HttpHeaders,
        userId: Long,
        movieIds: String
    ): ResponseEntity<List<Movie>> {
        httpHeaders.readAllHeaders()
        return favoriteMovieRepository.getFavoriteMovieList(userId, movieIds)
    }

    override fun saveFavoriteMovie(
        httpHeaders: HttpHeaders,
        addFavoriteMovieRequest: AddFavoriteMovieRequest
    ): ResponseEntity<Map<String, String>> {
        httpHeaders.readAllHeaders()
        return favoriteMovieRepository.addFavoriteMovie(addFavoriteMovieRequest)
    }

    override fun deleteFavoriteMovie(
        httpHeaders: HttpHeaders,
        movieId: Long
    ): ResponseEntity<User> {
        httpHeaders.readAllHeaders()
        return favoriteMovieRepository.removeFavoriteMovie(1, movieId)
    }
}