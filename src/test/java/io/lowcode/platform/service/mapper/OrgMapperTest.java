package io.lowcode.platform.service.mapper;

import static io.lowcode.platform.domain.OrgAsserts.*;
import static io.lowcode.platform.domain.OrgTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrgMapperTest {

    private OrgMapper orgMapper;

    @BeforeEach
    void setUp() {
        orgMapper = new OrgMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrgSample1();
        var actual = orgMapper.toEntity(orgMapper.toDto(expected));
        assertOrgAllPropertiesEquals(expected, actual);
    }
}
