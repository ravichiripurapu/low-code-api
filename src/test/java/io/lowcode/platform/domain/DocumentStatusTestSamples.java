package io.lowcode.platform.domain;

import java.util.UUID;

public class DocumentStatusTestSamples {

    public static DocumentStatus getDocumentStatusSample1() {
        return new DocumentStatus().id("id1").name("name1").code("code1").createUser("createUser1").updateUser("updateUser1").uuid("uuid1");
    }

    public static DocumentStatus getDocumentStatusSample2() {
        return new DocumentStatus().id("id2").name("name2").code("code2").createUser("createUser2").updateUser("updateUser2").uuid("uuid2");
    }

    public static DocumentStatus getDocumentStatusRandomSampleGenerator() {
        return new DocumentStatus()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
