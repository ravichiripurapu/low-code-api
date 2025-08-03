package io.lowcode.platform.domain;

import java.util.UUID;

public class PublicationTestSamples {

    public static Publication getPublicationSample1() {
        return new Publication().id("id1").title("title1").createUser("createUser1").updateUser("updateUser1").uuid("uuid1");
    }

    public static Publication getPublicationSample2() {
        return new Publication().id("id2").title("title2").createUser("createUser2").updateUser("updateUser2").uuid("uuid2");
    }

    public static Publication getPublicationRandomSampleGenerator() {
        return new Publication()
            .id(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
