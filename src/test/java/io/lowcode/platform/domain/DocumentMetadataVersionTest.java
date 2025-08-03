package io.lowcode.platform.domain;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentMetadataTestSamples.getDocumentMetadataRandomSampleGenerator;
import static io.lowcode.platform.domain.DocumentMetadataVersionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentMetadataVersionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentMetadataVersion.class);
        DocumentMetadataVersion documentMetadataVersion1 = getDocumentMetadataVersionSample1();
        DocumentMetadataVersion documentMetadataVersion2 = new DocumentMetadataVersion();
        assertThat(documentMetadataVersion1).isNotEqualTo(documentMetadataVersion2);

        documentMetadataVersion2.setId(documentMetadataVersion1.getId());
        assertThat(documentMetadataVersion1).isEqualTo(documentMetadataVersion2);

        documentMetadataVersion2 = getDocumentMetadataVersionSample2();
        assertThat(documentMetadataVersion1).isNotEqualTo(documentMetadataVersion2);
    }

    @Test
    void documentMetadataTest() {
        DocumentMetadataVersion documentMetadataVersion = getDocumentMetadataVersionRandomSampleGenerator();
        DocumentMetadata documentMetadataBack = getDocumentMetadataRandomSampleGenerator();

        documentMetadataVersion.setDocumentMetadata(documentMetadataBack);
        assertThat(documentMetadataVersion.getDocumentMetadata()).isEqualTo(documentMetadataBack);

        documentMetadataVersion.documentMetadata(null);
        assertThat(documentMetadataVersion.getDocumentMetadata()).isNull();
    }
}
