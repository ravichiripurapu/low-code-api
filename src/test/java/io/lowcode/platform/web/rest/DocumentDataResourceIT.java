package io.lowcode.platform.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.DocumentData;
import io.lowcode.platform.repository.DocumentDataRepository;
import io.lowcode.platform.service.dto.DocumentDataDTO;
import io.lowcode.platform.service.mapper.DocumentDataMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static io.lowcode.platform.domain.DocumentDataAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentDataResourceIT {

    private static final Long DEFAULT_ID_FORM_VERSION = 1L;
    private static final Long UPDATED_ID_FORM_VERSION = 2L;

    private static final Long DEFAULT_ID_FORM_PARENT = 1L;
    private static final Long UPDATED_ID_FORM_PARENT = 2L;

    private static final String DEFAULT_FIELD_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_ID = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_PLACEHOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_PLACEHOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_OPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_CONFIGS = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_CONFIGS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_POSITION = 1;
    private static final Integer UPDATED_ORDER_POSITION = 2;

    private static final String DEFAULT_VALIDATION_CONFIGS = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_CONFIGS = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FLAG_REQUIRED = false;
    private static final Boolean UPDATED_FLAG_REQUIRED = true;

    private static final Boolean DEFAULT_FLAG_ACTIVE = false;
    private static final Boolean UPDATED_FLAG_ACTIVE = true;

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATIVE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATIVE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/document-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentDataRepository documentDataRepository;

    @Autowired
    private DocumentDataMapper documentDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentDataMockMvc;

    private DocumentData documentData;

    private DocumentData insertedDocumentData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentData createEntity() {
        return new DocumentData()
            .idFormVersion(DEFAULT_ID_FORM_VERSION)
            .idFormParent(DEFAULT_ID_FORM_PARENT)
            .fieldLabel(DEFAULT_FIELD_LABEL)
            .fieldType(DEFAULT_FIELD_TYPE)
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldId(DEFAULT_FIELD_ID)
            .fieldClass(DEFAULT_FIELD_CLASS)
            .fieldPlaceholder(DEFAULT_FIELD_PLACEHOLDER)
            .fieldOptions(DEFAULT_FIELD_OPTIONS)
            .fieldConfigs(DEFAULT_FIELD_CONFIGS)
            .description(DEFAULT_DESCRIPTION)
            .orderPosition(DEFAULT_ORDER_POSITION)
            .validationConfigs(DEFAULT_VALIDATION_CONFIGS)
            .errorMessage(DEFAULT_ERROR_MESSAGE)
            .flagRequired(DEFAULT_FLAG_REQUIRED)
            .flagActive(DEFAULT_FLAG_ACTIVE)
            .createUser(DEFAULT_CREATE_USER)
            .createdAt(DEFAULT_CREATED_AT)
            .updateUser(DEFAULT_UPDATE_USER)
            .updatedAt(DEFAULT_UPDATED_AT)
            .value(DEFAULT_VALUE)
            .alternativeValue(DEFAULT_ALTERNATIVE_VALUE)
            .uuid(DEFAULT_UUID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentData createUpdatedEntity() {
        return new DocumentData()
            .idFormVersion(UPDATED_ID_FORM_VERSION)
            .idFormParent(UPDATED_ID_FORM_PARENT)
            .fieldLabel(UPDATED_FIELD_LABEL)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldId(UPDATED_FIELD_ID)
            .fieldClass(UPDATED_FIELD_CLASS)
            .fieldPlaceholder(UPDATED_FIELD_PLACEHOLDER)
            .fieldOptions(UPDATED_FIELD_OPTIONS)
            .fieldConfigs(UPDATED_FIELD_CONFIGS)
            .description(UPDATED_DESCRIPTION)
            .orderPosition(UPDATED_ORDER_POSITION)
            .validationConfigs(UPDATED_VALIDATION_CONFIGS)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .flagRequired(UPDATED_FLAG_REQUIRED)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .value(UPDATED_VALUE)
            .alternativeValue(UPDATED_ALTERNATIVE_VALUE)
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        documentData = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDocumentData != null) {
            documentDataRepository.delete(insertedDocumentData);
            insertedDocumentData = null;
        }
    }

    @Test
    @Transactional
    void createDocumentData() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentData
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);
        var returnedDocumentDataDTO = om.readValue(
            restDocumentDataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentDataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentDataDTO.class
        );

        // Validate the DocumentData in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentData = documentDataMapper.toEntity(returnedDocumentDataDTO);
        assertDocumentDataUpdatableFieldsEquals(returnedDocumentData, getPersistedDocumentData(returnedDocumentData));

        insertedDocumentData = returnedDocumentData;
    }

    @Test
    @Transactional
    void createDocumentDataWithExistingId() throws Exception {
        // Create the DocumentData with an existing ID
        documentData.setId(1L);
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentData() throws Exception {
        // Initialize the database
        insertedDocumentData = documentDataRepository.saveAndFlush(documentData);

        // Get all the documentDataList
        restDocumentDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentData.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFormVersion").value(hasItem(DEFAULT_ID_FORM_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].idFormParent").value(hasItem(DEFAULT_ID_FORM_PARENT.intValue())))
            .andExpect(jsonPath("$.[*].fieldLabel").value(hasItem(DEFAULT_FIELD_LABEL)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldId").value(hasItem(DEFAULT_FIELD_ID)))
            .andExpect(jsonPath("$.[*].fieldClass").value(hasItem(DEFAULT_FIELD_CLASS)))
            .andExpect(jsonPath("$.[*].fieldPlaceholder").value(hasItem(DEFAULT_FIELD_PLACEHOLDER)))
            .andExpect(jsonPath("$.[*].fieldOptions").value(hasItem(DEFAULT_FIELD_OPTIONS)))
            .andExpect(jsonPath("$.[*].fieldConfigs").value(hasItem(DEFAULT_FIELD_CONFIGS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orderPosition").value(hasItem(DEFAULT_ORDER_POSITION)))
            .andExpect(jsonPath("$.[*].validationConfigs").value(hasItem(DEFAULT_VALIDATION_CONFIGS)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].flagRequired").value(hasItem(DEFAULT_FLAG_REQUIRED)))
            .andExpect(jsonPath("$.[*].flagActive").value(hasItem(DEFAULT_FLAG_ACTIVE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].alternativeValue").value(hasItem(DEFAULT_ALTERNATIVE_VALUE)))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getDocumentData() throws Exception {
        // Initialize the database
        insertedDocumentData = documentDataRepository.saveAndFlush(documentData);

        // Get the documentData
        restDocumentDataMockMvc
            .perform(get(ENTITY_API_URL_ID, documentData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentData.getId().intValue()))
            .andExpect(jsonPath("$.idFormVersion").value(DEFAULT_ID_FORM_VERSION.intValue()))
            .andExpect(jsonPath("$.idFormParent").value(DEFAULT_ID_FORM_PARENT.intValue()))
            .andExpect(jsonPath("$.fieldLabel").value(DEFAULT_FIELD_LABEL))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldId").value(DEFAULT_FIELD_ID))
            .andExpect(jsonPath("$.fieldClass").value(DEFAULT_FIELD_CLASS))
            .andExpect(jsonPath("$.fieldPlaceholder").value(DEFAULT_FIELD_PLACEHOLDER))
            .andExpect(jsonPath("$.fieldOptions").value(DEFAULT_FIELD_OPTIONS))
            .andExpect(jsonPath("$.fieldConfigs").value(DEFAULT_FIELD_CONFIGS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.orderPosition").value(DEFAULT_ORDER_POSITION))
            .andExpect(jsonPath("$.validationConfigs").value(DEFAULT_VALIDATION_CONFIGS))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE))
            .andExpect(jsonPath("$.flagRequired").value(DEFAULT_FLAG_REQUIRED))
            .andExpect(jsonPath("$.flagActive").value(DEFAULT_FLAG_ACTIVE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.alternativeValue").value(DEFAULT_ALTERNATIVE_VALUE))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingDocumentData() throws Exception {
        // Get the documentData
        restDocumentDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentData() throws Exception {
        // Initialize the database
        insertedDocumentData = documentDataRepository.saveAndFlush(documentData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentData
        DocumentData updatedDocumentData = documentDataRepository.findById(documentData.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentData are not directly saved in db
        em.detach(updatedDocumentData);
        updatedDocumentData
            .idFormVersion(UPDATED_ID_FORM_VERSION)
            .idFormParent(UPDATED_ID_FORM_PARENT)
            .fieldLabel(UPDATED_FIELD_LABEL)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldId(UPDATED_FIELD_ID)
            .fieldClass(UPDATED_FIELD_CLASS)
            .fieldPlaceholder(UPDATED_FIELD_PLACEHOLDER)
            .fieldOptions(UPDATED_FIELD_OPTIONS)
            .fieldConfigs(UPDATED_FIELD_CONFIGS)
            .description(UPDATED_DESCRIPTION)
            .orderPosition(UPDATED_ORDER_POSITION)
            .validationConfigs(UPDATED_VALIDATION_CONFIGS)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .flagRequired(UPDATED_FLAG_REQUIRED)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .value(UPDATED_VALUE)
            .alternativeValue(UPDATED_ALTERNATIVE_VALUE)
            .uuid(UPDATED_UUID);
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(updatedDocumentData);

        restDocumentDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentDataToMatchAllProperties(updatedDocumentData);
    }

    @Test
    @Transactional
    void putNonExistingDocumentData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentData.setId(longCount.incrementAndGet());

        // Create the DocumentData
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentData.setId(longCount.incrementAndGet());

        // Create the DocumentData
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentData.setId(longCount.incrementAndGet());

        // Create the DocumentData
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentDataWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentData = documentDataRepository.saveAndFlush(documentData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentData using partial update
        DocumentData partialUpdatedDocumentData = new DocumentData();
        partialUpdatedDocumentData.setId(documentData.getId());

        partialUpdatedDocumentData
            .fieldLabel(UPDATED_FIELD_LABEL)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldId(UPDATED_FIELD_ID)
            .fieldConfigs(UPDATED_FIELD_CONFIGS)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .flagRequired(UPDATED_FLAG_REQUIRED)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .updatedAt(UPDATED_UPDATED_AT)
            .value(UPDATED_VALUE)
            .uuid(UPDATED_UUID);

        restDocumentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentData))
            )
            .andExpect(status().isOk());

        // Validate the DocumentData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentDataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentData, documentData),
            getPersistedDocumentData(documentData)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentDataWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentData = documentDataRepository.saveAndFlush(documentData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentData using partial update
        DocumentData partialUpdatedDocumentData = new DocumentData();
        partialUpdatedDocumentData.setId(documentData.getId());

        partialUpdatedDocumentData
            .idFormVersion(UPDATED_ID_FORM_VERSION)
            .idFormParent(UPDATED_ID_FORM_PARENT)
            .fieldLabel(UPDATED_FIELD_LABEL)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldId(UPDATED_FIELD_ID)
            .fieldClass(UPDATED_FIELD_CLASS)
            .fieldPlaceholder(UPDATED_FIELD_PLACEHOLDER)
            .fieldOptions(UPDATED_FIELD_OPTIONS)
            .fieldConfigs(UPDATED_FIELD_CONFIGS)
            .description(UPDATED_DESCRIPTION)
            .orderPosition(UPDATED_ORDER_POSITION)
            .validationConfigs(UPDATED_VALIDATION_CONFIGS)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .flagRequired(UPDATED_FLAG_REQUIRED)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .value(UPDATED_VALUE)
            .alternativeValue(UPDATED_ALTERNATIVE_VALUE)
            .uuid(UPDATED_UUID);

        restDocumentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentData))
            )
            .andExpect(status().isOk());

        // Validate the DocumentData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentDataUpdatableFieldsEquals(partialUpdatedDocumentData, getPersistedDocumentData(partialUpdatedDocumentData));
    }

    @Test
    @Transactional
    void patchNonExistingDocumentData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentData.setId(longCount.incrementAndGet());

        // Create the DocumentData
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentData.setId(longCount.incrementAndGet());

        // Create the DocumentData
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentData.setId(longCount.incrementAndGet());

        // Create the DocumentData
        DocumentDataDTO documentDataDTO = documentDataMapper.toDto(documentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentData() throws Exception {
        // Initialize the database
        insertedDocumentData = documentDataRepository.saveAndFlush(documentData);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentData
        restDocumentDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentDataRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected DocumentData getPersistedDocumentData(DocumentData documentData) {
        return documentDataRepository.findById(documentData.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentDataToMatchAllProperties(DocumentData expectedDocumentData) {
        assertDocumentDataAllPropertiesEquals(expectedDocumentData, getPersistedDocumentData(expectedDocumentData));
    }

    protected void assertPersistedDocumentDataToMatchUpdatableProperties(DocumentData expectedDocumentData) {
        assertDocumentDataAllUpdatablePropertiesEquals(expectedDocumentData, getPersistedDocumentData(expectedDocumentData));
    }
}
