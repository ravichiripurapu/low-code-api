package io.lowcode.platform.service.dto;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentDataDTO.class);
        DocumentDataDTO documentDataDTO1 = new DocumentDataDTO();
        documentDataDTO1.setId(1L);
        DocumentDataDTO documentDataDTO2 = new DocumentDataDTO();
        assertThat(documentDataDTO1).isNotEqualTo(documentDataDTO2);
        documentDataDTO2.setId(documentDataDTO1.getId());
        assertThat(documentDataDTO1).isEqualTo(documentDataDTO2);
        documentDataDTO2.setId(2L);
        assertThat(documentDataDTO1).isNotEqualTo(documentDataDTO2);
        documentDataDTO1.setId(null);
        assertThat(documentDataDTO1).isNotEqualTo(documentDataDTO2);
    }
}
