package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.PublicationStatusRepository;
import io.lowcode.platform.service.PublicationStatusService;
import io.lowcode.platform.service.dto.PublicationStatusDTO;
import io.lowcode.platform.web.rest.errors.BadRequestAlertException;
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


/**
 * REST controller for managing {@link io.lowcode.platform.domain.PublicationStatus}.
 */
@RestController
@RequestMapping("/api/publication-statuses")
public class PublicationStatusResource {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationStatusResource.class);

    private static final String ENTITY_NAME = "publicationStatus";

    @Value("${spring.clientApp.name}")
    private String applicationName;

    private final PublicationStatusService publicationStatusService;

    private final PublicationStatusRepository publicationStatusRepository;

    public PublicationStatusResource(
        PublicationStatusService publicationStatusService,
        PublicationStatusRepository publicationStatusRepository
    ) {
        this.publicationStatusService = publicationStatusService;
        this.publicationStatusRepository = publicationStatusRepository;
    }

    /**
     * {@code POST  /publication-statuses} : Create a new publicationStatus.
     *
     * @param publicationStatusDTO the publicationStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicationStatusDTO, or with status {@code 400 (Bad Request)} if the publicationStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PublicationStatusDTO> createPublicationStatus(@RequestBody PublicationStatusDTO publicationStatusDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PublicationStatus : {}", publicationStatusDTO);
        if (publicationStatusRepository.existsById(publicationStatusDTO.getId())) {
            throw new BadRequestAlertException("publicationStatus already exists", ENTITY_NAME, "idexists");
        }
        publicationStatusDTO = publicationStatusService.save(publicationStatusDTO);
        return ResponseEntity.created(new URI("/api/publication-statuses/" + publicationStatusDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, publicationStatusDTO.getId()))
            .body(publicationStatusDTO);
    }

    /**
     * {@code PUT  /publication-statuses/:id} : Updates an existing publicationStatus.
     *
     * @param id the id of the publicationStatusDTO to save.
     * @param publicationStatusDTO the publicationStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationStatusDTO,
     * or with status {@code 400 (Bad Request)} if the publicationStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicationStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PublicationStatusDTO> updatePublicationStatus(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PublicationStatusDTO publicationStatusDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PublicationStatus : {}, {}", id, publicationStatusDTO);
        if (publicationStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        publicationStatusDTO = publicationStatusService.update(publicationStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationStatusDTO.getId()))
            .body(publicationStatusDTO);
    }

    /**
     * {@code PATCH  /publication-statuses/:id} : Partial updates given fields of an existing publicationStatus, field will ignore if it is null
     *
     * @param id the id of the publicationStatusDTO to save.
     * @param publicationStatusDTO the publicationStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationStatusDTO,
     * or with status {@code 400 (Bad Request)} if the publicationStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicationStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicationStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PublicationStatusDTO> partialUpdatePublicationStatus(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PublicationStatusDTO publicationStatusDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PublicationStatus partially : {}, {}", id, publicationStatusDTO);
        if (publicationStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicationStatusDTO> result = publicationStatusService.partialUpdate(publicationStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationStatusDTO.getId())
        );
    }

    /**
     * {@code GET  /publication-statuses} : get all the publicationStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicationStatuses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PublicationStatusDTO>> getAllPublicationStatuses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PublicationStatuses");
        Page<PublicationStatusDTO> page = publicationStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publication-statuses/:id} : get the "id" publicationStatus.
     *
     * @param id the id of the publicationStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicationStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicationStatusDTO> getPublicationStatus(@PathVariable("id") String id) {
        LOG.debug("REST request to get PublicationStatus : {}", id);
        Optional<PublicationStatusDTO> publicationStatusDTO = publicationStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicationStatusDTO);
    }

    /**
     * {@code DELETE  /publication-statuses/:id} : delete the "id" publicationStatus.
     *
     * @param id the id of the publicationStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicationStatus(@PathVariable("id") String id) {
        LOG.debug("REST request to delete PublicationStatus : {}", id);
        publicationStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
