package io.lowcode.platform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentMetadataVersionAsserts.assertDocumentMetadataVersionAllPropertiesEquals;
import static io.lowcode.platform.domain.DocumentMetadataVersionTestSamples.getDocumentMetadataVersionSample1;

class DocumentMetadataVersionMapperTest {

    private DocumentMetadataVersionMapper documentMetadataVersionMapper;

    @BeforeEach
    void setUp() {
        documentMetadataVersionMapper = new DocumentMetadataVersionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentMetadataVersionSample1();
        var actual = documentMetadataVersionMapper.toEntity(documentMetadataVersionMapper.toDto(expected));
        assertDocumentMetadataVersionAllPropertiesEquals(expected, actual);
    }
}
