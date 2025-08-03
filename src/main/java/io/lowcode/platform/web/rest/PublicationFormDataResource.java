package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.PublicationFormDataRepository;
import io.lowcode.platform.service.PublicationFormDataService;
import io.lowcode.platform.service.dto.PublicationFormDataDTO;
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
 * REST controller for managing {@link io.lowcode.platform.domain.PublicationFormData}.
 */
@RestController
@RequestMapping("/api/publication-form-data")
public class PublicationFormDataResource {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationFormDataResource.class);

    private static final String ENTITY_NAME = "publicationFormData";

    @Value("${spring.clientApp.name}")
    private String applicationName;

    private final PublicationFormDataService publicationFormDataService;

    private final PublicationFormDataRepository publicationFormDataRepository;

    public PublicationFormDataResource(
        PublicationFormDataService publicationFormDataService,
        PublicationFormDataRepository publicationFormDataRepository
    ) {
        this.publicationFormDataService = publicationFormDataService;
        this.publicationFormDataRepository = publicationFormDataRepository;
    }

    /**
     * {@code POST  /publication-form-data} : Create a new publicationFormData.
     *
     * @param publicationFormDataDTO the publicationFormDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicationFormDataDTO, or with status {@code 400 (Bad Request)} if the publicationFormData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PublicationFormDataDTO> createPublicationFormData(@RequestBody PublicationFormDataDTO publicationFormDataDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PublicationFormData : {}", publicationFormDataDTO);
        if (publicationFormDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new publicationFormData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        publicationFormDataDTO = publicationFormDataService.save(publicationFormDataDTO);
        return ResponseEntity.created(new URI("/api/publication-form-data/" + publicationFormDataDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, publicationFormDataDTO.getId().toString()))
            .body(publicationFormDataDTO);
    }

    /**
     * {@code PUT  /publication-form-data/:id} : Updates an existing publicationFormData.
     *
     * @param id the id of the publicationFormDataDTO to save.
     * @param publicationFormDataDTO the publicationFormDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationFormDataDTO,
     * or with status {@code 400 (Bad Request)} if the publicationFormDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicationFormDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PublicationFormDataDTO> updatePublicationFormData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicationFormDataDTO publicationFormDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PublicationFormData : {}, {}", id, publicationFormDataDTO);
        if (publicationFormDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationFormDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationFormDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        publicationFormDataDTO = publicationFormDataService.update(publicationFormDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationFormDataDTO.getId().toString()))
            .body(publicationFormDataDTO);
    }

    /**
     * {@code PATCH  /publication-form-data/:id} : Partial updates given fields of an existing publicationFormData, field will ignore if it is null
     *
     * @param id the id of the publicationFormDataDTO to save.
     * @param publicationFormDataDTO the publicationFormDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationFormDataDTO,
     * or with status {@code 400 (Bad Request)} if the publicationFormDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicationFormDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicationFormDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PublicationFormDataDTO> partialUpdatePublicationFormData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicationFormDataDTO publicationFormDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PublicationFormData partially : {}, {}", id, publicationFormDataDTO);
        if (publicationFormDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationFormDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationFormDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicationFormDataDTO> result = publicationFormDataService.partialUpdate(publicationFormDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationFormDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /publication-form-data} : get all the publicationFormData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicationFormData in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PublicationFormDataDTO>> getAllPublicationFormData(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PublicationFormData");
        Page<PublicationFormDataDTO> page = publicationFormDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publication-form-data/:id} : get the "id" publicationFormData.
     *
     * @param id the id of the publicationFormDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicationFormDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicationFormDataDTO> getPublicationFormData(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PublicationFormData : {}", id);
        Optional<PublicationFormDataDTO> publicationFormDataDTO = publicationFormDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicationFormDataDTO);
    }

    /**
     * {@code DELETE  /publication-form-data/:id} : delete the "id" publicationFormData.
     *
     * @param id the id of the publicationFormDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicationFormData(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PublicationFormData : {}", id);
        publicationFormDataService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
