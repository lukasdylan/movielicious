package movies.model

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

data class AddFavoriteMovieRequest(
    var user_id: Long?,
    var movie_id: Long?,
    var movie_name: String?,
    var movie_image_url: String?
)

fun AddFavoriteMovieRequest.validateOrException() {
    if (user_id == null) {
        throw ResponseStatusException(
            HttpStatus.UNPROCESSABLE_ENTITY,
            "No User found with id: $user_id"
        )
    }
    if (movie_id == null) {
        throw ResponseStatusException(
            HttpStatus.UNPROCESSABLE_ENTITY,
            "Movie ID cannot be null"
        )
    }
    if (movie_name.isNullOrBlank()) {
        movie_name = "No Title"
    }
}