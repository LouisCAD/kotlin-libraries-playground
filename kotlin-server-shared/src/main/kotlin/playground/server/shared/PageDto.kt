package playground.server.shared

/**
 * Dto used to return paginated collections.
 */
data class PageDto<D>(
    var result: List<D> = emptyList(),
    var totalPages: Int,
    var totalElements: Long
)
