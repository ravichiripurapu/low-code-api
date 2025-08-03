package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.PublicationFormData;
import io.lowcode.platform.service.dto.PublicationFormDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicationFormData} and its DTO {@link PublicationFormDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicationFormDataMapper extends EntityMapper<PublicationFormDataDTO, PublicationFormData> {}
