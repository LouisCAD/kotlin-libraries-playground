package playground.server.springboot.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import playground.server.shared.InitialData
import playground.server.springboot.feature.author.Author
import playground.server.springboot.feature.author.IAuthorRepository
import playground.server.springboot.feature.book.Book
import playground.server.springboot.feature.book.IBookRepository

/**
 * Insert default data to repository.
 */
@Configuration
class DataInitConfig {

    @Bean
    fun init(
        authorRepository: IAuthorRepository,
        bookRepository: IBookRepository
    ) = CommandLineRunner {

        val authorsByName = InitialData.authors.associateWith {
            authorRepository.save(Author(name = it))
        }
        InitialData.books.forEach { book ->
            bookRepository.save(
                Book(
                    title = book.title,
                    publication = book.publication,
                    author = authorsByName[book.authorName] ?: error("Author not found ${book.authorName}")
                )
            )
        }

    }
}
