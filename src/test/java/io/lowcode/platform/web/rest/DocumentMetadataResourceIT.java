package io.lowcode.platform.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.DocumentMetadata;
import io.lowcode.platform.repository.DocumentMetadataRepository;
import io.lowcode.platform.service.dto.DocumentMetadataDTO;
import io.lowcode.platform.service.mapper.DocumentMetadataMapper;
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

import static io.lowcode.platform.domain.DocumentMetadataAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentMetadataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentMetadataResourceIT {

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

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/document-metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentMetadataRepository documentMetadataRepository;

    @Autowired
    private DocumentMetadataMapper documentMetadataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMetadataMockMvc;

    private DocumentMetadata documentMetadata;

    private DocumentMetadata insertedDocumentMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentMetadata createEntity() {
        return new DocumentMetadata()
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
            .uuid(DEFAULT_UUID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentMetadata createUpdatedEntity() {
        return new DocumentMetadata()
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
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        documentMetadata = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDocumentMetadata != null) {
            documentMetadataRepository.delete(insertedDocumentMetadata);
            insertedDocumentMetadata = null;
        }
    }

    @Test
    @Transactional
    void createDocumentMetadata() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentMetadata
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);
        var returnedDocumentMetadataDTO = om.readValue(
            restDocumentMetadataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentMetadataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentMetadataDTO.class
        );

        // Validate the DocumentMetadata in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentMetadata = documentMetadataMapper.toEntity(returnedDocumentMetadataDTO);
        assertDocumentMetadataUpdatableFieldsEquals(returnedDocumentMetadata, getPersistedDocumentMetadata(returnedDocumentMetadata));

        insertedDocumentMetadata = returnedDocumentMetadata;
    }

    @Test
    @Transactional
    void createDocumentMetadataWithExistingId() throws Exception {
        // Create the DocumentMetadata with an existing ID
        documentMetadata.setId(1L);
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMetadataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentMetadataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentMetadata() throws Exception {
        // Initialize the database
        insertedDocumentMetadata = documentMetadataRepository.saveAndFlush(documentMetadata);

        // Get all the documentMetadataList
        restDocumentMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentMetadata.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getDocumentMetadata() throws Exception {
        // Initialize the database
        insertedDocumentMetadata = documentMetadataRepository.saveAndFlush(documentMetadata);

        // Get the documentMetadata
        restDocumentMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, documentMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentMetadata.getId().intValue()))
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
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingDocumentMetadata() throws Exception {
        // Get the documentMetadata
        restDocumentMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentMetadata() throws Exception {
        // Initialize the database
        insertedDocumentMetadata = documentMetadataRepository.saveAndFlush(documentMetadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentMetadata
        DocumentMetadata updatedDocumentMetadata = documentMetadataRepository.findById(documentMetadata.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentMetadata are not directly saved in db
        em.detach(updatedDocumentMetadata);
        updatedDocumentMetadata
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
            .uuid(UPDATED_UUID);
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(updatedDocumentMetadata);

        restDocumentMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentMetadataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentMetadataDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentMetadataToMatchAllProperties(updatedDocumentMetadata);
    }

    @Test
    @Transactional
    void putNonExistingDocumentMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadata.setId(longCount.incrementAndGet());

        // Create the DocumentMetadata
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentMetadataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadata.setId(longCount.incrementAndGet());

        // Create the DocumentMetadata
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadata.setId(longCount.incrementAndGet());

        // Create the DocumentMetadata
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentMetadataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentMetadataWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentMetadata = documentMetadataRepository.saveAndFlush(documentMetadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentMetadata using partial update
        DocumentMetadata partialUpdatedDocumentMetadata = new DocumentMetadata();
        partialUpdatedDocumentMetadata.setId(documentMetadata.getId());

        partialUpdatedDocumentMetadata
            .fieldLabel(UPDATED_FIELD_LABEL)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldOptions(UPDATED_FIELD_OPTIONS)
            .description(UPDATED_DESCRIPTION)
            .validationConfigs(UPDATED_VALIDATION_CONFIGS)
            .flagRequired(UPDATED_FLAG_REQUIRED)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restDocumentMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentMetadata.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentMetadata))
            )
            .andExpect(status().isOk());

        // Validate the DocumentMetadata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentMetadataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentMetadata, documentMetadata),
            getPersistedDocumentMetadata(documentMetadata)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentMetadataWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentMetadata = documentMetadataRepository.saveAndFlush(documentMetadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentMetadata using partial update
        DocumentMetadata partialUpdatedDocumentMetadata = new DocumentMetadata();
        partialUpdatedDocumentMetadata.setId(documentMetadata.getId());

        partialUpdatedDocumentMetadata
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
            .uuid(UPDATED_UUID);

        restDocumentMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentMetadata.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentMetadata))
            )
            .andExpect(status().isOk());

        // Validate the DocumentMetadata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentMetadataUpdatableFieldsEquals(
            partialUpdatedDocumentMetadata,
            getPersistedDocumentMetadata(partialUpdatedDocumentMetadata)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDocumentMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadata.setId(longCount.incrementAndGet());

        // Create the DocumentMetadata
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentMetadataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadata.setId(longCount.incrementAndGet());

        // Create the DocumentMetadata
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadata.setId(longCount.incrementAndGet());

        // Create the DocumentMetadata
        DocumentMetadataDTO documentMetadataDTO = documentMetadataMapper.toDto(documentMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentMetadataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentMetadata() throws Exception {
        // Initialize the database
        insertedDocumentMetadata = documentMetadataRepository.saveAndFlush(documentMetadata);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentMetadata
        restDocumentMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentMetadata.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentMetadataRepository.count();
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

    protected DocumentMetadata getPersistedDocumentMetadata(DocumentMetadata documentMetadata) {
        return documentMetadataRepository.findById(documentMetadata.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentMetadataToMatchAllProperties(DocumentMetadata expectedDocumentMetadata) {
        assertDocumentMetadataAllPropertiesEquals(expectedDocumentMetadata, getPersistedDocumentMetadata(expectedDocumentMetadata));
    }

    protected void assertPersistedDocumentMetadataToMatchUpdatableProperties(DocumentMetadata expectedDocumentMetadata) {
        assertDocumentMetadataAllUpdatablePropertiesEquals(
            expectedDocumentMetadata,
            getPersistedDocumentMetadata(expectedDocumentMetadata)
        );
    }
}
