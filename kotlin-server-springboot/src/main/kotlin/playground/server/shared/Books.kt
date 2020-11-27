package playground.server.shared

import java.time.LocalDate

/**
 * Book dto that will serialized and sent to clients.
 */
interface IBook<T: IAuthor> {
    var id: Long?
    var title: String?
    var publication: LocalDate?
    var author: T?
}

/**
 * Book dto that will serialized and sent to clients.
 */
data class BookDto(
    var id: Long? = null,
    var title: String? = null,
    var publication: LocalDate? = null,
    var authorId: Long? = null,
    var authorName: String? = null
)

fun <T: IAuthor> IBook<T>.toBookDto(): BookDto =
    BookDto(id, title, publication, author?.id, author?.name)
