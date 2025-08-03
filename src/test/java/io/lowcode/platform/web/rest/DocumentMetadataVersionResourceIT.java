package io.lowcode.platform.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.DocumentMetadataVersion;
import io.lowcode.platform.repository.DocumentMetadataVersionRepository;
import io.lowcode.platform.service.dto.DocumentMetadataVersionDTO;
import io.lowcode.platform.service.mapper.DocumentMetadataVersionMapper;
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

import static io.lowcode.platform.domain.DocumentMetadataVersionAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentMetadataVersionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentMetadataVersionResourceIT {

    private static final String DEFAULT_DOCUMENT_METADATA_VERSION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_METADATA_VERSION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_METADATA_VERSION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_METADATA_VERSION_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/document-metadata-versions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentMetadataVersionRepository documentMetadataVersionRepository;

    @Autowired
    private DocumentMetadataVersionMapper documentMetadataVersionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMetadataVersionMockMvc;

    private DocumentMetadataVersion documentMetadataVersion;

    private DocumentMetadataVersion insertedDocumentMetadataVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentMetadataVersion createEntity() {
        return new DocumentMetadataVersion()
            .id(UUID.randomUUID().toString())
            .documentMetadataVersionName(DEFAULT_DOCUMENT_METADATA_VERSION_NAME)
            .documentMetadataVersionCode(DEFAULT_DOCUMENT_METADATA_VERSION_CODE)
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
    public static DocumentMetadataVersion createUpdatedEntity() {
        return new DocumentMetadataVersion()
            .id(UUID.randomUUID().toString())
            .documentMetadataVersionName(UPDATED_DOCUMENT_METADATA_VERSION_NAME)
            .documentMetadataVersionCode(UPDATED_DOCUMENT_METADATA_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        documentMetadataVersion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDocumentMetadataVersion != null) {
            documentMetadataVersionRepository.delete(insertedDocumentMetadataVersion);
            insertedDocumentMetadataVersion = null;
        }
    }

    @Test
    @Transactional
    void createDocumentMetadataVersion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentMetadataVersion
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);
        var returnedDocumentMetadataVersionDTO = om.readValue(
            restDocumentMetadataVersionMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentMetadataVersionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentMetadataVersionDTO.class
        );

        // Validate the DocumentMetadataVersion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentMetadataVersion = documentMetadataVersionMapper.toEntity(returnedDocumentMetadataVersionDTO);
        assertDocumentMetadataVersionUpdatableFieldsEquals(
            returnedDocumentMetadataVersion,
            getPersistedDocumentMetadataVersion(returnedDocumentMetadataVersion)
        );

        insertedDocumentMetadataVersion = returnedDocumentMetadataVersion;
    }

    @Test
    @Transactional
    void createDocumentMetadataVersionWithExistingId() throws Exception {
        // Create the DocumentMetadataVersion with an existing ID
        insertedDocumentMetadataVersion = documentMetadataVersionRepository.saveAndFlush(documentMetadataVersion);
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMetadataVersionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentMetadataVersionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentMetadataVersions() throws Exception {
        // Initialize the database
        documentMetadataVersion.setId(UUID.randomUUID().toString());
        insertedDocumentMetadataVersion = documentMetadataVersionRepository.saveAndFlush(documentMetadataVersion);

        // Get all the documentMetadataVersionList
        restDocumentMetadataVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentMetadataVersion.getId())))
            .andExpect(jsonPath("$.[*].documentMetadataVersionName").value(hasItem(DEFAULT_DOCUMENT_METADATA_VERSION_NAME)))
            .andExpect(jsonPath("$.[*].documentMetadataVersionCode").value(hasItem(DEFAULT_DOCUMENT_METADATA_VERSION_CODE)))
            .andExpect(jsonPath("$.[*].flagActive").value(hasItem(DEFAULT_FLAG_ACTIVE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getDocumentMetadataVersion() throws Exception {
        // Initialize the database
        documentMetadataVersion.setId(UUID.randomUUID().toString());
        insertedDocumentMetadataVersion = documentMetadataVersionRepository.saveAndFlush(documentMetadataVersion);

        // Get the documentMetadataVersion
        restDocumentMetadataVersionMockMvc
            .perform(get(ENTITY_API_URL_ID, documentMetadataVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentMetadataVersion.getId()))
            .andExpect(jsonPath("$.documentMetadataVersionName").value(DEFAULT_DOCUMENT_METADATA_VERSION_NAME))
            .andExpect(jsonPath("$.documentMetadataVersionCode").value(DEFAULT_DOCUMENT_METADATA_VERSION_CODE))
            .andExpect(jsonPath("$.flagActive").value(DEFAULT_FLAG_ACTIVE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingDocumentMetadataVersion() throws Exception {
        // Get the documentMetadataVersion
        restDocumentMetadataVersionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentMetadataVersion() throws Exception {
        // Initialize the database
        documentMetadataVersion.setId(UUID.randomUUID().toString());
        insertedDocumentMetadataVersion = documentMetadataVersionRepository.saveAndFlush(documentMetadataVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentMetadataVersion
        DocumentMetadataVersion updatedDocumentMetadataVersion = documentMetadataVersionRepository
            .findById(documentMetadataVersion.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentMetadataVersion are not directly saved in db
        em.detach(updatedDocumentMetadataVersion);
        updatedDocumentMetadataVersion
            .documentMetadataVersionName(UPDATED_DOCUMENT_METADATA_VERSION_NAME)
            .documentMetadataVersionCode(UPDATED_DOCUMENT_METADATA_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(updatedDocumentMetadataVersion);

        restDocumentMetadataVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentMetadataVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentMetadataVersionDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentMetadataVersionToMatchAllProperties(updatedDocumentMetadataVersion);
    }

    @Test
    @Transactional
    void putNonExistingDocumentMetadataVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadataVersion.setId(UUID.randomUUID().toString());

        // Create the DocumentMetadataVersion
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMetadataVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentMetadataVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentMetadataVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentMetadataVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadataVersion.setId(UUID.randomUUID().toString());

        // Create the DocumentMetadataVersion
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentMetadataVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentMetadataVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadataVersion.setId(UUID.randomUUID().toString());

        // Create the DocumentMetadataVersion
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataVersionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentMetadataVersionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentMetadataVersionWithPatch() throws Exception {
        // Initialize the database
        documentMetadataVersion.setId(UUID.randomUUID().toString());
        insertedDocumentMetadataVersion = documentMetadataVersionRepository.saveAndFlush(documentMetadataVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentMetadataVersion using partial update
        DocumentMetadataVersion partialUpdatedDocumentMetadataVersion = new DocumentMetadataVersion();
        partialUpdatedDocumentMetadataVersion.setId(documentMetadataVersion.getId());

        partialUpdatedDocumentMetadataVersion
            .documentMetadataVersionCode(UPDATED_DOCUMENT_METADATA_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER);

        restDocumentMetadataVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentMetadataVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentMetadataVersion))
            )
            .andExpect(status().isOk());

        // Validate the DocumentMetadataVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentMetadataVersionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentMetadataVersion, documentMetadataVersion),
            getPersistedDocumentMetadataVersion(documentMetadataVersion)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentMetadataVersionWithPatch() throws Exception {
        // Initialize the database
        documentMetadataVersion.setId(UUID.randomUUID().toString());
        insertedDocumentMetadataVersion = documentMetadataVersionRepository.saveAndFlush(documentMetadataVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentMetadataVersion using partial update
        DocumentMetadataVersion partialUpdatedDocumentMetadataVersion = new DocumentMetadataVersion();
        partialUpdatedDocumentMetadataVersion.setId(documentMetadataVersion.getId());

        partialUpdatedDocumentMetadataVersion
            .documentMetadataVersionName(UPDATED_DOCUMENT_METADATA_VERSION_NAME)
            .documentMetadataVersionCode(UPDATED_DOCUMENT_METADATA_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restDocumentMetadataVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentMetadataVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentMetadataVersion))
            )
            .andExpect(status().isOk());

        // Validate the DocumentMetadataVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentMetadataVersionUpdatableFieldsEquals(
            partialUpdatedDocumentMetadataVersion,
            getPersistedDocumentMetadataVersion(partialUpdatedDocumentMetadataVersion)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDocumentMetadataVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadataVersion.setId(UUID.randomUUID().toString());

        // Create the DocumentMetadataVersion
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMetadataVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentMetadataVersionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentMetadataVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentMetadataVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadataVersion.setId(UUID.randomUUID().toString());

        // Create the DocumentMetadataVersion
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentMetadataVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentMetadataVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentMetadataVersion.setId(UUID.randomUUID().toString());

        // Create the DocumentMetadataVersion
        DocumentMetadataVersionDTO documentMetadataVersionDTO = documentMetadataVersionMapper.toDto(documentMetadataVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMetadataVersionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentMetadataVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentMetadataVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentMetadataVersion() throws Exception {
        // Initialize the database
        documentMetadataVersion.setId(UUID.randomUUID().toString());
        insertedDocumentMetadataVersion = documentMetadataVersionRepository.saveAndFlush(documentMetadataVersion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentMetadataVersion
        restDocumentMetadataVersionMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentMetadataVersion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentMetadataVersionRepository.count();
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

    protected DocumentMetadataVersion getPersistedDocumentMetadataVersion(DocumentMetadataVersion documentMetadataVersion) {
        return documentMetadataVersionRepository.findById(documentMetadataVersion.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentMetadataVersionToMatchAllProperties(DocumentMetadataVersion expectedDocumentMetadataVersion) {
        assertDocumentMetadataVersionAllPropertiesEquals(
            expectedDocumentMetadataVersion,
            getPersistedDocumentMetadataVersion(expectedDocumentMetadataVersion)
        );
    }

    protected void assertPersistedDocumentMetadataVersionToMatchUpdatableProperties(
        DocumentMetadataVersion expectedDocumentMetadataVersion
    ) {
        assertDocumentMetadataVersionAllUpdatablePropertiesEquals(
            expectedDocumentMetadataVersion,
            getPersistedDocumentMetadataVersion(expectedDocumentMetadataVersion)
        );
    }
}
