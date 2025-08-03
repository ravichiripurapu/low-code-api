package io.lowcode.platform.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link io.lowcode.platform.domain.DocumentMetadataVersion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentMetadataVersionDTO implements Serializable {

    private String id;

    private String documentMetadataVersionName;

    private String documentMetadataVersionCode;

    private Boolean flagActive;

    private String createUser;

    private ZonedDateTime createdAt;

    private String updateUser;

    private ZonedDateTime updatedAt;

    private String uuid;

    private DocumentMetadataDTO documentMetadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentMetadataVersionName() {
        return documentMetadataVersionName;
    }

    public void setDocumentMetadataVersionName(String documentMetadataVersionName) {
        this.documentMetadataVersionName = documentMetadataVersionName;
    }

    public String getDocumentMetadataVersionCode() {
        return documentMetadataVersionCode;
    }

    public void setDocumentMetadataVersionCode(String documentMetadataVersionCode) {
        this.documentMetadataVersionCode = documentMetadataVersionCode;
    }

    public Boolean getFlagActive() {
        return flagActive;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DocumentMetadataDTO getDocumentMetadata() {
        return documentMetadata;
    }

    public void setDocumentMetadata(DocumentMetadataDTO documentMetadata) {
        this.documentMetadata = documentMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentMetadataVersionDTO)) {
            return false;
        }

        DocumentMetadataVersionDTO documentMetadataVersionDTO = (DocumentMetadataVersionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentMetadataVersionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentMetadataVersionDTO{" +
            "id='" + getId() + "'" +
            ", documentMetadataVersionName='" + getDocumentMetadataVersionName() + "'" +
            ", documentMetadataVersionCode='" + getDocumentMetadataVersionCode() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", documentMetadata=" + getDocumentMetadata() +
            "}";
    }
}
