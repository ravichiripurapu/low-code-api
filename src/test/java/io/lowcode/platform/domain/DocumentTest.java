package io.lowcode.platform.domain;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static io.lowcode.platform.domain.DocumentTestSamples.*;
import static io.lowcode.platform.domain.OrgTestSamples.getOrgRandomSampleGenerator;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = getDocumentSample1();
        Document document2 = new Document();
        assertThat(document1).isNotEqualTo(document2);

        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);

        document2 = getDocumentSample2();
        assertThat(document1).isNotEqualTo(document2);
    }

    @Test
    void orgTest() {
        Document document = getDocumentRandomSampleGenerator();
        Org orgBack = getOrgRandomSampleGenerator();

        document.setOrg(orgBack);
        assertThat(document.getOrg()).isEqualTo(orgBack);

        document.org(null);
        assertThat(document.getOrg()).isNull();
    }
}
