package playground.server.springboot.feature.author

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import playground.server.shared.AuthorDto
import playground.server.springboot.feature.book.Book
import playground.server.shared.IAuthor
import playground.server.shared.toAuthorDto
import playground.server.springboot.util.rest.IDtoConverter
import java.util.*
import javax.persistence.*

/**
 * JPA Author entity. All parameters have a default value because Hibernate need an empty constructor.
 */
@Entity
@Table(name = "author")
data class Author(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var name: String? = null,
    @OneToMany(mappedBy = "author")
    var books: List<Book> = emptyList()
): IAuthor

@Repository
interface IAuthorRepository : PagingAndSortingRepository<Author, Long>

object AuthorDtoConverter : IDtoConverter<Author, AuthorDto> {
    override fun convert(entity: Author): AuthorDto =
        entity.toAuthorDto()
}
