package io.lowcode.platform.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link io.lowcode.platform.domain.PublicationStatus} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationStatusDTO implements Serializable {

    private String id;

    private String publicationStatusName;

    private String publicationStatusCode;

    private Boolean flagActive;

    private String createUser;

    private ZonedDateTime createdAt;

    private String updateUser;

    private ZonedDateTime updatedAt;

    private String uuid;

    private PublicationDTO publication;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicationStatusName() {
        return publicationStatusName;
    }

    public void setPublicationStatusName(String publicationStatusName) {
        this.publicationStatusName = publicationStatusName;
    }

    public String getPublicationStatusCode() {
        return publicationStatusCode;
    }

    public void setPublicationStatusCode(String publicationStatusCode) {
        this.publicationStatusCode = publicationStatusCode;
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

    public PublicationDTO getPublication() {
        return publication;
    }

    public void setPublication(PublicationDTO publication) {
        this.publication = publication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationStatusDTO)) {
            return false;
        }

        PublicationStatusDTO publicationStatusDTO = (PublicationStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicationStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationStatusDTO{" +
            "id='" + getId() + "'" +
            ", publicationStatusName='" + getPublicationStatusName() + "'" +
            ", publicationStatusCode='" + getPublicationStatusCode() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", publication=" + getPublication() +
            "}";
    }
}
