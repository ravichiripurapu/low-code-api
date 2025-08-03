package io.lowcode.platform.web.rest;

import io.lowcode.platform.repository.DocumentDataRepository;
import io.lowcode.platform.service.DocumentDataService;
import io.lowcode.platform.service.dto.DocumentDataDTO;
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
 * REST controller for managing {@link io.lowcode.platform.domain.DocumentData}.
 */
@RestController
@RequestMapping("/api/document-data")
public class DocumentDataResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentDataResource.class);

    private static final String ENTITY_NAME = "documentData";

    @Value("${spring.clientApp.name}")
    private String applicationName;

    private final DocumentDataService documentDataService;

    private final DocumentDataRepository documentDataRepository;

    public DocumentDataResource(DocumentDataService documentDataService, DocumentDataRepository documentDataRepository) {
        this.documentDataService = documentDataService;
        this.documentDataRepository = documentDataRepository;
    }

    /**
     * {@code POST  /document-data} : Create a new documentData.
     *
     * @param documentDataDTO the documentDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentDataDTO, or with status {@code 400 (Bad Request)} if the documentData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocumentDataDTO> createDocumentData(@RequestBody DocumentDataDTO documentDataDTO) throws URISyntaxException {
        LOG.debug("REST request to save DocumentData : {}", documentDataDTO);
        if (documentDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documentDataDTO = documentDataService.save(documentDataDTO);
        return ResponseEntity.created(new URI("/api/document-data/" + documentDataDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentDataDTO.getId().toString()))
            .body(documentDataDTO);
    }

    /**
     * {@code PUT  /document-data/:id} : Updates an existing documentData.
     *
     * @param id the id of the documentDataDTO to save.
     * @param documentDataDTO the documentDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentDataDTO,
     * or with status {@code 400 (Bad Request)} if the documentDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentDataDTO> updateDocumentData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentDataDTO documentDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DocumentData : {}, {}", id, documentDataDTO);
        if (documentDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentDataDTO = documentDataService.update(documentDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentDataDTO.getId().toString()))
            .body(documentDataDTO);
    }

    /**
     * {@code PATCH  /document-data/:id} : Partial updates given fields of an existing documentData, field will ignore if it is null
     *
     * @param id the id of the documentDataDTO to save.
     * @param documentDataDTO the documentDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentDataDTO,
     * or with status {@code 400 (Bad Request)} if the documentDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentDataDTO> partialUpdateDocumentData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentDataDTO documentDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DocumentData partially : {}, {}", id, documentDataDTO);
        if (documentDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentDataDTO> result = documentDataService.partialUpdate(documentDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /document-data} : get all the documentData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentData in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DocumentDataDTO>> getAllDocumentData(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of DocumentData");
        Page<DocumentDataDTO> page = documentDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-data/:id} : get the "id" documentData.
     *
     * @param id the id of the documentDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDataDTO> getDocumentData(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DocumentData : {}", id);
        Optional<DocumentDataDTO> documentDataDTO = documentDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentDataDTO);
    }

    /**
     * {@code DELETE  /document-data/:id} : delete the "id" documentData.
     *
     * @param id the id of the documentDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentData(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DocumentData : {}", id);
        documentDataService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
