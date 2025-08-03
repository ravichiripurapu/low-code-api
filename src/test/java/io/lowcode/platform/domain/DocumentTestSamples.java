package io.lowcode.platform.domain;

import java.util.UUID;

public class DocumentTestSamples {

    public static Document getDocumentSample1() {
        return new Document().id("id1").title("title1").createUser("createUser1").updateUser("updateUser1").uuid("uuid1");
    }

    public static Document getDocumentSample2() {
        return new Document().id("id2").title("title2").createUser("createUser2").updateUser("updateUser2").uuid("uuid2");
    }

    public static Document getDocumentRandomSampleGenerator() {
        return new Document()
            .id(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
