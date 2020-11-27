package playground.server.springboot.feature.book

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import playground.server.springboot.feature.book.Book

/**
 * Repository to access books.
 */
@Repository
interface IBookRepository : PagingAndSortingRepository<Book, Long>
