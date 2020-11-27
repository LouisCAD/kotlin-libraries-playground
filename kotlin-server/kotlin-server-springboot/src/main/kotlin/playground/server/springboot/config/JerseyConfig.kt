package playground.server.springboot.config

import playground.server.springboot.feature.book.BookResource
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component
import playground.server.springboot.feature.author.AuthorResource

@Component
class JerseyConfig(
    val bookResource: BookResource,
    val authorResource: AuthorResource
) : ResourceConfig() {
    init {
        register(bookResource)
        register(authorResource)
    }
}
