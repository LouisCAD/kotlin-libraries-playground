package playground.server.springboot.util.rest

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import javax.ws.rs.QueryParam

/**
 * BeanParam that stores pagination info.
 */
data class PaginationDto(
        @QueryParam("page")
        var page: Int = 0,
        @QueryParam("size")
        var size: Int = 10,
        @QueryParam("sort")
        var sort: String? = null
) {
    fun toPageable(maxSize: Int = 10) : Pageable {
        // Make sure page is greater than zero.
        val checkedPage = Math.max(0, page)
        // Make sure size is between one and maxSize.
        val checkedSize = if (size in 1..maxSize) size else maxSize
        // Make sure sort is not null.
        val checkedSort = sort ?: ""
        // Convert to Sort object.
        val pageSort = when {
            checkedSort.startsWith("+") -> Sort.by(Sort.Direction.ASC, checkedSort.substring(1..checkedSort.length))
            checkedSort.startsWith("-") -> Sort.by(Sort.Direction.DESC, checkedSort.substring(1..checkedSort.length))
            !checkedSort.isEmpty() -> Sort.by(Sort.Direction.ASC, sort)
            else -> Sort.unsorted()
        }

        return PageRequest.of(checkedPage, checkedSize, pageSort)
    }
}
