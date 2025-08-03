package io.lowcode.platform.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PublicationFormData.
 */
@Entity
@Table(name = "publication_form_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationFormData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_form_version")
    private Long idFormVersion;

    @Column(name = "id_form_parent")
    private Long idFormParent;

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

    @Column(name = "value")
    private String value;

    @Column(name = "alternative_value")
    private String alternativeValue;

    @Column(name = "uuid")
    private String uuid;

    public Long getId() {
        return this.id;
    }

    public PublicationFormData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFormVersion() {
        return this.idFormVersion;
    }

    public PublicationFormData idFormVersion(Long idFormVersion) {
        this.setIdFormVersion(idFormVersion);
        return this;
    }

    public void setIdFormVersion(Long idFormVersion) {
        this.idFormVersion = idFormVersion;
    }

    public Long getIdFormParent() {
        return this.idFormParent;
    }

    public PublicationFormData idFormParent(Long idFormParent) {
        this.setIdFormParent(idFormParent);
        return this;
    }

    public void setIdFormParent(Long idFormParent) {
        this.idFormParent = idFormParent;
    }

    public String getFieldLabel() {
        return this.fieldLabel;
    }

    public PublicationFormData fieldLabel(String fieldLabel) {
        this.setFieldLabel(fieldLabel);
        return this;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public PublicationFormData fieldType(String fieldType) {
        this.setFieldType(fieldType);
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public PublicationFormData fieldName(String fieldName) {
        this.setFieldName(fieldName);
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldId() {
        return this.fieldId;
    }

    public PublicationFormData fieldId(String fieldId) {
        this.setFieldId(fieldId);
        return this;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldClass() {
        return this.fieldClass;
    }

    public PublicationFormData fieldClass(String fieldClass) {
        this.setFieldClass(fieldClass);
        return this;
    }

    public void setFieldClass(String fieldClass) {
        this.fieldClass = fieldClass;
    }

    public String getFieldPlaceholder() {
        return this.fieldPlaceholder;
    }

    public PublicationFormData fieldPlaceholder(String fieldPlaceholder) {
        this.setFieldPlaceholder(fieldPlaceholder);
        return this;
    }

    public void setFieldPlaceholder(String fieldPlaceholder) {
        this.fieldPlaceholder = fieldPlaceholder;
    }

    public String getFieldOptions() {
        return this.fieldOptions;
    }

    public PublicationFormData fieldOptions(String fieldOptions) {
        this.setFieldOptions(fieldOptions);
        return this;
    }

    public void setFieldOptions(String fieldOptions) {
        this.fieldOptions = fieldOptions;
    }

    public String getFieldConfigs() {
        return this.fieldConfigs;
    }

    public PublicationFormData fieldConfigs(String fieldConfigs) {
        this.setFieldConfigs(fieldConfigs);
        return this;
    }

    public void setFieldConfigs(String fieldConfigs) {
        this.fieldConfigs = fieldConfigs;
    }

    public String getDescription() {
        return this.description;
    }

    public PublicationFormData description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderPosition() {
        return this.orderPosition;
    }

    public PublicationFormData orderPosition(Integer orderPosition) {
        this.setOrderPosition(orderPosition);
        return this;
    }

    public void setOrderPosition(Integer orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getValidationConfigs() {
        return this.validationConfigs;
    }

    public PublicationFormData validationConfigs(String validationConfigs) {
        this.setValidationConfigs(validationConfigs);
        return this;
    }

    public void setValidationConfigs(String validationConfigs) {
        this.validationConfigs = validationConfigs;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public PublicationFormData errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getFlagRequired() {
        return this.flagRequired;
    }

    public PublicationFormData flagRequired(Boolean flagRequired) {
        this.setFlagRequired(flagRequired);
        return this;
    }

    public void setFlagRequired(Boolean flagRequired) {
        this.flagRequired = flagRequired;
    }

    public Boolean getFlagActive() {
        return this.flagActive;
    }

    public PublicationFormData flagActive(Boolean flagActive) {
        this.setFlagActive(flagActive);
        return this;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public PublicationFormData createUser(String createUser) {
        this.setCreateUser(createUser);
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public PublicationFormData createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public PublicationFormData updateUser(String updateUser) {
        this.setUpdateUser(updateUser);
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public PublicationFormData updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getValue() {
        return this.value;
    }

    public PublicationFormData value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAlternativeValue() {
        return this.alternativeValue;
    }

    public PublicationFormData alternativeValue(String alternativeValue) {
        this.setAlternativeValue(alternativeValue);
        return this;
    }

    public void setAlternativeValue(String alternativeValue) {
        this.alternativeValue = alternativeValue;
    }

    public String getUuid() {
        return this.uuid;
    }

    public PublicationFormData uuid(String uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PublicationFormData)) {
            return false;
        }
        return getId() != null && getId().equals(((PublicationFormData) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationFormData{" +
            "id=" + getId() +
            ", idFormVersion=" + getIdFormVersion() +
            ", idFormParent=" + getIdFormParent() +
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
            ", value='" + getValue() + "'" +
            ", alternativeValue='" + getAlternativeValue() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
