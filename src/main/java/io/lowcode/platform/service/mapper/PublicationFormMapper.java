package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Publication;
import io.lowcode.platform.domain.PublicationForm;
import io.lowcode.platform.service.dto.PublicationDTO;
import io.lowcode.platform.service.dto.PublicationFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicationForm} and its DTO {@link PublicationFormDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicationFormMapper extends EntityMapper<PublicationFormDTO, PublicationForm> {
    @Mapping(target = "publication", source = "publication", qualifiedByName = "publicationId")
    PublicationFormDTO toDto(PublicationForm s);

    @Named("publicationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PublicationDTO toDtoPublicationId(Publication publication);
}
