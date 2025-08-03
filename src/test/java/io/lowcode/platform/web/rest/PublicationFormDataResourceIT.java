package io.lowcode.platform.web.rest;

import static io.lowcode.platform.domain.PublicationFormDataAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.PublicationFormData;
import io.lowcode.platform.repository.PublicationFormDataRepository;
import io.lowcode.platform.service.dto.PublicationFormDataDTO;
import io.lowcode.platform.service.mapper.PublicationFormDataMapper;
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
 * Integration tests for the {@link PublicationFormDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublicationFormDataResourceIT {

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

    private static final String ENTITY_API_URL = "/api/publication-form-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PublicationFormDataRepository publicationFormDataRepository;

    @Autowired
    private PublicationFormDataMapper publicationFormDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicationFormDataMockMvc;

    private PublicationFormData publicationFormData;

    private PublicationFormData insertedPublicationFormData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicationFormData createEntity() {
        return new PublicationFormData()
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
    public static PublicationFormData createUpdatedEntity() {
        return new PublicationFormData()
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
        publicationFormData = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPublicationFormData != null) {
            publicationFormDataRepository.delete(insertedPublicationFormData);
            insertedPublicationFormData = null;
        }
    }

    @Test
    @Transactional
    void createPublicationFormData() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PublicationFormData
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);
        var returnedPublicationFormDataDTO = om.readValue(
            restPublicationFormDataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormDataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PublicationFormDataDTO.class
        );

        // Validate the PublicationFormData in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPublicationFormData = publicationFormDataMapper.toEntity(returnedPublicationFormDataDTO);
        assertPublicationFormDataUpdatableFieldsEquals(
            returnedPublicationFormData,
            getPersistedPublicationFormData(returnedPublicationFormData)
        );

        insertedPublicationFormData = returnedPublicationFormData;
    }

    @Test
    @Transactional
    void createPublicationFormDataWithExistingId() throws Exception {
        // Create the PublicationFormData with an existing ID
        publicationFormData.setId(1L);
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicationFormDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublicationFormData() throws Exception {
        // Initialize the database
        insertedPublicationFormData = publicationFormDataRepository.saveAndFlush(publicationFormData);

        // Get all the publicationFormDataList
        restPublicationFormDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicationFormData.getId().intValue())))
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
    void getPublicationFormData() throws Exception {
        // Initialize the database
        insertedPublicationFormData = publicationFormDataRepository.saveAndFlush(publicationFormData);

        // Get the publicationFormData
        restPublicationFormDataMockMvc
            .perform(get(ENTITY_API_URL_ID, publicationFormData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicationFormData.getId().intValue()))
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
    void getNonExistingPublicationFormData() throws Exception {
        // Get the publicationFormData
        restPublicationFormDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPublicationFormData() throws Exception {
        // Initialize the database
        insertedPublicationFormData = publicationFormDataRepository.saveAndFlush(publicationFormData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationFormData
        PublicationFormData updatedPublicationFormData = publicationFormDataRepository.findById(publicationFormData.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPublicationFormData are not directly saved in db
        em.detach(updatedPublicationFormData);
        updatedPublicationFormData
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
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(updatedPublicationFormData);

        restPublicationFormDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationFormDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPublicationFormDataToMatchAllProperties(updatedPublicationFormData);
    }

    @Test
    @Transactional
    void putNonExistingPublicationFormData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormData.setId(longCount.incrementAndGet());

        // Create the PublicationFormData
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationFormDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publicationFormDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublicationFormData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormData.setId(longCount.incrementAndGet());

        // Create the PublicationFormData
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(publicationFormDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublicationFormData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormData.setId(longCount.incrementAndGet());

        // Create the PublicationFormData
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(publicationFormDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublicationFormDataWithPatch() throws Exception {
        // Initialize the database
        insertedPublicationFormData = publicationFormDataRepository.saveAndFlush(publicationFormData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationFormData using partial update
        PublicationFormData partialUpdatedPublicationFormData = new PublicationFormData();
        partialUpdatedPublicationFormData.setId(publicationFormData.getId());

        partialUpdatedPublicationFormData
            .idFormVersion(UPDATED_ID_FORM_VERSION)
            .fieldLabel(UPDATED_FIELD_LABEL)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldId(UPDATED_FIELD_ID)
            .fieldClass(UPDATED_FIELD_CLASS)
            .flagRequired(UPDATED_FLAG_REQUIRED)
            .updateUser(UPDATED_UPDATE_USER)
            .updatedAt(UPDATED_UPDATED_AT)
            .value(UPDATED_VALUE)
            .alternativeValue(UPDATED_ALTERNATIVE_VALUE)
            .uuid(UPDATED_UUID);

        restPublicationFormDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationFormData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationFormData))
            )
            .andExpect(status().isOk());

        // Validate the PublicationFormData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationFormDataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPublicationFormData, publicationFormData),
            getPersistedPublicationFormData(publicationFormData)
        );
    }

    @Test
    @Transactional
    void fullUpdatePublicationFormDataWithPatch() throws Exception {
        // Initialize the database
        insertedPublicationFormData = publicationFormDataRepository.saveAndFlush(publicationFormData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the publicationFormData using partial update
        PublicationFormData partialUpdatedPublicationFormData = new PublicationFormData();
        partialUpdatedPublicationFormData.setId(publicationFormData.getId());

        partialUpdatedPublicationFormData
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

        restPublicationFormDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublicationFormData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPublicationFormData))
            )
            .andExpect(status().isOk());

        // Validate the PublicationFormData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPublicationFormDataUpdatableFieldsEquals(
            partialUpdatedPublicationFormData,
            getPersistedPublicationFormData(partialUpdatedPublicationFormData)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPublicationFormData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormData.setId(longCount.incrementAndGet());

        // Create the PublicationFormData
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicationFormDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publicationFormDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationFormDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublicationFormData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormData.setId(longCount.incrementAndGet());

        // Create the PublicationFormData
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(publicationFormDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublicationFormData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        publicationFormData.setId(longCount.incrementAndGet());

        // Create the PublicationFormData
        PublicationFormDataDTO publicationFormDataDTO = publicationFormDataMapper.toDto(publicationFormData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublicationFormDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(publicationFormDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PublicationFormData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublicationFormData() throws Exception {
        // Initialize the database
        insertedPublicationFormData = publicationFormDataRepository.saveAndFlush(publicationFormData);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the publicationFormData
        restPublicationFormDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, publicationFormData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return publicationFormDataRepository.count();
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

    protected PublicationFormData getPersistedPublicationFormData(PublicationFormData publicationFormData) {
        return publicationFormDataRepository.findById(publicationFormData.getId()).orElseThrow();
    }

    protected void assertPersistedPublicationFormDataToMatchAllProperties(PublicationFormData expectedPublicationFormData) {
        assertPublicationFormDataAllPropertiesEquals(
            expectedPublicationFormData,
            getPersistedPublicationFormData(expectedPublicationFormData)
        );
    }

    protected void assertPersistedPublicationFormDataToMatchUpdatableProperties(PublicationFormData expectedPublicationFormData) {
        assertPublicationFormDataAllUpdatablePropertiesEquals(
            expectedPublicationFormData,
            getPersistedPublicationFormData(expectedPublicationFormData)
        );
    }
}
