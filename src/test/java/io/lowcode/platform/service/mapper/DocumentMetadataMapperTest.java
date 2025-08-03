package io.lowcode.platform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentMetadataAsserts.assertDocumentMetadataAllPropertiesEquals;
import static io.lowcode.platform.domain.DocumentMetadataTestSamples.getDocumentMetadataSample1;

class DocumentMetadataMapperTest {

    private DocumentMetadataMapper documentMetadataMapper;

    @BeforeEach
    void setUp() {
        documentMetadataMapper = new DocumentMetadataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentMetadataSample1();
        var actual = documentMetadataMapper.toEntity(documentMetadataMapper.toDto(expected));
        assertDocumentMetadataAllPropertiesEquals(expected, actual);
    }
}
