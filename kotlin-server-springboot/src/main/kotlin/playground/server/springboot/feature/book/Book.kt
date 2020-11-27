package playground.server.springboot.feature.book

import playground.server.springboot.feature.author.Author
import playground.server.springboot.util.rest.IDtoConverter
import playground.server.shared.BookDto
import playground.server.shared.IBook
import playground.server.shared.toBookDto
import java.time.LocalDate
import javax.persistence.*

/**
 * JPA Book entity. All parameters have a default value because Hibernate need an empty constructor.
 */
@Entity
@Table(name = "book")
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var title: String? = null,
    override var publication: LocalDate? = null,
    @ManyToOne
    override var author: Author? = null
): IBook<Author>

object BookDtoConverter : IDtoConverter<Book, BookDto> {
    override fun convert(entity: Book): BookDto = entity.toBookDto()
}
