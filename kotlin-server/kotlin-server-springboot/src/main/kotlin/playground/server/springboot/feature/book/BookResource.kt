package playground.server.springboot.feature.book

import playground.server.springboot.util.rest.PaginationDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import playground.server.shared.BookDto
import playground.server.shared.PageDto
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Rest endpoint for books.
 */
@Path("/book")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
class BookResource(
    val bookService: BookService
) {

    @GET
    @Path("/{id}")
    fun getBook(@PathParam("id") id: Long) =
        bookService.getBook(id)

    @GET
    fun getBooks(@BeanParam pagination: PaginationDto): PageDto<BookDto> =
        bookService.getBooks(pagination)

    @POST
    fun createBook(dto: BookDto): Long? =
        bookService.createBook(dto)

    @POST
    @Path("/{id}")
    fun updateBook(@PathParam("id") id: Long, dto: BookDto) =
        bookService.updateBook(id, dto)

    @DELETE
    @Path("/{id}")
    fun deleteBook(@PathParam("id") id: Long) =
        bookService.deleteBook(id)
}
