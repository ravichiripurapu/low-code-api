package io.lowcode.platform.domain;

import static io.lowcode.platform.domain.PublicationFormTestSamples.*;
import static io.lowcode.platform.domain.PublicationFormVersionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationFormVersionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationFormVersion.class);
        PublicationFormVersion publicationFormVersion1 = getPublicationFormVersionSample1();
        PublicationFormVersion publicationFormVersion2 = new PublicationFormVersion();
        assertThat(publicationFormVersion1).isNotEqualTo(publicationFormVersion2);

        publicationFormVersion2.setId(publicationFormVersion1.getId());
        assertThat(publicationFormVersion1).isEqualTo(publicationFormVersion2);

        publicationFormVersion2 = getPublicationFormVersionSample2();
        assertThat(publicationFormVersion1).isNotEqualTo(publicationFormVersion2);
    }

    @Test
    void publicationFormTest() {
        PublicationFormVersion publicationFormVersion = getPublicationFormVersionRandomSampleGenerator();
        PublicationForm publicationFormBack = getPublicationFormRandomSampleGenerator();

        publicationFormVersion.setPublicationForm(publicationFormBack);
        assertThat(publicationFormVersion.getPublicationForm()).isEqualTo(publicationFormBack);

        publicationFormVersion.publicationForm(null);
        assertThat(publicationFormVersion.getPublicationForm()).isNull();
    }
}
