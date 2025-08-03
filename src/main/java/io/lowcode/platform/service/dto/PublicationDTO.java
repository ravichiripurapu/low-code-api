package io.lowcode.platform.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link io.lowcode.platform.domain.Publication} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationDTO implements Serializable {

    private String id;

    private String title;

    private ZonedDateTime publicationDate;

    private Boolean flagActive;

    private String createUser;

    private ZonedDateTime createdAt;

    private String updateUser;

    private ZonedDateTime updatedAt;

    private String uuid;

    private OrgDTO org;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(ZonedDateTime publicationDate) {
        this.publicationDate = publicationDate;
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

    public OrgDTO getOrg() {
        return org;
    }

    public void setOrg(OrgDTO org) {
        this.org = org;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationDTO)) {
            return false;
        }

        PublicationDTO publicationDTO = (PublicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", org=" + getOrg() +
            "}";
    }
}
