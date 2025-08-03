package io.lowcode.platform.service.dto;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTypeDTO.class);
        DocumentTypeDTO documentTypeDTO1 = new DocumentTypeDTO();
        documentTypeDTO1.setId("id1");
        DocumentTypeDTO documentTypeDTO2 = new DocumentTypeDTO();
        assertThat(documentTypeDTO1).isNotEqualTo(documentTypeDTO2);
        documentTypeDTO2.setId(documentTypeDTO1.getId());
        assertThat(documentTypeDTO1).isEqualTo(documentTypeDTO2);
        documentTypeDTO2.setId("id2");
        assertThat(documentTypeDTO1).isNotEqualTo(documentTypeDTO2);
        documentTypeDTO1.setId(null);
        assertThat(documentTypeDTO1).isNotEqualTo(documentTypeDTO2);
    }
}
