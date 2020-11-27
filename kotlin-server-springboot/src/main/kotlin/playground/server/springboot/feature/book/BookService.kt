package playground.server.springboot.feature.book

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import playground.server.shared.BookDto
import playground.server.shared.PageDto
import playground.server.shared.nullable
import playground.server.shared.toBookDto
import playground.server.springboot.feature.author.IAuthorRepository
import playground.server.springboot.util.rest.PaginationDto
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException
import javax.ws.rs.PathParam

/**
 * Book service class.
 */
@Service
class BookService(
    val authorRepository: IAuthorRepository,
    val bookRepository: IBookRepository
) {


    @Transactional(readOnly = true)
    fun getBook(@PathParam("id") id: Long): BookDto {
        // Find book in repository and throw exception if it was not found.
        val book = bookRepository.findById(id).nullable()
            ?: throw NotFoundException("Book $id does not exist")
        return book.toBookDto()
    }

    @Transactional(readOnly = true)
    fun getBooks(pagination: PaginationDto): PageDto<BookDto> {
        val page = bookRepository.findAll(pagination.toPageable())
        return BookDtoConverter.convert(page)
    }

    @Transactional
    fun createBook(dto: BookDto): Long? {
        // If author id is null throw exception.
        val authorId = dto.authorId ?: throw BadRequestException("Author id must not be null")
        // Find author in database and throw exception if it does not exist.
        val author = authorRepository.findById(authorId).orElse(null)
            ?: throw BadRequestException("Author ${dto.authorId} does not exist")

        // Create book.
        val book = Book(title = dto.title, publication = dto.publication, author = author)
        return bookRepository.save(book).id
    }


    @Transactional
    fun updateBook(@PathParam("id") id: Long, dto: BookDto) {
        // Look for book in database and throw exception if it was not found.
        val book = bookRepository.findById(id).nullable()
            ?: throw NotFoundException()

        // Update book.
        book.title = dto.title
        book.publication = dto.publication

        // Update author if is was changed.
        if (dto.authorId != null && dto.authorId == book.author?.id) {
            val author = authorRepository.findById(dto.authorId!!).nullable()
                ?: throw BadRequestException()
            book.author = author
        }

        bookRepository.save(book)
    }

    @Transactional
    fun deleteBook(@PathParam("id") id: Long) {
        val book = bookRepository.findById(id).orElse(null) ?: return
        bookRepository.delete(book)
    }
}
