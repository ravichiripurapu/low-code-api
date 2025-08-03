package io.lowcode.platform.service.dto;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentMetadataVersionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentMetadataVersionDTO.class);
        DocumentMetadataVersionDTO documentMetadataVersionDTO1 = new DocumentMetadataVersionDTO();
        documentMetadataVersionDTO1.setId("id1");
        DocumentMetadataVersionDTO documentMetadataVersionDTO2 = new DocumentMetadataVersionDTO();
        assertThat(documentMetadataVersionDTO1).isNotEqualTo(documentMetadataVersionDTO2);
        documentMetadataVersionDTO2.setId(documentMetadataVersionDTO1.getId());
        assertThat(documentMetadataVersionDTO1).isEqualTo(documentMetadataVersionDTO2);
        documentMetadataVersionDTO2.setId("id2");
        assertThat(documentMetadataVersionDTO1).isNotEqualTo(documentMetadataVersionDTO2);
        documentMetadataVersionDTO1.setId(null);
        assertThat(documentMetadataVersionDTO1).isNotEqualTo(documentMetadataVersionDTO2);
    }
}
