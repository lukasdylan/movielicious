package movies

import movies.model.User
import movies.repository.FavoriteMovieRepositoryImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableAutoConfiguration
class App: CommandLineRunner {

    @Autowired
    lateinit var favoriteMovieRepositoryImpl: FavoriteMovieRepositoryImpl

    override fun run(vararg args: String?) {
        val data = favoriteMovieRepositoryImpl.mongoTemplate.findAll(User::class.java)
        if (data.isNullOrEmpty()) {
            createDummyUser()
        }
    }

    private fun createDummyUser() {
        val user = User(1,"dee@gmail.com", "Dylan", "081388648675", emptyList())
        favoriteMovieRepositoryImpl.mongoTemplate.insert(user)
    }
}

fun main(vararg args: String) {
    SpringApplication.run(App::class.java, *args)
}