package io.lowcode.platform.domain;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentStatusTestSamples.*;
import static io.lowcode.platform.domain.DocumentTestSamples.getDocumentRandomSampleGenerator;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStatus.class);
        DocumentStatus documentStatus1 = getDocumentStatusSample1();
        DocumentStatus documentStatus2 = new DocumentStatus();
        assertThat(documentStatus1).isNotEqualTo(documentStatus2);

        documentStatus2.setId(documentStatus1.getId());
        assertThat(documentStatus1).isEqualTo(documentStatus2);

        documentStatus2 = getDocumentStatusSample2();
        assertThat(documentStatus1).isNotEqualTo(documentStatus2);
    }

    @Test
    void documentTest() {
        DocumentStatus documentStatus = getDocumentStatusRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        documentStatus.setDocument(documentBack);
        assertThat(documentStatus.getDocument()).isEqualTo(documentBack);

        documentStatus.document(null);
        assertThat(documentStatus.getDocument()).isNull();
    }
}
