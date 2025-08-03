package io.lowcode.platform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentDataAsserts.assertDocumentDataAllPropertiesEquals;
import static io.lowcode.platform.domain.DocumentDataTestSamples.getDocumentDataSample1;

class DocumentDataMapperTest {

    private DocumentDataMapper documentDataMapper;

    @BeforeEach
    void setUp() {
        documentDataMapper = new DocumentDataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentDataSample1();
        var actual = documentDataMapper.toEntity(documentDataMapper.toDto(expected));
        assertDocumentDataAllPropertiesEquals(expected, actual);
    }
}
