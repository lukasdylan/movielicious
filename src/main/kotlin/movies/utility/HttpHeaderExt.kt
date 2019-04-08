package movies.utility

import org.springframework.http.HttpHeaders

fun HttpHeaders.readAllHeaders() {
    this.toSingleValueMap().forEach { t, u ->
        println("$t $u")
    }
    println()
}