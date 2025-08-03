package io.lowcode.platform.domain;

import static io.lowcode.platform.domain.PublicationFormTestSamples.*;
import static io.lowcode.platform.domain.PublicationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationFormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicationForm.class);
        PublicationForm publicationForm1 = getPublicationFormSample1();
        PublicationForm publicationForm2 = new PublicationForm();
        assertThat(publicationForm1).isNotEqualTo(publicationForm2);

        publicationForm2.setId(publicationForm1.getId());
        assertThat(publicationForm1).isEqualTo(publicationForm2);

        publicationForm2 = getPublicationFormSample2();
        assertThat(publicationForm1).isNotEqualTo(publicationForm2);
    }

    @Test
    void publicationTest() {
        PublicationForm publicationForm = getPublicationFormRandomSampleGenerator();
        Publication publicationBack = getPublicationRandomSampleGenerator();

        publicationForm.setPublication(publicationBack);
        assertThat(publicationForm.getPublication()).isEqualTo(publicationBack);

        publicationForm.publication(null);
        assertThat(publicationForm.getPublication()).isNull();
    }
}
