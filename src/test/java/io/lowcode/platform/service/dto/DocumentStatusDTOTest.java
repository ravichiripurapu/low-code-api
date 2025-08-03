package io.lowcode.platform.service.dto;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStatusDTO.class);
        DocumentStatusDTO documentStatusDTO1 = new DocumentStatusDTO();
        documentStatusDTO1.setId("id1");
        DocumentStatusDTO documentStatusDTO2 = new DocumentStatusDTO();
        assertThat(documentStatusDTO1).isNotEqualTo(documentStatusDTO2);
        documentStatusDTO2.setId(documentStatusDTO1.getId());
        assertThat(documentStatusDTO1).isEqualTo(documentStatusDTO2);
        documentStatusDTO2.setId("id2");
        assertThat(documentStatusDTO1).isNotEqualTo(documentStatusDTO2);
        documentStatusDTO1.setId(null);
        assertThat(documentStatusDTO1).isNotEqualTo(documentStatusDTO2);
    }
}
