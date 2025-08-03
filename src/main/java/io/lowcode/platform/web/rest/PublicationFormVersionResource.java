package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.PublicationFormVersionRepository;
import io.lowcode.platform.service.PublicationFormVersionService;
import io.lowcode.platform.service.dto.PublicationFormVersionDTO;
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
 * REST controller for managing {@link io.lowcode.platform.domain.PublicationFormVersion}.
 */
@RestController
@RequestMapping("/api/publication-form-versions")
public class PublicationFormVersionResource {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationFormVersionResource.class);

    private static final String ENTITY_NAME = "publicationFormVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicationFormVersionService publicationFormVersionService;

    private final PublicationFormVersionRepository publicationFormVersionRepository;

    public PublicationFormVersionResource(
        PublicationFormVersionService publicationFormVersionService,
        PublicationFormVersionRepository publicationFormVersionRepository
    ) {
        this.publicationFormVersionService = publicationFormVersionService;
        this.publicationFormVersionRepository = publicationFormVersionRepository;
    }

    /**
     * {@code POST  /publication-form-versions} : Create a new publicationFormVersion.
     *
     * @param publicationFormVersionDTO the publicationFormVersionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicationFormVersionDTO, or with status {@code 400 (Bad Request)} if the publicationFormVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PublicationFormVersionDTO> createPublicationFormVersion(
        @RequestBody PublicationFormVersionDTO publicationFormVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save PublicationFormVersion : {}", publicationFormVersionDTO);
        if (publicationFormVersionRepository.existsById(publicationFormVersionDTO.getId())) {
            throw new BadRequestAlertException("publicationFormVersion already exists", ENTITY_NAME, "idexists");
        }
        publicationFormVersionDTO = publicationFormVersionService.save(publicationFormVersionDTO);
        return ResponseEntity.created(new URI("/api/publication-form-versions/" + publicationFormVersionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, publicationFormVersionDTO.getId()))
            .body(publicationFormVersionDTO);
    }

    /**
     * {@code PUT  /publication-form-versions/:id} : Updates an existing publicationFormVersion.
     *
     * @param id the id of the publicationFormVersionDTO to save.
     * @param publicationFormVersionDTO the publicationFormVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationFormVersionDTO,
     * or with status {@code 400 (Bad Request)} if the publicationFormVersionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicationFormVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PublicationFormVersionDTO> updatePublicationFormVersion(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PublicationFormVersionDTO publicationFormVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PublicationFormVersion : {}, {}", id, publicationFormVersionDTO);
        if (publicationFormVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationFormVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationFormVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        publicationFormVersionDTO = publicationFormVersionService.update(publicationFormVersionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationFormVersionDTO.getId()))
            .body(publicationFormVersionDTO);
    }

    /**
     * {@code PATCH  /publication-form-versions/:id} : Partial updates given fields of an existing publicationFormVersion, field will ignore if it is null
     *
     * @param id the id of the publicationFormVersionDTO to save.
     * @param publicationFormVersionDTO the publicationFormVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationFormVersionDTO,
     * or with status {@code 400 (Bad Request)} if the publicationFormVersionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicationFormVersionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicationFormVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PublicationFormVersionDTO> partialUpdatePublicationFormVersion(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PublicationFormVersionDTO publicationFormVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PublicationFormVersion partially : {}, {}", id, publicationFormVersionDTO);
        if (publicationFormVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationFormVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationFormVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicationFormVersionDTO> result = publicationFormVersionService.partialUpdate(publicationFormVersionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationFormVersionDTO.getId())
        );
    }

    /**
     * {@code GET  /publication-form-versions} : get all the publicationFormVersions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicationFormVersions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PublicationFormVersionDTO>> getAllPublicationFormVersions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PublicationFormVersions");
        Page<PublicationFormVersionDTO> page = publicationFormVersionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publication-form-versions/:id} : get the "id" publicationFormVersion.
     *
     * @param id the id of the publicationFormVersionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicationFormVersionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicationFormVersionDTO> getPublicationFormVersion(@PathVariable("id") String id) {
        LOG.debug("REST request to get PublicationFormVersion : {}", id);
        Optional<PublicationFormVersionDTO> publicationFormVersionDTO = publicationFormVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicationFormVersionDTO);
    }

    /**
     * {@code DELETE  /publication-form-versions/:id} : delete the "id" publicationFormVersion.
     *
     * @param id the id of the publicationFormVersionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicationFormVersion(@PathVariable("id") String id) {
        LOG.debug("REST request to delete PublicationFormVersion : {}", id);
        publicationFormVersionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
