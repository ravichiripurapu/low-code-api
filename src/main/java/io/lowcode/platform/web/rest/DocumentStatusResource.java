package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.DocumentStatusRepository;
import io.lowcode.platform.service.DocumentStatusService;
import io.lowcode.platform.service.dto.DocumentStatusDTO;
import io.lowcode.platform.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link io.lowcode.platform.domain.DocumentStatus}.
 */
@RestController
@RequestMapping("/api/document-statuses")
public class DocumentStatusResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentStatusResource.class);

    private static final String ENTITY_NAME = "documentStatus";

    @Value("${spring.clientApp.name}")
    private String applicationName;

    private final DocumentStatusService documentStatusService;

    private final DocumentStatusRepository documentStatusRepository;

    public DocumentStatusResource(DocumentStatusService documentStatusService, DocumentStatusRepository documentStatusRepository) {
        this.documentStatusService = documentStatusService;
        this.documentStatusRepository = documentStatusRepository;
    }

    /**
     * {@code POST  /document-statuses} : Create a new documentStatus.
     *
     * @param documentStatusDTO the documentStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentStatusDTO, or with status {@code 400 (Bad Request)} if the documentStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocumentStatusDTO> createDocumentStatus(@RequestBody DocumentStatusDTO documentStatusDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DocumentStatus : {}", documentStatusDTO);
        if (documentStatusRepository.existsById(documentStatusDTO.getId())) {
            throw new BadRequestAlertException("documentStatus already exists", ENTITY_NAME, "idexists");
        }
        documentStatusDTO = documentStatusService.save(documentStatusDTO);
        return ResponseEntity.created(new URI("/api/document-statuses/" + documentStatusDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentStatusDTO.getId()))
            .body(documentStatusDTO);
    }

    /**
     * {@code PUT  /document-statuses/:id} : Updates an existing documentStatus.
     *
     * @param id the id of the documentStatusDTO to save.
     * @param documentStatusDTO the documentStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentStatusDTO,
     * or with status {@code 400 (Bad Request)} if the documentStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentStatusDTO> updateDocumentStatus(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DocumentStatusDTO documentStatusDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DocumentStatus : {}, {}", id, documentStatusDTO);
        if (documentStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentStatusDTO = documentStatusService.update(documentStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentStatusDTO.getId()))
            .body(documentStatusDTO);
    }

    /**
     * {@code PATCH  /document-statuses/:id} : Partial updates given fields of an existing documentStatus, field will ignore if it is null
     *
     * @param id the id of the documentStatusDTO to save.
     * @param documentStatusDTO the documentStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentStatusDTO,
     * or with status {@code 400 (Bad Request)} if the documentStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentStatusDTO> partialUpdateDocumentStatus(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DocumentStatusDTO documentStatusDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DocumentStatus partially : {}, {}", id, documentStatusDTO);
        if (documentStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentStatusDTO> result = documentStatusService.partialUpdate(documentStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentStatusDTO.getId())
        );
    }

    /**
     * {@code GET  /document-statuses} : get all the documentStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentStatuses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DocumentStatusDTO>> getAllDocumentStatuses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DocumentStatuses");
        Page<DocumentStatusDTO> page = documentStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-statuses/:id} : get the "id" documentStatus.
     *
     * @param id the id of the documentStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentStatusDTO> getDocumentStatus(@PathVariable("id") String id) {
        LOG.debug("REST request to get DocumentStatus : {}", id);
        Optional<DocumentStatusDTO> documentStatusDTO = documentStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentStatusDTO);
    }

    /**
     * {@code DELETE  /document-statuses/:id} : delete the "id" documentStatus.
     *
     * @param id the id of the documentStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentStatus(@PathVariable("id") String id) {
        LOG.debug("REST request to delete DocumentStatus : {}", id);
        documentStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
