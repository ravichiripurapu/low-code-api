package io.lowcode.platform.domain;

import static io.lowcode.platform.domain.PublicationStatusTestSamples.*;
import static io.lowcode.platform.domain.PublicationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationStatus.class);
        PublicationStatus publicationStatus1 = getPublicationStatusSample1();
        PublicationStatus publicationStatus2 = new PublicationStatus();
        assertThat(publicationStatus1).isNotEqualTo(publicationStatus2);

        publicationStatus2.setId(publicationStatus1.getId());
        assertThat(publicationStatus1).isEqualTo(publicationStatus2);

        publicationStatus2 = getPublicationStatusSample2();
        assertThat(publicationStatus1).isNotEqualTo(publicationStatus2);
    }

    @Test
    void publicationTest() {
        PublicationStatus publicationStatus = getPublicationStatusRandomSampleGenerator();
        Publication publicationBack = getPublicationRandomSampleGenerator();

        publicationStatus.setPublication(publicationBack);
        assertThat(publicationStatus.getPublication()).isEqualTo(publicationBack);

        publicationStatus.publication(null);
        assertThat(publicationStatus.getPublication()).isNull();
    }
}
