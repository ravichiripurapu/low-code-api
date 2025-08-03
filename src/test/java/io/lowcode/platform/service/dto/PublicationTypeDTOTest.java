package io.lowcode.platform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationTypeDTO.class);
        PublicationTypeDTO publicationTypeDTO1 = new PublicationTypeDTO();
        publicationTypeDTO1.setId("id1");
        PublicationTypeDTO publicationTypeDTO2 = new PublicationTypeDTO();
        assertThat(publicationTypeDTO1).isNotEqualTo(publicationTypeDTO2);
        publicationTypeDTO2.setId(publicationTypeDTO1.getId());
        assertThat(publicationTypeDTO1).isEqualTo(publicationTypeDTO2);
        publicationTypeDTO2.setId("id2");
        assertThat(publicationTypeDTO1).isNotEqualTo(publicationTypeDTO2);
        publicationTypeDTO1.setId(null);
        assertThat(publicationTypeDTO1).isNotEqualTo(publicationTypeDTO2);
    }
}
