package io.lowcode.platform.web.rest;

import static io.lowcode.platform.domain.OrgAsserts.*;
import static io.lowcode.platform.web.rest.TestUtil.createUpdateProxyForBean;
import static io.lowcode.platform.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lowcode.platform.IntegrationTest;
import io.lowcode.platform.domain.Org;
import io.lowcode.platform.repository.OrgRepository;
import io.lowcode.platform.service.dto.OrgDTO;
import io.lowcode.platform.service.mapper.OrgMapper;
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
 * Integration tests for the {@link OrgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrgResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIPCODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/orgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgMockMvc;

    private Org org;

    private Org insertedOrg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Org createEntity() {
        return new Org()
            .id(UUID.randomUUID().toString())
            .name(DEFAULT_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zipcode(DEFAULT_ZIPCODE)
            .createdTimestamp(DEFAULT_CREATED_TIMESTAMP)
            .updatedTimestamp(DEFAULT_UPDATED_TIMESTAMP)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Org createUpdatedEntity() {
        return new Org()
            .id(UUID.randomUUID().toString())
            .name(UPDATED_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .createdTimestamp(UPDATED_CREATED_TIMESTAMP)
            .updatedTimestamp(UPDATED_UPDATED_TIMESTAMP)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
    }

    @BeforeEach
    void initTest() {
        org = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOrg != null) {
            orgRepository.delete(insertedOrg);
            insertedOrg = null;
        }
    }

    @Test
    @Transactional
    void createOrg() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);
        var returnedOrgDTO = om.readValue(
            restOrgMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orgDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrgDTO.class
        );

        // Validate the Org in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrg = orgMapper.toEntity(returnedOrgDTO);
        assertOrgUpdatableFieldsEquals(returnedOrg, getPersistedOrg(returnedOrg));

        insertedOrg = returnedOrg;
    }

    @Test
    @Transactional
    void createOrgWithExistingId() throws Exception {
        // Create the Org with an existing ID
        insertedOrg = orgRepository.saveAndFlush(org);
        OrgDTO orgDTO = orgMapper.toDto(org);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrgs() throws Exception {
        // Initialize the database
        org.setId(UUID.randomUUID().toString());
        insertedOrg = orgRepository.saveAndFlush(org);

        // Get all the orgList
        restOrgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(org.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].createdTimestamp").value(hasItem(sameInstant(DEFAULT_CREATED_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].updatedTimestamp").value(hasItem(sameInstant(DEFAULT_UPDATED_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getOrg() throws Exception {
        // Initialize the database
        org.setId(UUID.randomUUID().toString());
        insertedOrg = orgRepository.saveAndFlush(org);

        // Get the org
        restOrgMockMvc
            .perform(get(ENTITY_API_URL_ID, org.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(org.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.createdTimestamp").value(sameInstant(DEFAULT_CREATED_TIMESTAMP)))
            .andExpect(jsonPath("$.updatedTimestamp").value(sameInstant(DEFAULT_UPDATED_TIMESTAMP)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingOrg() throws Exception {
        // Get the org
        restOrgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrg() throws Exception {
        // Initialize the database
        org.setId(UUID.randomUUID().toString());
        insertedOrg = orgRepository.saveAndFlush(org);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the org
        Org updatedOrg = orgRepository.findById(org.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrg are not directly saved in db
        em.detach(updatedOrg);
        updatedOrg
            .name(UPDATED_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .createdTimestamp(UPDATED_CREATED_TIMESTAMP)
            .updatedTimestamp(UPDATED_UPDATED_TIMESTAMP)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        OrgDTO orgDTO = orgMapper.toDto(updatedOrg);

        restOrgMockMvc
            .perform(put(ENTITY_API_URL_ID, orgDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orgDTO)))
            .andExpect(status().isOk());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrgToMatchAllProperties(updatedOrg);
    }

    @Test
    @Transactional
    void putNonExistingOrg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        org.setId(UUID.randomUUID().toString());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(put(ENTITY_API_URL_ID, orgDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        org.setId(UUID.randomUUID().toString());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        org.setId(UUID.randomUUID().toString());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orgDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrgWithPatch() throws Exception {
        // Initialize the database
        org.setId(UUID.randomUUID().toString());
        insertedOrg = orgRepository.saveAndFlush(org);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the org using partial update
        Org partialUpdatedOrg = new Org();
        partialUpdatedOrg.setId(org.getId());

        partialUpdatedOrg
            .name(UPDATED_NAME)
            .zipcode(UPDATED_ZIPCODE)
            .createdTimestamp(UPDATED_CREATED_TIMESTAMP)
            .updatedTimestamp(UPDATED_UPDATED_TIMESTAMP)
            .createdBy(UPDATED_CREATED_BY);

        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrg.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrg))
            )
            .andExpect(status().isOk());

        // Validate the Org in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrgUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOrg, org), getPersistedOrg(org));
    }

    @Test
    @Transactional
    void fullUpdateOrgWithPatch() throws Exception {
        // Initialize the database
        org.setId(UUID.randomUUID().toString());
        insertedOrg = orgRepository.saveAndFlush(org);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the org using partial update
        Org partialUpdatedOrg = new Org();
        partialUpdatedOrg.setId(org.getId());

        partialUpdatedOrg
            .name(UPDATED_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .createdTimestamp(UPDATED_CREATED_TIMESTAMP)
            .updatedTimestamp(UPDATED_UPDATED_TIMESTAMP)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrg.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrg))
            )
            .andExpect(status().isOk());

        // Validate the Org in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrgUpdatableFieldsEquals(partialUpdatedOrg, getPersistedOrg(partialUpdatedOrg));
    }

    @Test
    @Transactional
    void patchNonExistingOrg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        org.setId(UUID.randomUUID().toString());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        org.setId(UUID.randomUUID().toString());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrg() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        org.setId(UUID.randomUUID().toString());

        // Create the Org
        OrgDTO orgDTO = orgMapper.toDto(org);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(orgDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Org in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrg() throws Exception {
        // Initialize the database
        org.setId(UUID.randomUUID().toString());
        insertedOrg = orgRepository.saveAndFlush(org);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the org
        restOrgMockMvc.perform(delete(ENTITY_API_URL_ID, org.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return orgRepository.count();
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

    protected Org getPersistedOrg(Org org) {
        return orgRepository.findById(org.getId()).orElseThrow();
    }

    protected void assertPersistedOrgToMatchAllProperties(Org expectedOrg) {
        assertOrgAllPropertiesEquals(expectedOrg, getPersistedOrg(expectedOrg));
    }

    protected void assertPersistedOrgToMatchUpdatableProperties(Org expectedOrg) {
        assertOrgAllUpdatablePropertiesEquals(expectedOrg, getPersistedOrg(expectedOrg));
    }
}
