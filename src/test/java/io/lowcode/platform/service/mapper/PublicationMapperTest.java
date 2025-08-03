package io.lowcode.platform.service.mapper;

import static io.lowcode.platform.domain.PublicationAsserts.*;
import static io.lowcode.platform.domain.PublicationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicationMapperTest {

    private PublicationMapper publicationMapper;

    @BeforeEach
    void setUp() {
        publicationMapper = new PublicationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPublicationSample1();
        var actual = publicationMapper.toEntity(publicationMapper.toDto(expected));
        assertPublicationAllPropertiesEquals(expected, actual);
    }
}
