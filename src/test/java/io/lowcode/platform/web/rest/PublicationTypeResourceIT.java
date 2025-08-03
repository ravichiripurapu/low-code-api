package io.lowcode.platform.web.rest;

import static io.lowcode.platform.domain.PublicationTypeAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.PublicationType;
import io.lowcode.platform.repository.PublicationTypeRepository;
import io.lowcode.platform.service.dto.PublicationTypeDTO;
import io.lowcode.platform.service.mapper.PublicationTypeMapper;
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
 * Integration tests for the {@link PublicationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationTypeResourceIT {

    private static final String DEFAULT_PUBLICATION_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLICATION_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLICATION_TYPE_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/publication-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PublicationTypeRepository publicationTypeRepository;

    @Autowired
    private PublicationTypeMapper publicationTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationTypeMockMvc;

    private PublicationType publicationType;

    private PublicationType insertedPublicationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicationType createEntity() {
        return new PublicationType()
            .id(UUID.randomUUID().toString())
            .publicationTypeName(DEFAULT_PUBLICATION_TYPE_NAME)
            .publicationTypeCode(DEFAULT_PUBLICATION_TYPE_CODE)
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
    public static PublicationType createUpdatedEntity() {
        return new PublicationType()
            .id(UUID.randomUUID().toString())
            .publicationTypeName(UPDATED_PUBLICATION_TYPE_NAME)
            .publicationTypeCode(UPDATED_PUBLICATION_TYPE_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
    }

    @BeforeEach
    void initTest() {
        publicationType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPublicationType != null) {
            publicationTypeRepository.delete(insertedPublicationType);
            insertedPublicationType = null;
        }
    }

    @Test
    @Transactional
    void createPublicationType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PublicationType
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);
        var returnedPublicationTypeDTO = om.readValue(
            restPublicationTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationTypeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PublicationTypeDTO.class
        );

        // Validate the PublicationType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPublicationType = publicationTypeMapper.toEntity(returnedPublicationTypeDTO);
        assertPublicationTypeUpdatableFieldsEquals(returnedPublicationType, getPersistedPublicationType(returnedPublicationType));

        insertedPublicationType = returnedPublicationType;
    }

    @Test
    @Transactional
    void createPublicationTypeWithExistingId() throws Exception {
        // Create the PublicationType with an existing ID
        insertedPublicationType = publicationTypeRepository.saveAndFlush(publicationType);
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicationTypes() throws Exception {
        // Initialize the database
        publicationType.setId(UUID.randomUUID().toString());
        insertedPublicationType = publicationTypeRepository.saveAndFlush(publicationType);

        // Get all the publicationTypeList
        restPublicationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicationType.getId())))
            .andExpect(jsonPath("$.[*].publicationTypeName").value(hasItem(DEFAULT_PUBLICATION_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].publicationTypeCode").value(hasItem(DEFAULT_PUBLICATION_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].flagActive").value(hasItem(DEFAULT_FLAG_ACTIVE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));
    }

    @Test
    @Transactional
    void getPublicationType() throws Exception {
        // Initialize the database
        publicationType.setId(UUID.randomUUID().toString());
        insertedPublicationType = publicationTypeRepository.saveAndFlush(publicationType);

        // Get the publicationType
        restPublicationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, publicationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicationType.getId()))
            .andExpect(jsonPath("$.publicationTypeName").value(DEFAULT_PUBLICATION_TYPE_NAME))
            .andExpect(jsonPath("$.publicationTypeCode").value(DEFAULT_PUBLICATION_TYPE_CODE))
            .andExpect(jsonPath("$.flagActive").value(DEFAULT_FLAG_ACTIVE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID));
    }

    @Test
    @Transactional
    void getNonExistingPublicationType() throws Exception {
        // Get the publicationType
        restPublicationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublicationType() throws Exception {
        // Initialize the database
        publicationType.setId(UUID.randomUUID().toString());
        insertedPublicationType = publicationTypeRepository.saveAndFlush(publicationType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationType
        PublicationType updatedPublicationType = publicationTypeRepository.findById(publicationType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPublicationType are not directly saved in db
        em.detach(updatedPublicationType);
        updatedPublicationType
            .publicationTypeName(UPDATED_PUBLICATION_TYPE_NAME)
            .publicationTypeCode(UPDATED_PUBLICATION_TYPE_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(updatedPublicationType);

        restPublicationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPublicationTypeToMatchAllProperties(updatedPublicationType);
    }

    @Test
    @Transactional
    void putNonExistingPublicationType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationType.setId(UUID.randomUUID().toString());

        // Create the PublicationType
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicationType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationType.setId(UUID.randomUUID().toString());

        // Create the PublicationType
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicationType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationType.setId(UUID.randomUUID().toString());

        // Create the PublicationType
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationTypeWithPatch() throws Exception {
        // Initialize the database
        publicationType.setId(UUID.randomUUID().toString());
        insertedPublicationType = publicationTypeRepository.saveAndFlush(publicationType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationType using partial update
        PublicationType partialUpdatedPublicationType = new PublicationType();
        partialUpdatedPublicationType.setId(publicationType.getId());

        partialUpdatedPublicationType
            .publicationTypeName(UPDATED_PUBLICATION_TYPE_NAME)
            .publicationTypeCode(UPDATED_PUBLICATION_TYPE_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restPublicationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationType))
            )
            .andExpect(status().isOk());

        // Validate the PublicationType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPublicationType, publicationType),
            getPersistedPublicationType(publicationType)
        );
    }

    @Test
    @Transactional
    void fullUpdatePublicationTypeWithPatch() throws Exception {
        // Initialize the database
        publicationType.setId(UUID.randomUUID().toString());
        insertedPublicationType = publicationTypeRepository.saveAndFlush(publicationType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationType using partial update
        PublicationType partialUpdatedPublicationType = new PublicationType();
        partialUpdatedPublicationType.setId(publicationType.getId());

        partialUpdatedPublicationType
            .publicationTypeName(UPDATED_PUBLICATION_TYPE_NAME)
            .publicationTypeCode(UPDATED_PUBLICATION_TYPE_CODE)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restPublicationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationType))
            )
            .andExpect(status().isOk());

        // Validate the PublicationType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationTypeUpdatableFieldsEquals(
            partialUpdatedPublicationType,
            getPersistedPublicationType(partialUpdatedPublicationType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPublicationType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationType.setId(UUID.randomUUID().toString());

        // Create the PublicationType
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicationTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicationType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationType.setId(UUID.randomUUID().toString());

        // Create the PublicationType
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicationType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationType.setId(UUID.randomUUID().toString());

        // Create the PublicationType
        PublicationTypeDTO publicationTypeDTO = publicationTypeMapper.toDto(publicationType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(publicationTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicationType() throws Exception {
        // Initialize the database
        publicationType.setId(UUID.randomUUID().toString());
        insertedPublicationType = publicationTypeRepository.saveAndFlush(publicationType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the publicationType
        restPublicationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicationType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return publicationTypeRepository.count();
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

    protected PublicationType getPersistedPublicationType(PublicationType publicationType) {
        return publicationTypeRepository.findById(publicationType.getId()).orElseThrow();
    }

    protected void assertPersistedPublicationTypeToMatchAllProperties(PublicationType expectedPublicationType) {
        assertPublicationTypeAllPropertiesEquals(expectedPublicationType, getPersistedPublicationType(expectedPublicationType));
    }

    protected void assertPersistedPublicationTypeToMatchUpdatableProperties(PublicationType expectedPublicationType) {
        assertPublicationTypeAllUpdatablePropertiesEquals(expectedPublicationType, getPersistedPublicationType(expectedPublicationType));
    }
}
