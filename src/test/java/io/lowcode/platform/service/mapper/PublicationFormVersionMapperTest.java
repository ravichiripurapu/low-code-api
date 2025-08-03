package io.lowcode.platform.service.mapper;

import static io.lowcode.platform.domain.PublicationFormVersionAsserts.*;
import static io.lowcode.platform.domain.PublicationFormVersionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicationFormVersionMapperTest {

    private PublicationFormVersionMapper publicationFormVersionMapper;

    @BeforeEach
    void setUp() {
        publicationFormVersionMapper = new PublicationFormVersionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPublicationFormVersionSample1();
        var actual = publicationFormVersionMapper.toEntity(publicationFormVersionMapper.toDto(expected));
        assertPublicationFormVersionAllPropertiesEquals(expected, actual);
    }
}
