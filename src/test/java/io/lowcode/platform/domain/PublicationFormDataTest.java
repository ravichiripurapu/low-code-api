package io.lowcode.platform.domain;

import static io.lowcode.platform.domain.PublicationFormDataTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationFormDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationFormData.class);
        PublicationFormData publicationFormData1 = getPublicationFormDataSample1();
        PublicationFormData publicationFormData2 = new PublicationFormData();
        assertThat(publicationFormData1).isNotEqualTo(publicationFormData2);

        publicationFormData2.setId(publicationFormData1.getId());
        assertThat(publicationFormData1).isEqualTo(publicationFormData2);

        publicationFormData2 = getPublicationFormDataSample2();
        assertThat(publicationFormData1).isNotEqualTo(publicationFormData2);
    }
}
