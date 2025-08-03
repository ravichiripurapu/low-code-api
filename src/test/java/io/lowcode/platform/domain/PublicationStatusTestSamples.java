package io.lowcode.platform.domain;

import java.util.UUID;

public class PublicationStatusTestSamples {

    public static PublicationStatus getPublicationStatusSample1() {
        return new PublicationStatus()
            .id("id1")
            .publicationStatusName("publicationStatusName1")
            .publicationStatusCode("publicationStatusCode1")
            .createUser("createUser1")
            .updateUser("updateUser1")
            .uuid("uuid1");
    }

    public static PublicationStatus getPublicationStatusSample2() {
        return new PublicationStatus()
            .id("id2")
            .publicationStatusName("publicationStatusName2")
            .publicationStatusCode("publicationStatusCode2")
            .createUser("createUser2")
            .updateUser("updateUser2")
            .uuid("uuid2");
    }

    public static PublicationStatus getPublicationStatusRandomSampleGenerator() {
        return new PublicationStatus()
            .id(UUID.randomUUID().toString())
            .publicationStatusName(UUID.randomUUID().toString())
            .publicationStatusCode(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
