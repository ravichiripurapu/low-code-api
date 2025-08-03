package io.lowcode.platform.service.mapper;

import io.lowcode.platform.domain.Org;
import io.lowcode.platform.service.dto.OrgDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Org} and its DTO {@link OrgDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrgMapper extends EntityMapper<OrgDTO, Org> {}
