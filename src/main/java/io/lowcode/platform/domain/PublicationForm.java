package io.lowcode.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PublicationForm.
 */
@Entity
@Table(name = "publication_form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "field_label")
    private String fieldLabel;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_id")
    private String fieldId;

    @Column(name = "field_class")
    private String fieldClass;

    @Column(name = "field_placeholder")
    private String fieldPlaceholder;

    @Column(name = "field_options")
    private String fieldOptions;

    @Column(name = "field_configs")
    private String fieldConfigs;

    @Column(name = "description")
    private String description;

    @Column(name = "order_position")
    private Integer orderPosition;

    @Column(name = "validation_configs")
    private String validationConfigs;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "flag_required")
    private Boolean flagRequired;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "org" }, allowSetters = true)
    private Publication publication;

    public Long getId() {
        return this.id;
    }

    public PublicationForm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldLabel() {
        return this.fieldLabel;
    }

    public PublicationForm fieldLabel(String fieldLabel) {
        this.setFieldLabel(fieldLabel);
        return this;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public PublicationForm fieldType(String fieldType) {
        this.setFieldType(fieldType);
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public PublicationForm fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldId() {
        return this.fieldId;
    }

    public PublicationForm fieldId(String fieldId) {
        this.setFieldId(fieldId);
        return this;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldClass() {
        return this.fieldClass;
    }

    public PublicationForm fieldClass(String fieldClass) {
        this.setFieldClass(fieldClass);
        return this;
    }

    public void setFieldClass(String fieldClass) {
        this.fieldClass = fieldClass;
    }

    public String getFieldPlaceholder() {
        return this.fieldPlaceholder;
    }

    public PublicationForm fieldPlaceholder(String fieldPlaceholder) {
        this.setFieldPlaceholder(fieldPlaceholder);
        return this;
    }

    public void setFieldPlaceholder(String fieldPlaceholder) {
        this.fieldPlaceholder = fieldPlaceholder;
    }

    public String getFieldOptions() {
        return this.fieldOptions;
    }

    public PublicationForm fieldOptions(String fieldOptions) {
        this.setFieldOptions(fieldOptions);
        return this;
    }

    public void setFieldOptions(String fieldOptions) {
        this.fieldOptions = fieldOptions;
    }

    public String getFieldConfigs() {
        return this.fieldConfigs;
    }

    public PublicationForm fieldConfigs(String fieldConfigs) {
        this.setFieldConfigs(fieldConfigs);
        return this;
    }

    public void setFieldConfigs(String fieldConfigs) {
        this.fieldConfigs = fieldConfigs;
    }

    public String getDescription() {
        return this.description;
    }

    public PublicationForm description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderPosition() {
        return this.orderPosition;
    }

    public PublicationForm orderPosition(Integer orderPosition) {
        this.setOrderPosition(orderPosition);
        return this;
    }

    public void setOrderPosition(Integer orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getValidationConfigs() {
        return this.validationConfigs;
    }

    public PublicationForm validationConfigs(String validationConfigs) {
        this.setValidationConfigs(validationConfigs);
        return this;
    }

    public void setValidationConfigs(String validationConfigs) {
        this.validationConfigs = validationConfigs;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public PublicationForm errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getFlagRequired() {
        return this.flagRequired;
    }

    public PublicationForm flagRequired(Boolean flagRequired) {
        this.setFlagRequired(flagRequired);
        return this;
    }

    public void setFlagRequired(Boolean flagRequired) {
        this.flagRequired = flagRequired;
    }

    public Boolean getFlagActive() {
        return this.flagActive;
    }

    public PublicationForm flagActive(Boolean flagActive) {
        this.setFlagActive(flagActive);
        return this;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public PublicationForm createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public PublicationForm createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public PublicationForm updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public PublicationForm updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return this.uuid;
    }

    public PublicationForm uuid(String uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public PublicationForm publication(Publication publication) {
        this.setPublication(publication);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationForm)) {
            return false;
        }
        return getId() != null && getId().equals(((PublicationForm) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationForm{" +
            "id=" + getId() +
            ", fieldLabel='" + getFieldLabel() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldId='" + getFieldId() + "'" +
            ", fieldClass='" + getFieldClass() + "'" +
            ", fieldPlaceholder='" + getFieldPlaceholder() + "'" +
            ", fieldOptions='" + getFieldOptions() + "'" +
            ", fieldConfigs='" + getFieldConfigs() + "'" +
            ", description='" + getDescription() + "'" +
            ", orderPosition=" + getOrderPosition() +
            ", validationConfigs='" + getValidationConfigs() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", flagRequired='" + getFlagRequired() + "'" +
            ", flagActive='" + getFlagActive() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
