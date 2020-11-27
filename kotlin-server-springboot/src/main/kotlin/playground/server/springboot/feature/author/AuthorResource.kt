package playground.server.springboot.feature.author

import org.springframework.stereotype.Component
import playground.server.shared.AuthorDto
import playground.server.shared.PageDto
import playground.server.springboot.util.rest.PaginationDto
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Rest endpoint for authors.
 */
@Path("/author")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
class AuthorResource(
    val authorService: AuthorService
) {
    @GET
    @Path("/{id}")
    fun getAuthor(@PathParam("id") id: Long) =
        authorService.getAuthor(id)

    @GET
    fun getAuthors(@BeanParam pagination: PaginationDto): PageDto<AuthorDto> =
        authorService.getAuthors(pagination)

    @POST
    fun createAuthor(dto: AuthorDto): Long? =
        authorService.createAuthor(dto)

    @POST
    @Path("/{id}")
    fun updateAuthor(@PathParam("id") id: Long, dto: AuthorDto) =
        authorService.updateAuthor(id, dto)
}
