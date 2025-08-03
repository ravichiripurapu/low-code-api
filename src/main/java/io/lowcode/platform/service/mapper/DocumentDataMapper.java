package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.DocumentData;
import io.lowcode.platform.service.dto.DocumentDataDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link DocumentData} and its DTO {@link DocumentDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentDataMapper extends EntityMapper<DocumentDataDTO, DocumentData> {}
