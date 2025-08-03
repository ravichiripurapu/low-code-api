package io.lowcode.platform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentAsserts.assertDocumentAllPropertiesEquals;
import static io.lowcode.platform.domain.DocumentTestSamples.getDocumentSample1;

class DocumentMapperTest {

    private DocumentMapper documentMapper;

    @BeforeEach
    void setUp() {
        documentMapper = new DocumentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentSample1();
        var actual = documentMapper.toEntity(documentMapper.toDto(expected));
        assertDocumentAllPropertiesEquals(expected, actual);
    }
}
