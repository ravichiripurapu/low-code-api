package io.lowcode.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DocumentMetadataVersion.
 */
@Entity
@Table(name = "document_metadata_version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentMetadataVersion implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "document_metadata_version_name")
    private String documentMetadataVersionName;

    @Column(name = "document_metadata_version_code")
    private String documentMetadataVersionCode;

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
    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    private DocumentMetadata documentMetadata;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public DocumentMetadataVersion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentMetadataVersionName() {
        return this.documentMetadataVersionName;
    }

    public DocumentMetadataVersion documentMetadataVersionName(String documentMetadataVersionName) {
        this.setDocumentMetadataVersionName(documentMetadataVersionName);
        return this;
    }

    public void setDocumentMetadataVersionName(String documentMetadataVersionName) {
        this.documentMetadataVersionName = documentMetadataVersionName;
    }

    public String getDocumentMetadataVersionCode() {
        return this.documentMetadataVersionCode;
    }

    public DocumentMetadataVersion documentMetadataVersionCode(String documentMetadataVersionCode) {
        this.setDocumentMetadataVersionCode(documentMetadataVersionCode);
        return this;
    }

    public void setDocumentMetadataVersionCode(String documentMetadataVersionCode) {
        this.documentMetadataVersionCode = documentMetadataVersionCode;
    }

    public Boolean getFlagActive() {
        return this.flagActive;
    }

    public DocumentMetadataVersion flagActive(Boolean flagActive) {
        this.setFlagActive(flagActive);
        return this;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public DocumentMetadataVersion createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public DocumentMetadataVersion createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public DocumentMetadataVersion updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public DocumentMetadataVersion updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return this.uuid;
    }

    public DocumentMetadataVersion uuid(String uuid) {
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

    public DocumentMetadataVersion setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public DocumentMetadata getDocumentMetadata() {
        return this.documentMetadata;
    }

    public void setDocumentMetadata(DocumentMetadata documentMetadata) {
        this.documentMetadata = documentMetadata;
    }

    public DocumentMetadataVersion documentMetadata(DocumentMetadata documentMetadata) {
        this.setDocumentMetadata(documentMetadata);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentMetadataVersion)) {
            return false;
        }
        return getId() != null && getId().equals(((DocumentMetadataVersion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentMetadataVersion{" +
            "id=" + getId() +
            ", documentMetadataVersionName='" + getDocumentMetadataVersionName() + "'" +
            ", documentMetadataVersionCode='" + getDocumentMetadataVersionCode() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
