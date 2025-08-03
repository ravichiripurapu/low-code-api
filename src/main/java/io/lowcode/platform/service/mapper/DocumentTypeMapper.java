package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Document;
import io.lowcode.platform.domain.DocumentType;
import io.lowcode.platform.service.dto.DocumentDTO;
import io.lowcode.platform.service.dto.DocumentTypeDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DocumentType} and its DTO {@link DocumentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentType> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    DocumentTypeDTO toDto(DocumentType s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
