package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.DocumentMetadataVersionRepository;
import io.lowcode.platform.service.DocumentMetadataVersionService;
import io.lowcode.platform.service.dto.DocumentMetadataVersionDTO;
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
 * REST controller for managing {@link io.lowcode.platform.domain.DocumentMetadataVersion}.
 */
@RestController
@RequestMapping("/api/document-metadata-versions")
public class DocumentMetadataVersionResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentMetadataVersionResource.class);

    private static final String ENTITY_NAME = "documentMetadataVersion";

    @Value("${spring.clientApp.name}")
    private String applicationName;

    private final DocumentMetadataVersionService documentMetadataVersionService;

    private final DocumentMetadataVersionRepository documentMetadataVersionRepository;

    public DocumentMetadataVersionResource(
        DocumentMetadataVersionService documentMetadataVersionService,
        DocumentMetadataVersionRepository documentMetadataVersionRepository
    ) {
        this.documentMetadataVersionService = documentMetadataVersionService;
        this.documentMetadataVersionRepository = documentMetadataVersionRepository;
    }

    /**
     * {@code POST  /document-metadata-versions} : Create a new documentMetadataVersion.
     *
     * @param documentMetadataVersionDTO the documentMetadataVersionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentMetadataVersionDTO, or with status {@code 400 (Bad Request)} if the documentMetadataVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocumentMetadataVersionDTO> createDocumentMetadataVersion(
        @RequestBody DocumentMetadataVersionDTO documentMetadataVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save DocumentMetadataVersion : {}", documentMetadataVersionDTO);
        if (documentMetadataVersionRepository.existsById(documentMetadataVersionDTO.getId())) {
            throw new BadRequestAlertException("documentMetadataVersion already exists", ENTITY_NAME, "idexists");
        }
        documentMetadataVersionDTO = documentMetadataVersionService.save(documentMetadataVersionDTO);
        return ResponseEntity.created(new URI("/api/document-metadata-versions/" + documentMetadataVersionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentMetadataVersionDTO.getId()))
            .body(documentMetadataVersionDTO);
    }

    /**
     * {@code PUT  /document-metadata-versions/:id} : Updates an existing documentMetadataVersion.
     *
     * @param id the id of the documentMetadataVersionDTO to save.
     * @param documentMetadataVersionDTO the documentMetadataVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentMetadataVersionDTO,
     * or with status {@code 400 (Bad Request)} if the documentMetadataVersionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentMetadataVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentMetadataVersionDTO> updateDocumentMetadataVersion(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DocumentMetadataVersionDTO documentMetadataVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DocumentMetadataVersion : {}, {}", id, documentMetadataVersionDTO);
        if (documentMetadataVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentMetadataVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentMetadataVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentMetadataVersionDTO = documentMetadataVersionService.update(documentMetadataVersionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentMetadataVersionDTO.getId()))
            .body(documentMetadataVersionDTO);
    }

    /**
     * {@code PATCH  /document-metadata-versions/:id} : Partial updates given fields of an existing documentMetadataVersion, field will ignore if it is null
     *
     * @param id the id of the documentMetadataVersionDTO to save.
     * @param documentMetadataVersionDTO the documentMetadataVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentMetadataVersionDTO,
     * or with status {@code 400 (Bad Request)} if the documentMetadataVersionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentMetadataVersionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentMetadataVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentMetadataVersionDTO> partialUpdateDocumentMetadataVersion(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DocumentMetadataVersionDTO documentMetadataVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DocumentMetadataVersion partially : {}, {}", id, documentMetadataVersionDTO);
        if (documentMetadataVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentMetadataVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentMetadataVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentMetadataVersionDTO> result = documentMetadataVersionService.partialUpdate(documentMetadataVersionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentMetadataVersionDTO.getId())
        );
    }

    /**
     * {@code GET  /document-metadata-versions} : get all the documentMetadataVersions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentMetadataVersions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DocumentMetadataVersionDTO>> getAllDocumentMetadataVersions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DocumentMetadataVersions");
        Page<DocumentMetadataVersionDTO> page = documentMetadataVersionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-metadata-versions/:id} : get the "id" documentMetadataVersion.
     *
     * @param id the id of the documentMetadataVersionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentMetadataVersionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentMetadataVersionDTO> getDocumentMetadataVersion(@PathVariable("id") String id) {
        LOG.debug("REST request to get DocumentMetadataVersion : {}", id);
        Optional<DocumentMetadataVersionDTO> documentMetadataVersionDTO = documentMetadataVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentMetadataVersionDTO);
    }

    /**
     * {@code DELETE  /document-metadata-versions/:id} : delete the "id" documentMetadataVersion.
     *
     * @param id the id of the documentMetadataVersionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentMetadataVersion(@PathVariable("id") String id) {
        LOG.debug("REST request to delete DocumentMetadataVersion : {}", id);
        documentMetadataVersionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
