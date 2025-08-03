package io.lowcode.platform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentStatusAsserts.assertDocumentStatusAllPropertiesEquals;
import static io.lowcode.platform.domain.DocumentStatusTestSamples.getDocumentStatusSample1;

class DocumentStatusMapperTest {

    private DocumentStatusMapper documentStatusMapper;

    @BeforeEach
    void setUp() {
        documentStatusMapper = new DocumentStatusMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentStatusSample1();
        var actual = documentStatusMapper.toEntity(documentStatusMapper.toDto(expected));
        assertDocumentStatusAllPropertiesEquals(expected, actual);
    }
}
