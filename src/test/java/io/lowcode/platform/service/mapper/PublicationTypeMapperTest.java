package io.lowcode.platform.service.mapper;

import static io.lowcode.platform.domain.PublicationTypeAsserts.*;
import static io.lowcode.platform.domain.PublicationTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicationTypeMapperTest {

    private PublicationTypeMapper publicationTypeMapper;

    @BeforeEach
    void setUp() {
        publicationTypeMapper = new PublicationTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPublicationTypeSample1();
        var actual = publicationTypeMapper.toEntity(publicationTypeMapper.toDto(expected));
        assertPublicationTypeAllPropertiesEquals(expected, actual);
    }
}
