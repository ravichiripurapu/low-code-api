package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Publication;
import io.lowcode.platform.domain.PublicationType;
import io.lowcode.platform.service.dto.PublicationDTO;
import io.lowcode.platform.service.dto.PublicationTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicationType} and its DTO {@link PublicationTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicationTypeMapper extends EntityMapper<PublicationTypeDTO, PublicationType> {
    @Mapping(target = "publication", source = "publication", qualifiedByName = "publicationId")
    PublicationTypeDTO toDto(PublicationType s);

    @Named("publicationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PublicationDTO toDtoPublicationId(Publication publication);
}
