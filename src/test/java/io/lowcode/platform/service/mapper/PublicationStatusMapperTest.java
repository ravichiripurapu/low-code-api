package io.lowcode.platform.service.mapper;

import static io.lowcode.platform.domain.PublicationStatusAsserts.*;
import static io.lowcode.platform.domain.PublicationStatusTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicationStatusMapperTest {

    private PublicationStatusMapper publicationStatusMapper;

    @BeforeEach
    void setUp() {
        publicationStatusMapper = new PublicationStatusMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPublicationStatusSample1();
        var actual = publicationStatusMapper.toEntity(publicationStatusMapper.toDto(expected));
        assertPublicationStatusAllPropertiesEquals(expected, actual);
    }
}
