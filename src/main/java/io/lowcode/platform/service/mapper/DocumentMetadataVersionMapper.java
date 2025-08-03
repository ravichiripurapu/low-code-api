package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.DocumentMetadata;
import io.lowcode.platform.domain.DocumentMetadataVersion;
import io.lowcode.platform.service.dto.DocumentMetadataDTO;
import io.lowcode.platform.service.dto.DocumentMetadataVersionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DocumentMetadataVersion} and its DTO {@link DocumentMetadataVersionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMetadataVersionMapper extends EntityMapper<DocumentMetadataVersionDTO, DocumentMetadataVersion> {
    @Mapping(target = "documentMetadata", source = "documentMetadata", qualifiedByName = "documentMetadataId")
    DocumentMetadataVersionDTO toDto(DocumentMetadataVersion s);

    @Named("documentMetadataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentMetadataDTO toDtoDocumentMetadataId(DocumentMetadata documentMetadata);
}
