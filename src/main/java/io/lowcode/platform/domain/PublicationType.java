package io.lowcode.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A PublicationType.
 */
@Entity
@Table(name = "publication_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationType implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "publication_type_name")
    private String publicationTypeName;

    @Column(name = "publication_type_code")
    private String publicationTypeCode;

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

    public String getId() {
        return this.id;
    }

    public PublicationType id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicationTypeName() {
        return this.publicationTypeName;
    }

    public PublicationType publicationTypeName(String publicationTypeName) {
        this.setPublicationTypeName(publicationTypeName);
        return this;
    }

    public void setPublicationTypeName(String publicationTypeName) {
        this.publicationTypeName = publicationTypeName;
    }

    public String getPublicationTypeCode() {
        return this.publicationTypeCode;
    }

    public PublicationType publicationTypeCode(String publicationTypeCode) {
        this.setPublicationTypeCode(publicationTypeCode);
        return this;
    }

    public void setPublicationTypeCode(String publicationTypeCode) {
        this.publicationTypeCode = publicationTypeCode;
    }

    public Boolean getFlagActive() {
        return this.flagActive;
    }

    public PublicationType flagActive(Boolean flagActive) {
        this.setFlagActive(flagActive);
        return this;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public PublicationType createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public PublicationType createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public PublicationType updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public PublicationType updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return this.uuid;
    }

    public PublicationType uuid(String uuid) {
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

    public PublicationType setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public PublicationType publication(Publication publication) {
        this.setPublication(publication);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationType)) {
            return false;
        }
        return getId() != null && getId().equals(((PublicationType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationType{" +
            "id=" + getId() +
            ", publicationTypeName='" + getPublicationTypeName() + "'" +
            ", publicationTypeCode='" + getPublicationTypeCode() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
