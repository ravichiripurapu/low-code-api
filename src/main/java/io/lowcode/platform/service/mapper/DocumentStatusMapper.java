package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Document;
import io.lowcode.platform.domain.DocumentStatus;
import io.lowcode.platform.service.dto.DocumentDTO;
import io.lowcode.platform.service.dto.DocumentStatusDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DocumentStatus} and its DTO {@link DocumentStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentStatusMapper extends EntityMapper<DocumentStatusDTO, DocumentStatus> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    DocumentStatusDTO toDto(DocumentStatus s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
