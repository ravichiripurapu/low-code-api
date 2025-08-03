package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Org;
import io.lowcode.platform.domain.Publication;
import io.lowcode.platform.service.dto.OrgDTO;
import io.lowcode.platform.service.dto.PublicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Publication} and its DTO {@link PublicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PublicationMapper extends EntityMapper<PublicationDTO, Publication> {
    @Mapping(target = "org", source = "org", qualifiedByName = "orgId")
    PublicationDTO toDto(Publication s);

    @Named("orgId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrgDTO toDtoOrgId(Org org);
}
