package io.lowcode.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A PublicationFormVersion.
 */
@Entity
@Table(name = "publication_form_version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationFormVersion implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "publication_form_version_name")
    private String publicationFormVersionName;

    @Column(name = "publication_form_version_code")
    private String publicationFormVersionCode;

    @Column(name = "flag_active")
    private Boolean flagActive;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "uuid")
    private String uuid;

    @org.springframework.data.annotation.Transient
    @Transient
    private boolean isPersisted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "publication" }, allowSetters = true)
    private PublicationForm publicationForm;

    public String getId() {
        return this.id;
    }

    public PublicationFormVersion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicationFormVersionName() {
        return this.publicationFormVersionName;
    }

    public PublicationFormVersion publicationFormVersionName(String publicationFormVersionName) {
        this.setPublicationFormVersionName(publicationFormVersionName);
        return this;
    }

    public void setPublicationFormVersionName(String publicationFormVersionName) {
        this.publicationFormVersionName = publicationFormVersionName;
    }

    public String getPublicationFormVersionCode() {
        return this.publicationFormVersionCode;
    }

    public PublicationFormVersion publicationFormVersionCode(String publicationFormVersionCode) {
        this.setPublicationFormVersionCode(publicationFormVersionCode);
        return this;
    }

    public void setPublicationFormVersionCode(String publicationFormVersionCode) {
        this.publicationFormVersionCode = publicationFormVersionCode;
    }

    public Boolean getFlagActive() {
        return this.flagActive;
    }

    public PublicationFormVersion flagActive(Boolean flagActive) {
        this.setFlagActive(flagActive);
        return this;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public PublicationFormVersion createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public PublicationFormVersion createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public PublicationFormVersion updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public PublicationFormVersion updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return this.uuid;
    }

    public PublicationFormVersion uuid(String uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @org.springframework.data.annotation.Transient
    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public PublicationFormVersion setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public PublicationForm getPublicationForm() {
        return this.publicationForm;
    }

    public void setPublicationForm(PublicationForm publicationForm) {
        this.publicationForm = publicationForm;
    }

    public PublicationFormVersion publicationForm(PublicationForm publicationForm) {
        this.setPublicationForm(publicationForm);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationFormVersion)) {
            return false;
        }
        return getId() != null && getId().equals(((PublicationFormVersion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationFormVersion{" +
            "id=" + getId() +
            ", publicationFormVersionName='" + getPublicationFormVersionName() + "'" +
            ", publicationFormVersionCode='" + getPublicationFormVersionCode() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
