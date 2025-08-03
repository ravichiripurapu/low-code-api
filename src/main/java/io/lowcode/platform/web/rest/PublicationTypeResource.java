package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.PublicationTypeRepository;
import io.lowcode.platform.service.PublicationTypeService;
import io.lowcode.platform.service.dto.PublicationTypeDTO;
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
 * REST controller for managing {@link io.lowcode.platform.domain.PublicationType}.
 */
@RestController
@RequestMapping("/api/publication-types")
public class PublicationTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationTypeResource.class);

    private static final String ENTITY_NAME = "publicationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicationTypeService publicationTypeService;

    private final PublicationTypeRepository publicationTypeRepository;

    public PublicationTypeResource(PublicationTypeService publicationTypeService, PublicationTypeRepository publicationTypeRepository) {
        this.publicationTypeService = publicationTypeService;
        this.publicationTypeRepository = publicationTypeRepository;
    }

    /**
     * {@code POST  /publication-types} : Create a new publicationType.
     *
     * @param publicationTypeDTO the publicationTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicationTypeDTO, or with status {@code 400 (Bad Request)} if the publicationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PublicationTypeDTO> createPublicationType(@RequestBody PublicationTypeDTO publicationTypeDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PublicationType : {}", publicationTypeDTO);
        if (publicationTypeRepository.existsById(publicationTypeDTO.getId())) {
            throw new BadRequestAlertException("publicationType already exists", ENTITY_NAME, "idexists");
        }
        publicationTypeDTO = publicationTypeService.save(publicationTypeDTO);
        return ResponseEntity.created(new URI("/api/publication-types/" + publicationTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, publicationTypeDTO.getId()))
            .body(publicationTypeDTO);
    }

    /**
     * {@code PUT  /publication-types/:id} : Updates an existing publicationType.
     *
     * @param id the id of the publicationTypeDTO to save.
     * @param publicationTypeDTO the publicationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the publicationTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PublicationTypeDTO> updatePublicationType(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PublicationTypeDTO publicationTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PublicationType : {}, {}", id, publicationTypeDTO);
        if (publicationTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        publicationTypeDTO = publicationTypeService.update(publicationTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationTypeDTO.getId()))
            .body(publicationTypeDTO);
    }

    /**
     * {@code PATCH  /publication-types/:id} : Partial updates given fields of an existing publicationType, field will ignore if it is null
     *
     * @param id the id of the publicationTypeDTO to save.
     * @param publicationTypeDTO the publicationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the publicationTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicationTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the publicationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PublicationTypeDTO> partialUpdatePublicationType(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PublicationTypeDTO publicationTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PublicationType partially : {}, {}", id, publicationTypeDTO);
        if (publicationTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, publicationTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!publicationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PublicationTypeDTO> result = publicationTypeService.partialUpdate(publicationTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicationTypeDTO.getId())
        );
    }

    /**
     * {@code GET  /publication-types} : get all the publicationTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicationTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PublicationTypeDTO>> getAllPublicationTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PublicationTypes");
        Page<PublicationTypeDTO> page = publicationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publication-types/:id} : get the "id" publicationType.
     *
     * @param id the id of the publicationTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicationTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicationTypeDTO> getPublicationType(@PathVariable("id") String id) {
        LOG.debug("REST request to get PublicationType : {}", id);
        Optional<PublicationTypeDTO> publicationTypeDTO = publicationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicationTypeDTO);
    }

    /**
     * {@code DELETE  /publication-types/:id} : delete the "id" publicationType.
     *
     * @param id the id of the publicationTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicationType(@PathVariable("id") String id) {
        LOG.debug("REST request to delete PublicationType : {}", id);
        publicationTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
