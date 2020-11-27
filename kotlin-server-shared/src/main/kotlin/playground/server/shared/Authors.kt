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
data class AuthorDto(
    var id: Long? = null,
    var name: String? = null
)

fun IAuthor.toAuthorDto(): AuthorDto =
    AuthorDto(id, name)
