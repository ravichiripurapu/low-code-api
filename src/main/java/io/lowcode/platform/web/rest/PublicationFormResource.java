package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.PublicationFormRepository;
import io.lowcode.platform.service.PublicationFormService;
import io.lowcode.platform.service.dto.PublicationFormDTO;
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
 * REST controller for managing {@link io.lowcode.platform.domain.PublicationForm}.
 */
@RestController
@RequestMapping("/api/publication-forms")
public class PublicationFormResource {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationFormResource.class);

    private static final String ENTITY_NAME = "publicationForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicationFormService publicationFormService;

    private final PublicationFormRepository publicationFormRepository;

    public PublicationFormResource(PublicationFormService publicationFormService, PublicationFormRepository publicationFormRepository) {
        this.publicationFormService = publicationFormService;
        this.publicationFormRepository = publicationFormRepository;
    }

    /**
     * {@code POST  /publication-forms} : Create a new publicationForm.
     *
     * @param publicationFormDTO the publicationFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicationFormDTO, or with status {@code 400 (Bad Request)} if the publicationForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PublicationFormDTO> createPublicationForm(@RequestBody PublicationFormDTO publicationFormDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PublicationForm : {}", publicationFormDTO);
        if (publicationFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new publicationForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        publicationFormDTO = publicationFormService.save(publicationFormDTO);
        return ResponseEntity.created(new URI("/api/publication-forms/" + publicationFormDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, publicationFormDTO.getId().toString()))
            .body(publicationFormDTO);
    }

    /**
     * {@code PUT  /publication-forms/:id} : Updates an existing publicationForm.
     *
     * @param id the id of the publicationFormDTO to save.
     * @param publicationFormDTO the publicationFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationFormDTO,
     * or with status {@code 400 (Bad Request)} if the publicationFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicationFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PublicationFormDTO> updatePublicationForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicationFormDTO publicationFormDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PublicationForm : {}, {}", id, publicationFormDTO);
        if (publicationFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        publicationFormDTO = publicationFormService.update(publicationFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationFormDTO.getId().toString()))
            .body(publicationFormDTO);
    }

    /**
     * {@code PATCH  /publication-forms/:id} : Partial updates given fields of an existing publicationForm, field will ignore if it is null
     *
     * @param id the id of the publicationFormDTO to save.
     * @param publicationFormDTO the publicationFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationFormDTO,
     * or with status {@code 400 (Bad Request)} if the publicationFormDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicationFormDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicationFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PublicationFormDTO> partialUpdatePublicationForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PublicationFormDTO publicationFormDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PublicationForm partially : {}, {}", id, publicationFormDTO);
        if (publicationFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicationFormDTO> result = publicationFormService.partialUpdate(publicationFormDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationFormDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /publication-forms} : get all the publicationForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicationForms in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PublicationFormDTO>> getAllPublicationForms(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PublicationForms");
        Page<PublicationFormDTO> page = publicationFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publication-forms/:id} : get the "id" publicationForm.
     *
     * @param id the id of the publicationFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicationFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicationFormDTO> getPublicationForm(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PublicationForm : {}", id);
        Optional<PublicationFormDTO> publicationFormDTO = publicationFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicationFormDTO);
    }

    /**
     * {@code DELETE  /publication-forms/:id} : delete the "id" publicationForm.
     *
     * @param id the id of the publicationFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicationForm(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PublicationForm : {}", id);
        publicationFormService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
