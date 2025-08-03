package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Document;
import io.lowcode.platform.domain.DocumentMetadata;
import io.lowcode.platform.service.dto.DocumentDTO;
import io.lowcode.platform.service.dto.DocumentMetadataDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DocumentMetadata} and its DTO {@link DocumentMetadataDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMetadataMapper extends EntityMapper<DocumentMetadataDTO, DocumentMetadata> {
    @Mapping(target = "document", source = "document", qualifiedByName = "documentId")
    DocumentMetadataDTO toDto(DocumentMetadata s);

    @Named("documentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentDTO toDtoDocumentId(Document document);
}
