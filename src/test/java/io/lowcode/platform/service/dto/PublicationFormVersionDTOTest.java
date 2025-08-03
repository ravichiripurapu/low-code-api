package io.lowcode.platform.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationFormVersionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationFormVersionDTO.class);
        PublicationFormVersionDTO publicationFormVersionDTO1 = new PublicationFormVersionDTO();
        publicationFormVersionDTO1.setId("id1");
        PublicationFormVersionDTO publicationFormVersionDTO2 = new PublicationFormVersionDTO();
        assertThat(publicationFormVersionDTO1).isNotEqualTo(publicationFormVersionDTO2);
        publicationFormVersionDTO2.setId(publicationFormVersionDTO1.getId());
        assertThat(publicationFormVersionDTO1).isEqualTo(publicationFormVersionDTO2);
        publicationFormVersionDTO2.setId("id2");
        assertThat(publicationFormVersionDTO1).isNotEqualTo(publicationFormVersionDTO2);
        publicationFormVersionDTO1.setId(null);
        assertThat(publicationFormVersionDTO1).isNotEqualTo(publicationFormVersionDTO2);
    }
}
