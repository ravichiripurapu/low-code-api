package io.lowcode.platform.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link io.lowcode.platform.domain.PublicationForm} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PublicationFormDTO implements Serializable {

    private Long id;

    private String fieldLabel;

    private String fieldType;

    private String fieldName;

    private String fieldId;

    private String fieldClass;

    private String fieldPlaceholder;

    private String fieldOptions;

    private String fieldConfigs;

    private String description;

    private Integer orderPosition;

    private String validationConfigs;

    private String errorMessage;

    private Boolean flagRequired;

    private Boolean flagActive;

    private String createUser;

    private ZonedDateTime createdAt;

    private String updateUser;

    private ZonedDateTime updatedAt;

    private String uuid;

    private PublicationDTO publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(String fieldClass) {
        this.fieldClass = fieldClass;
    }

    public String getFieldPlaceholder() {
        return fieldPlaceholder;
    }

    public void setFieldPlaceholder(String fieldPlaceholder) {
        this.fieldPlaceholder = fieldPlaceholder;
    }

    public String getFieldOptions() {
        return fieldOptions;
    }

    public void setFieldOptions(String fieldOptions) {
        this.fieldOptions = fieldOptions;
    }

    public String getFieldConfigs() {
        return fieldConfigs;
    }

    public void setFieldConfigs(String fieldConfigs) {
        this.fieldConfigs = fieldConfigs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(Integer orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getValidationConfigs() {
        return validationConfigs;
    }

    public void setValidationConfigs(String validationConfigs) {
        this.validationConfigs = validationConfigs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getFlagRequired() {
        return flagRequired;
    }

    public void setFlagRequired(Boolean flagRequired) {
        this.flagRequired = flagRequired;
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
        if (!(o instanceof PublicationFormDTO)) {
            return false;
        }

        PublicationFormDTO publicationFormDTO = (PublicationFormDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, publicationFormDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicationFormDTO{" +
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
            ", publication=" + getPublication() +
            "}";
    }
}
