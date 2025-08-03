package io.lowcode.platform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationFormDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationFormDataDTO.class);
        PublicationFormDataDTO publicationFormDataDTO1 = new PublicationFormDataDTO();
        publicationFormDataDTO1.setId(1L);
        PublicationFormDataDTO publicationFormDataDTO2 = new PublicationFormDataDTO();
        assertThat(publicationFormDataDTO1).isNotEqualTo(publicationFormDataDTO2);
        publicationFormDataDTO2.setId(publicationFormDataDTO1.getId());
        assertThat(publicationFormDataDTO1).isEqualTo(publicationFormDataDTO2);
        publicationFormDataDTO2.setId(2L);
        assertThat(publicationFormDataDTO1).isNotEqualTo(publicationFormDataDTO2);
        publicationFormDataDTO1.setId(null);
        assertThat(publicationFormDataDTO1).isNotEqualTo(publicationFormDataDTO2);
    }
}
