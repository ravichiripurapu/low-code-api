package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Publication;
import io.lowcode.platform.domain.PublicationStatus;
import io.lowcode.platform.service.dto.PublicationDTO;
import io.lowcode.platform.service.dto.PublicationStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicationStatus} and its DTO {@link PublicationStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicationStatusMapper extends EntityMapper<PublicationStatusDTO, PublicationStatus> {
    @Mapping(target = "publication", source = "publication", qualifiedByName = "publicationId")
    PublicationStatusDTO toDto(PublicationStatus s);

    @Named("publicationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PublicationDTO toDtoPublicationId(Publication publication);
}
