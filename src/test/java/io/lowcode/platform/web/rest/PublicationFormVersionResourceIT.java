package io.lowcode.platform.web.rest;

import static io.lowcode.platform.domain.PublicationFormVersionAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.PublicationFormVersion;
import io.lowcode.platform.repository.PublicationFormVersionRepository;
import io.lowcode.platform.service.dto.PublicationFormVersionDTO;
import io.lowcode.platform.service.mapper.PublicationFormVersionMapper;
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
 * Integration tests for the {@link PublicationFormVersionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationFormVersionResourceIT {

    private static final String DEFAULT_PUBLICATION_FORM_VERSION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_FORM_VERSION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATION_FORM_VERSION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_FORM_VERSION_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/publication-form-versions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PublicationFormVersionRepository publicationFormVersionRepository;

    @Autowired
    private PublicationFormVersionMapper publicationFormVersionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationFormVersionMockMvc;

    private PublicationFormVersion publicationFormVersion;

    private PublicationFormVersion insertedPublicationFormVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicationFormVersion createEntity() {
        return new PublicationFormVersion()
            .id(UUID.randomUUID().toString())
            .publicationFormVersionName(DEFAULT_PUBLICATION_FORM_VERSION_NAME)
            .publicationFormVersionCode(DEFAULT_PUBLICATION_FORM_VERSION_CODE)
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
    public static PublicationFormVersion createUpdatedEntity() {
        return new PublicationFormVersion()
            .id(UUID.randomUUID().toString())
            .publicationFormVersionName(UPDATED_PUBLICATION_FORM_VERSION_NAME)
            .publicationFormVersionCode(UPDATED_PUBLICATION_FORM_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        publicationFormVersion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPublicationFormVersion != null) {
            publicationFormVersionRepository.delete(insertedPublicationFormVersion);
            insertedPublicationFormVersion = null;
        }
    }

    @Test
    @Transactional
    void createPublicationFormVersion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PublicationFormVersion
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);
        var returnedPublicationFormVersionDTO = om.readValue(
            restPublicationFormVersionMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormVersionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PublicationFormVersionDTO.class
        );

        // Validate the PublicationFormVersion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPublicationFormVersion = publicationFormVersionMapper.toEntity(returnedPublicationFormVersionDTO);
        assertPublicationFormVersionUpdatableFieldsEquals(
            returnedPublicationFormVersion,
            getPersistedPublicationFormVersion(returnedPublicationFormVersion)
        );

        insertedPublicationFormVersion = returnedPublicationFormVersion;
    }

    @Test
    @Transactional
    void createPublicationFormVersionWithExistingId() throws Exception {
        // Create the PublicationFormVersion with an existing ID
        insertedPublicationFormVersion = publicationFormVersionRepository.saveAndFlush(publicationFormVersion);
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationFormVersionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormVersionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicationFormVersions() throws Exception {
        // Initialize the database
        publicationFormVersion.setId(UUID.randomUUID().toString());
        insertedPublicationFormVersion = publicationFormVersionRepository.saveAndFlush(publicationFormVersion);

        // Get all the publicationFormVersionList
        restPublicationFormVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicationFormVersion.getId())))
            .andExpect(jsonPath("$.[*].publicationFormVersionName").value(hasItem(DEFAULT_PUBLICATION_FORM_VERSION_NAME)))
            .andExpect(jsonPath("$.[*].publicationFormVersionCode").value(hasItem(DEFAULT_PUBLICATION_FORM_VERSION_CODE)))
            .andExpect(jsonPath("$.[*].flagActive").value(hasItem(DEFAULT_FLAG_ACTIVE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getPublicationFormVersion() throws Exception {
        // Initialize the database
        publicationFormVersion.setId(UUID.randomUUID().toString());
        insertedPublicationFormVersion = publicationFormVersionRepository.saveAndFlush(publicationFormVersion);

        // Get the publicationFormVersion
        restPublicationFormVersionMockMvc
            .perform(get(ENTITY_API_URL_ID, publicationFormVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicationFormVersion.getId()))
            .andExpect(jsonPath("$.publicationFormVersionName").value(DEFAULT_PUBLICATION_FORM_VERSION_NAME))
            .andExpect(jsonPath("$.publicationFormVersionCode").value(DEFAULT_PUBLICATION_FORM_VERSION_CODE))
            .andExpect(jsonPath("$.flagActive").value(DEFAULT_FLAG_ACTIVE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingPublicationFormVersion() throws Exception {
        // Get the publicationFormVersion
        restPublicationFormVersionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublicationFormVersion() throws Exception {
        // Initialize the database
        publicationFormVersion.setId(UUID.randomUUID().toString());
        insertedPublicationFormVersion = publicationFormVersionRepository.saveAndFlush(publicationFormVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationFormVersion
        PublicationFormVersion updatedPublicationFormVersion = publicationFormVersionRepository
            .findById(publicationFormVersion.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPublicationFormVersion are not directly saved in db
        em.detach(updatedPublicationFormVersion);
        updatedPublicationFormVersion
            .publicationFormVersionName(UPDATED_PUBLICATION_FORM_VERSION_NAME)
            .publicationFormVersionCode(UPDATED_PUBLICATION_FORM_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(updatedPublicationFormVersion);

        restPublicationFormVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationFormVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormVersionDTO))
            )
            .andExpect(status().isOk());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPublicationFormVersionToMatchAllProperties(updatedPublicationFormVersion);
    }

    @Test
    @Transactional
    void putNonExistingPublicationFormVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormVersion.setId(UUID.randomUUID().toString());

        // Create the PublicationFormVersion
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationFormVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationFormVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicationFormVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormVersion.setId(UUID.randomUUID().toString());

        // Create the PublicationFormVersion
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicationFormVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormVersion.setId(UUID.randomUUID().toString());

        // Create the PublicationFormVersion
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormVersionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormVersionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationFormVersionWithPatch() throws Exception {
        // Initialize the database
        publicationFormVersion.setId(UUID.randomUUID().toString());
        insertedPublicationFormVersion = publicationFormVersionRepository.saveAndFlush(publicationFormVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationFormVersion using partial update
        PublicationFormVersion partialUpdatedPublicationFormVersion = new PublicationFormVersion();
        partialUpdatedPublicationFormVersion.setId(publicationFormVersion.getId());

        partialUpdatedPublicationFormVersion
            .publicationFormVersionCode(UPDATED_PUBLICATION_FORM_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER);

        restPublicationFormVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationFormVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationFormVersion))
            )
            .andExpect(status().isOk());

        // Validate the PublicationFormVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationFormVersionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPublicationFormVersion, publicationFormVersion),
            getPersistedPublicationFormVersion(publicationFormVersion)
        );
    }

    @Test
    @Transactional
    void fullUpdatePublicationFormVersionWithPatch() throws Exception {
        // Initialize the database
        publicationFormVersion.setId(UUID.randomUUID().toString());
        insertedPublicationFormVersion = publicationFormVersionRepository.saveAndFlush(publicationFormVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationFormVersion using partial update
        PublicationFormVersion partialUpdatedPublicationFormVersion = new PublicationFormVersion();
        partialUpdatedPublicationFormVersion.setId(publicationFormVersion.getId());

        partialUpdatedPublicationFormVersion
            .publicationFormVersionName(UPDATED_PUBLICATION_FORM_VERSION_NAME)
            .publicationFormVersionCode(UPDATED_PUBLICATION_FORM_VERSION_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restPublicationFormVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationFormVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationFormVersion))
            )
            .andExpect(status().isOk());

        // Validate the PublicationFormVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationFormVersionUpdatableFieldsEquals(
            partialUpdatedPublicationFormVersion,
            getPersistedPublicationFormVersion(partialUpdatedPublicationFormVersion)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPublicationFormVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormVersion.setId(UUID.randomUUID().toString());

        // Create the PublicationFormVersion
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationFormVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicationFormVersionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationFormVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicationFormVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormVersion.setId(UUID.randomUUID().toString());

        // Create the PublicationFormVersion
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationFormVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicationFormVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormVersion.setId(UUID.randomUUID().toString());

        // Create the PublicationFormVersion
        PublicationFormVersionDTO publicationFormVersionDTO = publicationFormVersionMapper.toDto(publicationFormVersion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormVersionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(publicationFormVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationFormVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicationFormVersion() throws Exception {
        // Initialize the database
        publicationFormVersion.setId(UUID.randomUUID().toString());
        insertedPublicationFormVersion = publicationFormVersionRepository.saveAndFlush(publicationFormVersion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the publicationFormVersion
        restPublicationFormVersionMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicationFormVersion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return publicationFormVersionRepository.count();
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

    protected PublicationFormVersion getPersistedPublicationFormVersion(PublicationFormVersion publicationFormVersion) {
        return publicationFormVersionRepository.findById(publicationFormVersion.getId()).orElseThrow();
    }

    protected void assertPersistedPublicationFormVersionToMatchAllProperties(PublicationFormVersion expectedPublicationFormVersion) {
        assertPublicationFormVersionAllPropertiesEquals(
            expectedPublicationFormVersion,
            getPersistedPublicationFormVersion(expectedPublicationFormVersion)
        );
    }

    protected void assertPersistedPublicationFormVersionToMatchUpdatableProperties(PublicationFormVersion expectedPublicationFormVersion) {
        assertPublicationFormVersionAllUpdatablePropertiesEquals(
            expectedPublicationFormVersion,
            getPersistedPublicationFormVersion(expectedPublicationFormVersion)
        );
    }
}
