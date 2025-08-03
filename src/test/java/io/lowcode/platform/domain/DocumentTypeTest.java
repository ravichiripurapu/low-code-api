package io.lowcode.platform.domain;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentTestSamples.getDocumentRandomSampleGenerator;
import static io.lowcode.platform.domain.DocumentTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentType.class);
        DocumentType documentType1 = getDocumentTypeSample1();
        DocumentType documentType2 = new DocumentType();
        assertThat(documentType1).isNotEqualTo(documentType2);

        documentType2.setId(documentType1.getId());
        assertThat(documentType1).isEqualTo(documentType2);

        documentType2 = getDocumentTypeSample2();
        assertThat(documentType1).isNotEqualTo(documentType2);
    }

    @Test
    void documentTest() {
        DocumentType documentType = getDocumentTypeRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        documentType.setDocument(documentBack);
        assertThat(documentType.getDocument()).isEqualTo(documentBack);

        documentType.document(null);
        assertThat(documentType.getDocument()).isNull();
    }
}
