package io.lowcode.platform.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentMetadataTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DocumentMetadata getDocumentMetadataSample1() {
        return new DocumentMetadata()
            .id(1L)
            .fieldLabel("fieldLabel1")
            .fieldType("fieldType1")
            .fieldName("fieldName1")
            .fieldId("fieldId1")
            .fieldClass("fieldClass1")
            .fieldPlaceholder("fieldPlaceholder1")
            .fieldOptions("fieldOptions1")
            .fieldConfigs("fieldConfigs1")
            .description("description1")
            .orderPosition(1)
            .validationConfigs("validationConfigs1")
            .errorMessage("errorMessage1")
            .createUser("createUser1")
            .updateUser("updateUser1")
            .uuid("uuid1");
    }

    public static DocumentMetadata getDocumentMetadataSample2() {
        return new DocumentMetadata()
            .id(2L)
            .fieldLabel("fieldLabel2")
            .fieldType("fieldType2")
            .fieldName("fieldName2")
            .fieldId("fieldId2")
            .fieldClass("fieldClass2")
            .fieldPlaceholder("fieldPlaceholder2")
            .fieldOptions("fieldOptions2")
            .fieldConfigs("fieldConfigs2")
            .description("description2")
            .orderPosition(2)
            .validationConfigs("validationConfigs2")
            .errorMessage("errorMessage2")
            .createUser("createUser2")
            .updateUser("updateUser2")
            .uuid("uuid2");
    }

    public static DocumentMetadata getDocumentMetadataRandomSampleGenerator() {
        return new DocumentMetadata()
            .id(longCount.incrementAndGet())
            .fieldLabel(UUID.randomUUID().toString())
            .fieldType(UUID.randomUUID().toString())
            .fieldName(UUID.randomUUID().toString())
            .fieldId(UUID.randomUUID().toString())
            .fieldClass(UUID.randomUUID().toString())
            .fieldPlaceholder(UUID.randomUUID().toString())
            .fieldOptions(UUID.randomUUID().toString())
            .fieldConfigs(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .orderPosition(intCount.incrementAndGet())
            .validationConfigs(UUID.randomUUID().toString())
            .errorMessage(UUID.randomUUID().toString())
            .createUser(UUID.randomUUID().toString())
            .updateUser(UUID.randomUUID().toString())
            .uuid(UUID.randomUUID().toString());
    }
}
