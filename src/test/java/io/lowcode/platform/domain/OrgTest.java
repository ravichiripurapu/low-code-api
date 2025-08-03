package io.lowcode.platform.domain;

import static io.lowcode.platform.domain.OrgTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.lowcode.platform.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Org.class);
        Org org1 = getOrgSample1();
        Org org2 = new Org();
        assertThat(org1).isNotEqualTo(org2);

        org2.setId(org1.getId());
        assertThat(org1).isEqualTo(org2);

        org2 = getOrgSample2();
        assertThat(org1).isNotEqualTo(org2);
    }
}
