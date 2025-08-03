package io.lowcode.platform.domain;

import java.util.UUID;

public class OrgTestSamples {

    public static Org getOrgSample1() {
        return new Org()
            .id("id1")
            .name("name1")
            .address1("address11")
            .address2("address21")
            .city("city1")
            .state("state1")
            .zipcode("zipcode1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static Org getOrgSample2() {
        return new Org()
            .id("id2")
            .name("name2")
            .address1("address12")
            .address2("address22")
            .city("city2")
            .state("state2")
            .zipcode("zipcode2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static Org getOrgRandomSampleGenerator() {
        return new Org()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .address1(UUID.randomUUID().toString())
            .address2(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .zipcode(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
