package playground.server.springboot.config

import playground.server.springboot.feature.book.BookResource
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
class JerseyConfig() : ResourceConfig() {
    init {
        register(BookResource())
    }
}
