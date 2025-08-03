package io.lowcode.platform.service.mapper;

import static io.lowcode.platform.domain.PublicationFormAsserts.*;
import static io.lowcode.platform.domain.PublicationFormTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicationFormMapperTest {

    private PublicationFormMapper publicationFormMapper;

    @BeforeEach
    void setUp() {
        publicationFormMapper = new PublicationFormMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPublicationFormSample1();
        var actual = publicationFormMapper.toEntity(publicationFormMapper.toDto(expected));
        assertPublicationFormAllPropertiesEquals(expected, actual);
    }
}
