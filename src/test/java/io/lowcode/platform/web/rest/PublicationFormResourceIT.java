package io.lowcode.platform.web.rest;

import static io.lowcode.platform.domain.PublicationFormAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.PublicationForm;
import io.lowcode.platform.repository.PublicationFormRepository;
import io.lowcode.platform.service.dto.PublicationFormDTO;
import io.lowcode.platform.service.mapper.PublicationFormMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link PublicationFormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationFormResourceIT {

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

    private static final String ENTITY_API_URL = "/api/publication-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PublicationFormRepository publicationFormRepository;

    @Autowired
    private PublicationFormMapper publicationFormMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationFormMockMvc;

    private PublicationForm publicationForm;

    private PublicationForm insertedPublicationForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicationForm createEntity() {
        return new PublicationForm()
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
    public static PublicationForm createUpdatedEntity() {
        return new PublicationForm()
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
        publicationForm = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPublicationForm != null) {
            publicationFormRepository.delete(insertedPublicationForm);
            insertedPublicationForm = null;
        }
    }

    @Test
    @Transactional
    void createPublicationForm() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PublicationForm
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);
        var returnedPublicationFormDTO = om.readValue(
            restPublicationFormMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PublicationFormDTO.class
        );

        // Validate the PublicationForm in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPublicationForm = publicationFormMapper.toEntity(returnedPublicationFormDTO);
        assertPublicationFormUpdatableFieldsEquals(returnedPublicationForm, getPersistedPublicationForm(returnedPublicationForm));

        insertedPublicationForm = returnedPublicationForm;
    }

    @Test
    @Transactional
    void createPublicationFormWithExistingId() throws Exception {
        // Create the PublicationForm with an existing ID
        publicationForm.setId(1L);
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicationForms() throws Exception {
        // Initialize the database
        insertedPublicationForm = publicationFormRepository.saveAndFlush(publicationForm);

        // Get all the publicationFormList
        restPublicationFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicationForm.getId().intValue())))
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
    void getPublicationForm() throws Exception {
        // Initialize the database
        insertedPublicationForm = publicationFormRepository.saveAndFlush(publicationForm);

        // Get the publicationForm
        restPublicationFormMockMvc
            .perform(get(ENTITY_API_URL_ID, publicationForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicationForm.getId().intValue()))
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
    void getNonExistingPublicationForm() throws Exception {
        // Get the publicationForm
        restPublicationFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublicationForm() throws Exception {
        // Initialize the database
        insertedPublicationForm = publicationFormRepository.saveAndFlush(publicationForm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationForm
        PublicationForm updatedPublicationForm = publicationFormRepository.findById(publicationForm.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPublicationForm are not directly saved in db
        em.detach(updatedPublicationForm);
        updatedPublicationForm
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
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(updatedPublicationForm);

        restPublicationFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationFormDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormDTO))
            )
            .andExpect(status().isOk());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPublicationFormToMatchAllProperties(updatedPublicationForm);
    }

    @Test
    @Transactional
    void putNonExistingPublicationForm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationForm.setId(longCount.incrementAndGet());

        // Create the PublicationForm
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationFormDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicationForm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationForm.setId(longCount.incrementAndGet());

        // Create the PublicationForm
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicationForm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationForm.setId(longCount.incrementAndGet());

        // Create the PublicationForm
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationFormWithPatch() throws Exception {
        // Initialize the database
        insertedPublicationForm = publicationFormRepository.saveAndFlush(publicationForm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationForm using partial update
        PublicationForm partialUpdatedPublicationForm = new PublicationForm();
        partialUpdatedPublicationForm.setId(publicationForm.getId());

        partialUpdatedPublicationForm
            .fieldConfigs(UPDATED_FIELD_CONFIGS)
            .description(UPDATED_DESCRIPTION)
            .validationConfigs(UPDATED_VALIDATION_CONFIGS)
            .flagActive(UPDATED_FLAG_ACTIVE)
            .createUser(UPDATED_CREATE_USER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .uuid(UPDATED_UUID);

        restPublicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationForm))
            )
            .andExpect(status().isOk());

        // Validate the PublicationForm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationFormUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPublicationForm, publicationForm),
            getPersistedPublicationForm(publicationForm)
        );
    }

    @Test
    @Transactional
    void fullUpdatePublicationFormWithPatch() throws Exception {
        // Initialize the database
        insertedPublicationForm = publicationFormRepository.saveAndFlush(publicationForm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationForm using partial update
        PublicationForm partialUpdatedPublicationForm = new PublicationForm();
        partialUpdatedPublicationForm.setId(publicationForm.getId());

        partialUpdatedPublicationForm
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

        restPublicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationForm))
            )
            .andExpect(status().isOk());

        // Validate the PublicationForm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationFormUpdatableFieldsEquals(
            partialUpdatedPublicationForm,
            getPersistedPublicationForm(partialUpdatedPublicationForm)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPublicationForm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationForm.setId(longCount.incrementAndGet());

        // Create the PublicationForm
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicationFormDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicationForm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationForm.setId(longCount.incrementAndGet());

        // Create the PublicationForm
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicationForm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationForm.setId(longCount.incrementAndGet());

        // Create the PublicationForm
        PublicationFormDTO publicationFormDTO = publicationFormMapper.toDto(publicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(publicationFormDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationForm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicationForm() throws Exception {
        // Initialize the database
        insertedPublicationForm = publicationFormRepository.saveAndFlush(publicationForm);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the publicationForm
        restPublicationFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicationForm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return publicationFormRepository.count();
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

    protected PublicationForm getPersistedPublicationForm(PublicationForm publicationForm) {
        return publicationFormRepository.findById(publicationForm.getId()).orElseThrow();
    }

    protected void assertPersistedPublicationFormToMatchAllProperties(PublicationForm expectedPublicationForm) {
        assertPublicationFormAllPropertiesEquals(expectedPublicationForm, getPersistedPublicationForm(expectedPublicationForm));
    }

    protected void assertPersistedPublicationFormToMatchUpdatableProperties(PublicationForm expectedPublicationForm) {
        assertPublicationFormAllUpdatablePropertiesEquals(expectedPublicationForm, getPersistedPublicationForm(expectedPublicationForm));
    }
}
