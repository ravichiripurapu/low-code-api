package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Document;
import io.lowcode.platform.domain.Org;
import io.lowcode.platform.service.dto.DocumentDTO;
import io.lowcode.platform.service.dto.OrgDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "org", source = "org", qualifiedByName = "orgId")
    DocumentDTO toDto(Document s);

    @Named("orgId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrgDTO toDtoOrgId(Org org);
}
