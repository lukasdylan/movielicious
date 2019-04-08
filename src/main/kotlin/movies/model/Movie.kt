package movies.model

import org.springframework.data.annotation.TypeAlias
import java.util.*

@TypeAlias("Movie")
data class Movie(var movie_id: Long?,
                 var movie_name: String?,
                 var movie_image_url: String?,
                 var created_date: Date = Date())