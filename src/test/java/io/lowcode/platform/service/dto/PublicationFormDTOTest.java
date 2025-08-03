package io.lowcode.platform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationFormDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationFormDTO.class);
        PublicationFormDTO publicationFormDTO1 = new PublicationFormDTO();
        publicationFormDTO1.setId(1L);
        PublicationFormDTO publicationFormDTO2 = new PublicationFormDTO();
        assertThat(publicationFormDTO1).isNotEqualTo(publicationFormDTO2);
        publicationFormDTO2.setId(publicationFormDTO1.getId());
        assertThat(publicationFormDTO1).isEqualTo(publicationFormDTO2);
        publicationFormDTO2.setId(2L);
        assertThat(publicationFormDTO1).isNotEqualTo(publicationFormDTO2);
        publicationFormDTO1.setId(null);
        assertThat(publicationFormDTO1).isNotEqualTo(publicationFormDTO2);
    }
}
