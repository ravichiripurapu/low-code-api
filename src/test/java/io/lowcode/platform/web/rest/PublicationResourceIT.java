package io.lowcode.platform.web.rest;

import static io.lowcode.platform.domain.PublicationAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.Publication;
import io.lowcode.platform.repository.PublicationRepository;
import io.lowcode.platform.service.dto.PublicationDTO;
import io.lowcode.platform.service.mapper.PublicationMapper;
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
 * Integration tests for the {@link PublicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PUBLICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUBLICATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

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

    private static final String ENTITY_API_URL = "/api/publications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationMockMvc;

    private Publication publication;

    private Publication insertedPublication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publication createEntity() {
        return new Publication()
            .id(UUID.randomUUID().toString())
            .title(DEFAULT_TITLE)
            .publicationDate(DEFAULT_PUBLICATION_DATE)
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
    public static Publication createUpdatedEntity() {
        return new Publication()
            .id(UUID.randomUUID().toString())
            .title(UPDATED_TITLE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        publication = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPublication != null) {
            publicationRepository.delete(insertedPublication);
            insertedPublication = null;
        }
    }

    @Test
    @Transactional
    void createPublication() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);
        var returnedPublicationDTO = om.readValue(
            restPublicationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PublicationDTO.class
        );

        // Validate the Publication in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPublication = publicationMapper.toEntity(returnedPublicationDTO);
        assertPublicationUpdatableFieldsEquals(returnedPublication, getPersistedPublication(returnedPublication));

        insertedPublication = returnedPublication;
    }

    @Test
    @Transactional
    void createPublicationWithExistingId() throws Exception {
        // Create the Publication with an existing ID
        insertedPublication = publicationRepository.saveAndFlush(publication);
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublications() throws Exception {
        // Initialize the database
        publication.setId(UUID.randomUUID().toString());
        insertedPublication = publicationRepository.saveAndFlush(publication);

        // Get all the publicationList
        restPublicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publication.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(sameInstant(DEFAULT_PUBLICATION_DATE))))
            .andExpect(jsonPath("$.[*].flagActive").value(hasItem(DEFAULT_FLAG_ACTIVE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getPublication() throws Exception {
        // Initialize the database
        publication.setId(UUID.randomUUID().toString());
        insertedPublication = publicationRepository.saveAndFlush(publication);

        // Get the publication
        restPublicationMockMvc
            .perform(get(ENTITY_API_URL_ID, publication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publication.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.publicationDate").value(sameInstant(DEFAULT_PUBLICATION_DATE)))
            .andExpect(jsonPath("$.flagActive").value(DEFAULT_FLAG_ACTIVE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingPublication() throws Exception {
        // Get the publication
        restPublicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublication() throws Exception {
        // Initialize the database
        publication.setId(UUID.randomUUID().toString());
        insertedPublication = publicationRepository.saveAndFlush(publication);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publication
        Publication updatedPublication = publicationRepository.findById(publication.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPublication are not directly saved in db
        em.detach(updatedPublication);
        updatedPublication
            .title(UPDATED_TITLE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        PublicationDTO publicationDTO = publicationMapper.toDto(updatedPublication);

        restPublicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPublicationToMatchAllProperties(updatedPublication);
    }

    @Test
    @Transactional
    void putNonExistingPublication() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publication.setId(UUID.randomUUID().toString());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublication() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publication.setId(UUID.randomUUID().toString());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublication() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publication.setId(UUID.randomUUID().toString());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationWithPatch() throws Exception {
        // Initialize the database
        publication.setId(UUID.randomUUID().toString());
        insertedPublication = publicationRepository.saveAndFlush(publication);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publication using partial update
        Publication partialUpdatedPublication = new Publication();
        partialUpdatedPublication.setId(publication.getId());

        partialUpdatedPublication.publicationDate(UPDATED_PUBLICATION_DATE).createUser(UPDATED_CREATE_USER).updatedAt(UPDATED_UPDATED_AT);

        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublication.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublication))
            )
            .andExpect(status().isOk());

        // Validate the Publication in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPublication, publication),
            getPersistedPublication(publication)
        );
    }

    @Test
    @Transactional
    void fullUpdatePublicationWithPatch() throws Exception {
        // Initialize the database
        publication.setId(UUID.randomUUID().toString());
        insertedPublication = publicationRepository.saveAndFlush(publication);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publication using partial update
        Publication partialUpdatedPublication = new Publication();
        partialUpdatedPublication.setId(publication.getId());

        partialUpdatedPublication
            .title(UPDATED_TITLE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublication.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublication))
            )
            .andExpect(status().isOk());

        // Validate the Publication in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationUpdatableFieldsEquals(partialUpdatedPublication, getPersistedPublication(partialUpdatedPublication));
    }

    @Test
    @Transactional
    void patchNonExistingPublication() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publication.setId(UUID.randomUUID().toString());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublication() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publication.setId(UUID.randomUUID().toString());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublication() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publication.setId(UUID.randomUUID().toString());

        // Create the Publication
        PublicationDTO publicationDTO = publicationMapper.toDto(publication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(publicationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publication in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublication() throws Exception {
        // Initialize the database
        publication.setId(UUID.randomUUID().toString());
        insertedPublication = publicationRepository.saveAndFlush(publication);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the publication
        restPublicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, publication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return publicationRepository.count();
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

    protected Publication getPersistedPublication(Publication publication) {
        return publicationRepository.findById(publication.getId()).orElseThrow();
    }

    protected void assertPersistedPublicationToMatchAllProperties(Publication expectedPublication) {
        assertPublicationAllPropertiesEquals(expectedPublication, getPersistedPublication(expectedPublication));
    }

    protected void assertPersistedPublicationToMatchUpdatableProperties(Publication expectedPublication) {
        assertPublicationAllUpdatablePropertiesEquals(expectedPublication, getPersistedPublication(expectedPublication));
    }
}
