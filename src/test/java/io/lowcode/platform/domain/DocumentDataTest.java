package io.lowcode.platform.domain;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentDataTestSamples.getDocumentDataSample1;
import static io.lowcode.platform.domain.DocumentDataTestSamples.getDocumentDataSample2;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentData.class);
        DocumentData documentData1 = getDocumentDataSample1();
        DocumentData documentData2 = new DocumentData();
        assertThat(documentData1).isNotEqualTo(documentData2);

        documentData2.setId(documentData1.getId());
        assertThat(documentData1).isEqualTo(documentData2);

        documentData2 = getDocumentDataSample2();
        assertThat(documentData1).isNotEqualTo(documentData2);
    }
}
