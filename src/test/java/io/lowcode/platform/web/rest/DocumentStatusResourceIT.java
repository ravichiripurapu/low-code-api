package io.lowcode.platform.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.DocumentStatus;
import io.lowcode.platform.repository.DocumentStatusRepository;
import io.lowcode.platform.service.dto.DocumentStatusDTO;
import io.lowcode.platform.service.mapper.DocumentStatusMapper;
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
import java.util.UUID;

import static io.lowcode.platform.domain.DocumentStatusAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/document-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentStatusRepository documentStatusRepository;

    @Autowired
    private DocumentStatusMapper documentStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentStatusMockMvc;

    private DocumentStatus documentStatus;

    private DocumentStatus insertedDocumentStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStatus createEntity() {
        return new DocumentStatus()
            .id(UUID.randomUUID().toString())
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
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
    public static DocumentStatus createUpdatedEntity() {
        return new DocumentStatus()
            .id(UUID.randomUUID().toString())
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        documentStatus = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDocumentStatus != null) {
            documentStatusRepository.delete(insertedDocumentStatus);
            insertedDocumentStatus = null;
        }
    }

    @Test
    @Transactional
    void createDocumentStatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentStatus
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);
        var returnedDocumentStatusDTO = om.readValue(
            restDocumentStatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentStatusDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentStatusDTO.class
        );

        // Validate the DocumentStatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentStatus = documentStatusMapper.toEntity(returnedDocumentStatusDTO);
        assertDocumentStatusUpdatableFieldsEquals(returnedDocumentStatus, getPersistedDocumentStatus(returnedDocumentStatus));

        insertedDocumentStatus = returnedDocumentStatus;
    }

    @Test
    @Transactional
    void createDocumentStatusWithExistingId() throws Exception {
        // Create the DocumentStatus with an existing ID
        insertedDocumentStatus = documentStatusRepository.saveAndFlush(documentStatus);
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentStatuses() throws Exception {
        // Initialize the database
        documentStatus.setId(UUID.randomUUID().toString());
        insertedDocumentStatus = documentStatusRepository.saveAndFlush(documentStatus);

        // Get all the documentStatusList
        restDocumentStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentStatus.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].flagActive").value(hasItem(DEFAULT_FLAG_ACTIVE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getDocumentStatus() throws Exception {
        // Initialize the database
        documentStatus.setId(UUID.randomUUID().toString());
        insertedDocumentStatus = documentStatusRepository.saveAndFlush(documentStatus);

        // Get the documentStatus
        restDocumentStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, documentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentStatus.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.flagActive").value(DEFAULT_FLAG_ACTIVE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingDocumentStatus() throws Exception {
        // Get the documentStatus
        restDocumentStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentStatus() throws Exception {
        // Initialize the database
        documentStatus.setId(UUID.randomUUID().toString());
        insertedDocumentStatus = documentStatusRepository.saveAndFlush(documentStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentStatus
        DocumentStatus updatedDocumentStatus = documentStatusRepository.findById(documentStatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentStatus are not directly saved in db
        em.detach(updatedDocumentStatus);
        updatedDocumentStatus
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(updatedDocumentStatus);

        restDocumentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentStatusToMatchAllProperties(updatedDocumentStatus);
    }

    @Test
    @Transactional
    void putNonExistingDocumentStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentStatus.setId(UUID.randomUUID().toString());

        // Create the DocumentStatus
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentStatus.setId(UUID.randomUUID().toString());

        // Create the DocumentStatus
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentStatus.setId(UUID.randomUUID().toString());

        // Create the DocumentStatus
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentStatusWithPatch() throws Exception {
        // Initialize the database
        documentStatus.setId(UUID.randomUUID().toString());
        insertedDocumentStatus = documentStatusRepository.saveAndFlush(documentStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentStatus using partial update
        DocumentStatus partialUpdatedDocumentStatus = new DocumentStatus();
        partialUpdatedDocumentStatus.setId(documentStatus.getId());

        partialUpdatedDocumentStatus.name(UPDATED_NAME).code(UPDATED_CODE).flagActive(UPDATED_FLAG_ACTIVE).updateUser(UPDATED_UPDATE_USER);

        restDocumentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentStatus))
            )
            .andExpect(status().isOk());

        // Validate the DocumentStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentStatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentStatus, documentStatus),
            getPersistedDocumentStatus(documentStatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentStatusWithPatch() throws Exception {
        // Initialize the database
        documentStatus.setId(UUID.randomUUID().toString());
        insertedDocumentStatus = documentStatusRepository.saveAndFlush(documentStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentStatus using partial update
        DocumentStatus partialUpdatedDocumentStatus = new DocumentStatus();
        partialUpdatedDocumentStatus.setId(documentStatus.getId());

        partialUpdatedDocumentStatus
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restDocumentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentStatus))
            )
            .andExpect(status().isOk());

        // Validate the DocumentStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentStatusUpdatableFieldsEquals(partialUpdatedDocumentStatus, getPersistedDocumentStatus(partialUpdatedDocumentStatus));
    }

    @Test
    @Transactional
    void patchNonExistingDocumentStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentStatus.setId(UUID.randomUUID().toString());

        // Create the DocumentStatus
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentStatus.setId(UUID.randomUUID().toString());

        // Create the DocumentStatus
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentStatus.setId(UUID.randomUUID().toString());

        // Create the DocumentStatus
        DocumentStatusDTO documentStatusDTO = documentStatusMapper.toDto(documentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentStatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentStatus() throws Exception {
        // Initialize the database
        documentStatus.setId(UUID.randomUUID().toString());
        insertedDocumentStatus = documentStatusRepository.saveAndFlush(documentStatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentStatus
        restDocumentStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentStatusRepository.count();
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

    protected DocumentStatus getPersistedDocumentStatus(DocumentStatus documentStatus) {
        return documentStatusRepository.findById(documentStatus.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentStatusToMatchAllProperties(DocumentStatus expectedDocumentStatus) {
        assertDocumentStatusAllPropertiesEquals(expectedDocumentStatus, getPersistedDocumentStatus(expectedDocumentStatus));
    }

    protected void assertPersistedDocumentStatusToMatchUpdatableProperties(DocumentStatus expectedDocumentStatus) {
        assertDocumentStatusAllUpdatablePropertiesEquals(expectedDocumentStatus, getPersistedDocumentStatus(expectedDocumentStatus));
    }
}
