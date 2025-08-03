package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.PublicationForm;
import io.lowcode.platform.domain.PublicationFormVersion;
import io.lowcode.platform.service.dto.PublicationFormDTO;
import io.lowcode.platform.service.dto.PublicationFormVersionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicationFormVersion} and its DTO {@link PublicationFormVersionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicationFormVersionMapper extends EntityMapper<PublicationFormVersionDTO, PublicationFormVersion> {
    @Mapping(target = "publicationForm", source = "publicationForm", qualifiedByName = "publicationFormId")
    PublicationFormVersionDTO toDto(PublicationFormVersion s);

    @Named("publicationFormId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PublicationFormDTO toDtoPublicationFormId(PublicationForm publicationForm);
}
