package io.lowcode.platform.domain;

import static io.lowcode.platform.domain.PublicationTestSamples.*;
import static io.lowcode.platform.domain.PublicationTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationType.class);
        PublicationType publicationType1 = getPublicationTypeSample1();
        PublicationType publicationType2 = new PublicationType();
        assertThat(publicationType1).isNotEqualTo(publicationType2);

        publicationType2.setId(publicationType1.getId());
        assertThat(publicationType1).isEqualTo(publicationType2);

        publicationType2 = getPublicationTypeSample2();
        assertThat(publicationType1).isNotEqualTo(publicationType2);
    }

    @Test
    void publicationTest() {
        PublicationType publicationType = getPublicationTypeRandomSampleGenerator();
        Publication publicationBack = getPublicationRandomSampleGenerator();

        publicationType.setPublication(publicationBack);
        assertThat(publicationType.getPublication()).isEqualTo(publicationBack);

        publicationType.publication(null);
        assertThat(publicationType.getPublication()).isNull();
    }
}
