package playground.server.springboot.feature.author

import org.springframework.stereotype.Service
import playground.server.shared.AuthorDto
import playground.server.shared.PageDto
import playground.server.shared.nullable
import playground.server.shared.toAuthorDto
import playground.server.springboot.util.rest.PaginationDto
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

/**
 * Book service class.
 */
@Service
class AuthorService(
    val authorRepository: IAuthorRepository
) {

    fun getAuthor(id: Long): AuthorDto {
        val author = authorRepository.findById(id).nullable()
            ?: throw NotFoundException()
        return author.toAuthorDto()
    }

    fun getAuthors(pagination: PaginationDto): PageDto<AuthorDto> {
        val page = authorRepository.findAll(pagination.toPageable())
        return AuthorDtoConverter.convert(page)
    }

    fun createAuthor(dto: AuthorDto): Long? {
        val author = Author(dto.id, dto.name)
        return authorRepository.save(author).id
    }

    fun updateAuthor(id: Long, dto: AuthorDto): Author {
        // Look for book in database and throw exception if it was not found.
        val author = authorRepository.findById(id).nullable()
            ?: throw NotFoundException()
        author.name = dto.name
        return authorRepository.save(author)
    }


}
