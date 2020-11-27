package playground.server.springboot.util.rest

import org.springframework.data.domain.Page
import playground.server.shared.PageDto

/**
 * Interface for dto converters.
 * @param DATA entity type.
 * @param DTO dto type.
 */
interface IDtoConverter<DATA, DTO> {

    /**
     * Convert entity to dto.
     * @param entity entity to convert.
     */
    fun convert(entity: DATA): DTO

    /**
     * Convert list of entities to list of dtos.
     * @param entities entity list to convert.
     */
    fun convert(entities: List<DATA>): List<DTO> = entities.map { convert(it) }

    /**
     * Convert page of entities to PageDto.
     * @param page page to convert.
     */
    fun convert(page: Page<DATA>): PageDto<DTO> =
        PageDto(convert(page.content), page.totalPages, page.totalElements)
}
