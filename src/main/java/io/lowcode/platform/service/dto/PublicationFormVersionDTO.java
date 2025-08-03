package io.lowcode.platform.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link io.lowcode.platform.domain.PublicationFormVersion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationFormVersionDTO implements Serializable {

    private String id;

    private String publicationFormVersionName;

    private String publicationFormVersionCode;

    private Boolean flagActive;

    private String createUser;

    private ZonedDateTime createdAt;

    private String updateUser;

    private ZonedDateTime updatedAt;

    private String uuid;

    private PublicationFormDTO publicationForm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicationFormVersionName() {
        return publicationFormVersionName;
    }

    public void setPublicationFormVersionName(String publicationFormVersionName) {
        this.publicationFormVersionName = publicationFormVersionName;
    }

    public String getPublicationFormVersionCode() {
        return publicationFormVersionCode;
    }

    public void setPublicationFormVersionCode(String publicationFormVersionCode) {
        this.publicationFormVersionCode = publicationFormVersionCode;
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

    public PublicationFormDTO getPublicationForm() {
        return publicationForm;
    }

    public void setPublicationForm(PublicationFormDTO publicationForm) {
        this.publicationForm = publicationForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationFormVersionDTO)) {
            return false;
        }

        PublicationFormVersionDTO publicationFormVersionDTO = (PublicationFormVersionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicationFormVersionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationFormVersionDTO{" +
            "id='" + getId() + "'" +
            ", publicationFormVersionName='" + getPublicationFormVersionName() + "'" +
            ", publicationFormVersionCode='" + getPublicationFormVersionCode() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", publicationForm=" + getPublicationForm() +
            "}";
    }
}
