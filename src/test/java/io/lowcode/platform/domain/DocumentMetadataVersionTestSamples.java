package io.lowcode.platform.domain;

import java.util.UUID;

public class DocumentMetadataVersionTestSamples {

    public static DocumentMetadataVersion getDocumentMetadataVersionSample1() {
        return new DocumentMetadataVersion()
            .id("id1")
            .documentMetadataVersionName("documentMetadataVersionName1")
            .documentMetadataVersionCode("documentMetadataVersionCode1")
            .createUser("createUser1")
            .updateUser("updateUser1")
            .uuid("uuid1");
    }

    public static DocumentMetadataVersion getDocumentMetadataVersionSample2() {
        return new DocumentMetadataVersion()
            .id("id2")
            .documentMetadataVersionName("documentMetadataVersionName2")
            .documentMetadataVersionCode("documentMetadataVersionCode2")
            .createUser("createUser2")
            .updateUser("updateUser2")
            .uuid("uuid2");
    }

    public static DocumentMetadataVersion getDocumentMetadataVersionRandomSampleGenerator() {
        return new DocumentMetadataVersion()
            .id(UUID.randomUUID().toString())
            .documentMetadataVersionName(UUID.randomUUID().toString())
            .documentMetadataVersionCode(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
