package io.lowcode.platform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationStatusDTO.class);
        PublicationStatusDTO publicationStatusDTO1 = new PublicationStatusDTO();
        publicationStatusDTO1.setId("id1");
        PublicationStatusDTO publicationStatusDTO2 = new PublicationStatusDTO();
        assertThat(publicationStatusDTO1).isNotEqualTo(publicationStatusDTO2);
        publicationStatusDTO2.setId(publicationStatusDTO1.getId());
        assertThat(publicationStatusDTO1).isEqualTo(publicationStatusDTO2);
        publicationStatusDTO2.setId("id2");
        assertThat(publicationStatusDTO1).isNotEqualTo(publicationStatusDTO2);
        publicationStatusDTO1.setId(null);
        assertThat(publicationStatusDTO1).isNotEqualTo(publicationStatusDTO2);
    }
}
