package playground.server.springboot.config

import playground.server.springboot.feature.author.Author
import playground.server.springboot.feature.author.IAuthorRepository
import playground.server.springboot.feature.book.Book
import playground.server.springboot.feature.book.IBookRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

/**
 * Insert default data to repository.
 */
@Configuration
class DataInitConfig {

    @Bean
    fun init(IAuthorRepository: IAuthorRepository, bookRepository: IBookRepository) = CommandLineRunner {
        val stephenKing = Author(name = "Stephen King")
        IAuthorRepository.save(stephenKing)

        val robinHobb = Author(name = "Robin Hobb")
        IAuthorRepository.save(robinHobb)

        bookRepository.save(
            Book(
                title = "22/11/63",
                publication = LocalDate.parse("2011-11-08"),
                author = stephenKing
        )
        )
        bookRepository.save(
            Book(
                title = "L'assassin Royal",
                publication = LocalDate.parse("1998-12-17"),
                author = robinHobb
        )
        )
    }
}
