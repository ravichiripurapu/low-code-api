package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.OrgRepository;
import io.lowcode.platform.service.OrgService;
import io.lowcode.platform.service.dto.OrgDTO;
import io.lowcode.platform.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.lowcode.platform.domain.Org}.
 */
@RestController
@RequestMapping("/api/orgs")
public class OrgResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrgResource.class);

    private static final String ENTITY_NAME = "org";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgService orgService;

    private final OrgRepository orgRepository;

    public OrgResource(OrgService orgService, OrgRepository orgRepository) {
        this.orgService = orgService;
        this.orgRepository = orgRepository;
    }

    /**
     * {@code POST  /orgs} : Create a new org.
     *
     * @param orgDTO the orgDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgDTO, or with status {@code 400 (Bad Request)} if the org has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrgDTO> createOrg(@Valid @RequestBody OrgDTO orgDTO) throws URISyntaxException {
        LOG.debug("REST request to save Org : {}", orgDTO);
        if (orgRepository.existsById(orgDTO.getId())) {
            throw new BadRequestAlertException("org already exists", ENTITY_NAME, "idexists");
        }
        orgDTO = orgService.save(orgDTO);
        return ResponseEntity.created(new URI("/api/orgs/" + orgDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, orgDTO.getId()))
            .body(orgDTO);
    }

    /**
     * {@code PUT  /orgs/:id} : Updates an existing org.
     *
     * @param id the id of the orgDTO to save.
     * @param orgDTO the orgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgDTO,
     * or with status {@code 400 (Bad Request)} if the orgDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrgDTO> updateOrg(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OrgDTO orgDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Org : {}, {}", id, orgDTO);
        if (orgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        orgDTO = orgService.update(orgDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgDTO.getId()))
            .body(orgDTO);
    }

    /**
     * {@code PATCH  /orgs/:id} : Partial updates given fields of an existing org, field will ignore if it is null
     *
     * @param id the id of the orgDTO to save.
     * @param orgDTO the orgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgDTO,
     * or with status {@code 400 (Bad Request)} if the orgDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orgDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrgDTO> partialUpdateOrg(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OrgDTO orgDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Org partially : {}, {}", id, orgDTO);
        if (orgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrgDTO> result = orgService.partialUpdate(orgDTO);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgDTO.getId()));
    }

    /**
     * {@code GET  /orgs} : get all the orgs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrgDTO>> getAllOrgs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Orgs");
        Page<OrgDTO> page = orgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /orgs/:id} : get the "id" org.
     *
     * @param id the id of the orgDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrgDTO> getOrg(@PathVariable("id") String id) {
        LOG.debug("REST request to get Org : {}", id);
        Optional<OrgDTO> orgDTO = orgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgDTO);
    }

    /**
     * {@code DELETE  /orgs/:id} : delete the "id" org.
     *
     * @param id the id of the orgDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrg(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Org : {}", id);
        orgService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
