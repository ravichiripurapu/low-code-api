package io.lowcode.platform.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link io.lowcode.platform.domain.PublicationType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationTypeDTO implements Serializable {

    private String id;

    private String publicationTypeName;

    private String publicationTypeCode;

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

    public String getPublicationTypeName() {
        return publicationTypeName;
    }

    public void setPublicationTypeName(String publicationTypeName) {
        this.publicationTypeName = publicationTypeName;
    }

    public String getPublicationTypeCode() {
        return publicationTypeCode;
    }

    public void setPublicationTypeCode(String publicationTypeCode) {
        this.publicationTypeCode = publicationTypeCode;
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
        if (!(o instanceof PublicationTypeDTO)) {
            return false;
        }

        PublicationTypeDTO publicationTypeDTO = (PublicationTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicationTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationTypeDTO{" +
            "id='" + getId() + "'" +
            ", publicationTypeName='" + getPublicationTypeName() + "'" +
            ", publicationTypeCode='" + getPublicationTypeCode() + "'" +
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
