package movies.utility

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.filter.OncePerRequestFilter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class TimeoutConfig : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val completed = AtomicBoolean(false)
        val requestHandlingThread = Thread.currentThread()
        val timeout = timeoutsPool.schedule({
            if (completed.compareAndSet(false, true)) {
                requestHandlingThread.interrupt()
            }
        }, 15, TimeUnit.SECONDS)

        try {
            filterChain.doFilter(request, response)
            timeout.cancel(false)
        } finally {
            completed.set(true)
        }
    }

    companion object {
        private val timeoutsPool = Executors.newScheduledThreadPool(10)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class TimeoutException(message: String) : java.util.concurrent.TimeoutException(message)
}