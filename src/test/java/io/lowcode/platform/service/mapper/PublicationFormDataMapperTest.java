package io.lowcode.platform.service.mapper;

import static io.lowcode.platform.domain.PublicationFormDataAsserts.*;
import static io.lowcode.platform.domain.PublicationFormDataTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicationFormDataMapperTest {

    private PublicationFormDataMapper publicationFormDataMapper;

    @BeforeEach
    void setUp() {
        publicationFormDataMapper = new PublicationFormDataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPublicationFormDataSample1();
        var actual = publicationFormDataMapper.toEntity(publicationFormDataMapper.toDto(expected));
        assertPublicationFormDataAllPropertiesEquals(expected, actual);
    }
}
