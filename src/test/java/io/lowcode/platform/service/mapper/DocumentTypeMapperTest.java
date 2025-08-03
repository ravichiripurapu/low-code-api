package io.lowcode.platform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentTypeAsserts.assertDocumentTypeAllPropertiesEquals;
import static io.lowcode.platform.domain.DocumentTypeTestSamples.getDocumentTypeSample1;

class DocumentTypeMapperTest {

    private DocumentTypeMapper documentTypeMapper;

    @BeforeEach
    void setUp() {
        documentTypeMapper = new DocumentTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentTypeSample1();
        var actual = documentTypeMapper.toEntity(documentTypeMapper.toDto(expected));
        assertDocumentTypeAllPropertiesEquals(expected, actual);
    }
}
