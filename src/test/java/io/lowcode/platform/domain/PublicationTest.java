package io.lowcode.platform.domain;

import static io.lowcode.platform.domain.OrgTestSamples.*;
import static io.lowcode.platform.domain.PublicationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Publication.class);
        Publication publication1 = getPublicationSample1();
        Publication publication2 = new Publication();
        assertThat(publication1).isNotEqualTo(publication2);

        publication2.setId(publication1.getId());
        assertThat(publication1).isEqualTo(publication2);

        publication2 = getPublicationSample2();
        assertThat(publication1).isNotEqualTo(publication2);
    }

    @Test
    void orgTest() {
        Publication publication = getPublicationRandomSampleGenerator();
        Org orgBack = getOrgRandomSampleGenerator();

        publication.setOrg(orgBack);
        assertThat(publication.getOrg()).isEqualTo(orgBack);

        publication.org(null);
        assertThat(publication.getOrg()).isNull();
    }
}
