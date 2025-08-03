package io.lowcode.platform.domain;

import java.util.UUID;

public class DocumentTypeTestSamples {

    public static DocumentType getDocumentTypeSample1() {
        return new DocumentType()
            .id("id1")
            .name("name1")
            .documentode("documentode1")
            .createUser("createUser1")
            .updateUser("updateUser1")
            .uuid("uuid1");
    }

    public static DocumentType getDocumentTypeSample2() {
        return new DocumentType()
            .id("id2")
            .name("name2")
            .documentode("documentode2")
            .createUser("createUser2")
            .updateUser("updateUser2")
            .uuid("uuid2");
    }

    public static DocumentType getDocumentTypeRandomSampleGenerator() {
        return new DocumentType()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .documentode(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
