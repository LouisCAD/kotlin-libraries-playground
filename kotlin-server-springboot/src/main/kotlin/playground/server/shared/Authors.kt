package playground.server.shared

/**
 * Author stored in the DB
 */
interface IAuthor {
    var id: Long?
    var name: String?
}

/**
 * Author dto that will serialized and sent to clients.
 */
data class AuthorDTO(
    var id: Long? = null,
    var name: String? = null,
    var books: List<BookDto> = emptyList()
)

fun <T: IAuthor> IAuthor.toAuthorDto(books: List<IBook<T>>): AuthorDTO =
    AuthorDTO(id, name, books.map(IBook<T>::toBookDto))
