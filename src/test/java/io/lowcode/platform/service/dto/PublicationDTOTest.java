package io.lowcode.platform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationDTO.class);
        PublicationDTO publicationDTO1 = new PublicationDTO();
        publicationDTO1.setId("id1");
        PublicationDTO publicationDTO2 = new PublicationDTO();
        assertThat(publicationDTO1).isNotEqualTo(publicationDTO2);
        publicationDTO2.setId(publicationDTO1.getId());
        assertThat(publicationDTO1).isEqualTo(publicationDTO2);
        publicationDTO2.setId("id2");
        assertThat(publicationDTO1).isNotEqualTo(publicationDTO2);
        publicationDTO1.setId(null);
        assertThat(publicationDTO1).isNotEqualTo(publicationDTO2);
    }
}
