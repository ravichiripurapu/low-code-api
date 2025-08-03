package io.lowcode.platform.web.rest;

import static io.lowcode.platform.domain.PublicationStatusAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.PublicationStatus;
import io.lowcode.platform.repository.PublicationStatusRepository;
import io.lowcode.platform.service.dto.PublicationStatusDTO;
import io.lowcode.platform.service.mapper.PublicationStatusMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PublicationStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationStatusResourceIT {

    private static final String DEFAULT_PUBLICATION_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_STATUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATION_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_STATUS_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/publication-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PublicationStatusRepository publicationStatusRepository;

    @Autowired
    private PublicationStatusMapper publicationStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationStatusMockMvc;

    private PublicationStatus publicationStatus;

    private PublicationStatus insertedPublicationStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicationStatus createEntity() {
        return new PublicationStatus()
            .id(UUID.randomUUID().toString())
            .publicationStatusName(DEFAULT_PUBLICATION_STATUS_NAME)
            .publicationStatusCode(DEFAULT_PUBLICATION_STATUS_CODE)
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
    public static PublicationStatus createUpdatedEntity() {
        return new PublicationStatus()
            .id(UUID.randomUUID().toString())
            .publicationStatusName(UPDATED_PUBLICATION_STATUS_NAME)
            .publicationStatusCode(UPDATED_PUBLICATION_STATUS_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        publicationStatus = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPublicationStatus != null) {
            publicationStatusRepository.delete(insertedPublicationStatus);
            insertedPublicationStatus = null;
        }
    }

    @Test
    @Transactional
    void createPublicationStatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PublicationStatus
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);
        var returnedPublicationStatusDTO = om.readValue(
            restPublicationStatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationStatusDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PublicationStatusDTO.class
        );

        // Validate the PublicationStatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPublicationStatus = publicationStatusMapper.toEntity(returnedPublicationStatusDTO);
        assertPublicationStatusUpdatableFieldsEquals(returnedPublicationStatus, getPersistedPublicationStatus(returnedPublicationStatus));

        insertedPublicationStatus = returnedPublicationStatus;
    }

    @Test
    @Transactional
    void createPublicationStatusWithExistingId() throws Exception {
        // Create the PublicationStatus with an existing ID
        insertedPublicationStatus = publicationStatusRepository.saveAndFlush(publicationStatus);
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicationStatuses() throws Exception {
        // Initialize the database
        publicationStatus.setId(UUID.randomUUID().toString());
        insertedPublicationStatus = publicationStatusRepository.saveAndFlush(publicationStatus);

        // Get all the publicationStatusList
        restPublicationStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicationStatus.getId())))
            .andExpect(jsonPath("$.[*].publicationStatusName").value(hasItem(DEFAULT_PUBLICATION_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].publicationStatusCode").value(hasItem(DEFAULT_PUBLICATION_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].flagActive").value(hasItem(DEFAULT_FLAG_ACTIVE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getPublicationStatus() throws Exception {
        // Initialize the database
        publicationStatus.setId(UUID.randomUUID().toString());
        insertedPublicationStatus = publicationStatusRepository.saveAndFlush(publicationStatus);

        // Get the publicationStatus
        restPublicationStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, publicationStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicationStatus.getId()))
            .andExpect(jsonPath("$.publicationStatusName").value(DEFAULT_PUBLICATION_STATUS_NAME))
            .andExpect(jsonPath("$.publicationStatusCode").value(DEFAULT_PUBLICATION_STATUS_CODE))
            .andExpect(jsonPath("$.flagActive").value(DEFAULT_FLAG_ACTIVE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingPublicationStatus() throws Exception {
        // Get the publicationStatus
        restPublicationStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublicationStatus() throws Exception {
        // Initialize the database
        publicationStatus.setId(UUID.randomUUID().toString());
        insertedPublicationStatus = publicationStatusRepository.saveAndFlush(publicationStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationStatus
        PublicationStatus updatedPublicationStatus = publicationStatusRepository.findById(publicationStatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPublicationStatus are not directly saved in db
        em.detach(updatedPublicationStatus);
        updatedPublicationStatus
            .publicationStatusName(UPDATED_PUBLICATION_STATUS_NAME)
            .publicationStatusCode(UPDATED_PUBLICATION_STATUS_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(updatedPublicationStatus);

        restPublicationStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPublicationStatusToMatchAllProperties(updatedPublicationStatus);
    }

    @Test
    @Transactional
    void putNonExistingPublicationStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationStatus.setId(UUID.randomUUID().toString());

        // Create the PublicationStatus
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicationStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationStatus.setId(UUID.randomUUID().toString());

        // Create the PublicationStatus
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicationStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationStatus.setId(UUID.randomUUID().toString());

        // Create the PublicationStatus
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationStatusWithPatch() throws Exception {
        // Initialize the database
        publicationStatus.setId(UUID.randomUUID().toString());
        insertedPublicationStatus = publicationStatusRepository.saveAndFlush(publicationStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationStatus using partial update
        PublicationStatus partialUpdatedPublicationStatus = new PublicationStatus();
        partialUpdatedPublicationStatus.setId(publicationStatus.getId());

        partialUpdatedPublicationStatus
            .publicationStatusName(UPDATED_PUBLICATION_STATUS_NAME)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .uuid(UPDATED_UUID);

        restPublicationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationStatus))
            )
            .andExpect(status().isOk());

        // Validate the PublicationStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationStatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPublicationStatus, publicationStatus),
            getPersistedPublicationStatus(publicationStatus)
        );
    }

    @Test
    @Transactional
    void fullUpdatePublicationStatusWithPatch() throws Exception {
        // Initialize the database
        publicationStatus.setId(UUID.randomUUID().toString());
        insertedPublicationStatus = publicationStatusRepository.saveAndFlush(publicationStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationStatus using partial update
        PublicationStatus partialUpdatedPublicationStatus = new PublicationStatus();
        partialUpdatedPublicationStatus.setId(publicationStatus.getId());

        partialUpdatedPublicationStatus
            .publicationStatusName(UPDATED_PUBLICATION_STATUS_NAME)
            .publicationStatusCode(UPDATED_PUBLICATION_STATUS_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restPublicationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationStatus))
            )
            .andExpect(status().isOk());

        // Validate the PublicationStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationStatusUpdatableFieldsEquals(
            partialUpdatedPublicationStatus,
            getPersistedPublicationStatus(partialUpdatedPublicationStatus)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPublicationStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationStatus.setId(UUID.randomUUID().toString());

        // Create the PublicationStatus
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicationStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicationStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationStatus.setId(UUID.randomUUID().toString());

        // Create the PublicationStatus
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicationStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationStatus.setId(UUID.randomUUID().toString());

        // Create the PublicationStatus
        PublicationStatusDTO publicationStatusDTO = publicationStatusMapper.toDto(publicationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationStatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(publicationStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicationStatus() throws Exception {
        // Initialize the database
        publicationStatus.setId(UUID.randomUUID().toString());
        insertedPublicationStatus = publicationStatusRepository.saveAndFlush(publicationStatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the publicationStatus
        restPublicationStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicationStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return publicationStatusRepository.count();
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

    protected PublicationStatus getPersistedPublicationStatus(PublicationStatus publicationStatus) {
        return publicationStatusRepository.findById(publicationStatus.getId()).orElseThrow();
    }

    protected void assertPersistedPublicationStatusToMatchAllProperties(PublicationStatus expectedPublicationStatus) {
        assertPublicationStatusAllPropertiesEquals(expectedPublicationStatus, getPersistedPublicationStatus(expectedPublicationStatus));
    }

    protected void assertPersistedPublicationStatusToMatchUpdatableProperties(PublicationStatus expectedPublicationStatus) {
        assertPublicationStatusAllUpdatablePropertiesEquals(
            expectedPublicationStatus,
            getPersistedPublicationStatus(expectedPublicationStatus)
        );
    }
}
