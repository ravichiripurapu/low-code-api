package io.lowcode.platform.domain;

import java.util.UUID;

public class PublicationTypeTestSamples {

    public static PublicationType getPublicationTypeSample1() {
        return new PublicationType()
            .id("id1")
            .publicationTypeName("publicationTypeName1")
            .publicationTypeCode("publicationTypeCode1")
            .createUser("createUser1")
            .updateUser("updateUser1")
            .uuid("uuid1");
    }

    public static PublicationType getPublicationTypeSample2() {
        return new PublicationType()
            .id("id2")
            .publicationTypeName("publicationTypeName2")
            .publicationTypeCode("publicationTypeCode2")
            .createUser("createUser2")
            .updateUser("updateUser2")
            .uuid("uuid2");
    }

    public static PublicationType getPublicationTypeRandomSampleGenerator() {
        return new PublicationType()
            .id(UUID.randomUUID().toString())
            .publicationTypeName(UUID.randomUUID().toString())
            .publicationTypeCode(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
