package io.lowcode.platform.domain;

import java.util.UUID;

public class PublicationFormVersionTestSamples {

    public static PublicationFormVersion getPublicationFormVersionSample1() {
        return new PublicationFormVersion()
            .id("id1")
            .publicationFormVersionName("publicationFormVersionName1")
            .publicationFormVersionCode("publicationFormVersionCode1")
            .createUser("createUser1")
            .updateUser("updateUser1")
            .uuid("uuid1");
    }

    public static PublicationFormVersion getPublicationFormVersionSample2() {
        return new PublicationFormVersion()
            .id("id2")
            .publicationFormVersionName("publicationFormVersionName2")
            .publicationFormVersionCode("publicationFormVersionCode2")
            .createUser("createUser2")
            .updateUser("updateUser2")
            .uuid("uuid2");
    }

    public static PublicationFormVersion getPublicationFormVersionRandomSampleGenerator() {
        return new PublicationFormVersion()
            .id(UUID.randomUUID().toString())
            .publicationFormVersionName(UUID.randomUUID().toString())
            .publicationFormVersionCode(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
