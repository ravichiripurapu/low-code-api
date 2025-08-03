package io.lowcode.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A PublicationStatus.
 */
@Entity
@Table(name = "publication_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationStatus implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "publication_status_name")
    private String publicationStatusName;

    @Column(name = "publication_status_code")
    private String publicationStatusCode;

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
    @JsonIgnoreProperties(value = { "org" }, allowSetters = true)
    private Publication publication;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PublicationStatus id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicationStatusName() {
        return this.publicationStatusName;
    }

    public PublicationStatus publicationStatusName(String publicationStatusName) {
        this.setPublicationStatusName(publicationStatusName);
        return this;
    }

    public void setPublicationStatusName(String publicationStatusName) {
        this.publicationStatusName = publicationStatusName;
    }

    public String getPublicationStatusCode() {
        return this.publicationStatusCode;
    }

    public PublicationStatus publicationStatusCode(String publicationStatusCode) {
        this.setPublicationStatusCode(publicationStatusCode);
        return this;
    }

    public void setPublicationStatusCode(String publicationStatusCode) {
        this.publicationStatusCode = publicationStatusCode;
    }

    public Boolean getFlagActive() {
        return this.flagActive;
    }

    public PublicationStatus flagActive(Boolean flagActive) {
        this.setFlagActive(flagActive);
        return this;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public PublicationStatus createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public PublicationStatus createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public PublicationStatus updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public PublicationStatus updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return this.uuid;
    }

    public PublicationStatus uuid(String uuid) {
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

    public PublicationStatus setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public PublicationStatus publication(Publication publication) {
        this.setPublication(publication);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationStatus)) {
            return false;
        }
        return getId() != null && getId().equals(((PublicationStatus) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationStatus{" +
            "id=" + getId() +
            ", publicationStatusName='" + getPublicationStatusName() + "'" +
            ", publicationStatusCode='" + getPublicationStatusCode() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
