package io.lowcode.platform.service;

import io.lowcode.platform.domain.DocumentMetadata;
import io.lowcode.platform.repository.DocumentMetadataRepository;
import io.lowcode.platform.service.dto.DocumentMetadataDTO;
import io.lowcode.platform.service.mapper.DocumentMetadataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentMetadata}.
 */
@Service
@Transactional
public class DocumentMetadataService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentMetadataService.class);

    private final DocumentMetadataRepository documentMetadataRepository;

    private final DocumentMetadataMapper documentMetadataMapper;

    public DocumentMetadataService(DocumentMetadataRepository documentMetadataRepository, DocumentMetadataMapper documentMetadataMapper) {
        this.documentMetadataRepository = documentMetadataRepository;
        this.documentMetadataMapper = documentMetadataMapper;
    }

    /**
     * Save a documentMetadata.
     *
     * @param documentMetadataDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentMetadataDTO save(DocumentMetadataDTO documentMetadataDTO) {
        LOG.debug("Request to save DocumentMetadata : {}", documentMetadataDTO);
        DocumentMetadata documentMetadata = documentMetadataMapper.toEntity(documentMetadataDTO);
        documentMetadata = documentMetadataRepository.save(documentMetadata);
        return documentMetadataMapper.toDto(documentMetadata);
    }

    /**
     * Update a documentMetadata.
     *
     * @param documentMetadataDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentMetadataDTO update(DocumentMetadataDTO documentMetadataDTO) {
        LOG.debug("Request to update DocumentMetadata : {}", documentMetadataDTO);
        DocumentMetadata documentMetadata = documentMetadataMapper.toEntity(documentMetadataDTO);
        documentMetadata = documentMetadataRepository.save(documentMetadata);
        return documentMetadataMapper.toDto(documentMetadata);
    }

    /**
     * Partially update a documentMetadata.
     *
     * @param documentMetadataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentMetadataDTO> partialUpdate(DocumentMetadataDTO documentMetadataDTO) {
        LOG.debug("Request to partially update DocumentMetadata : {}", documentMetadataDTO);

        return documentMetadataRepository
            .findById(documentMetadataDTO.getId())
            .map(existingDocumentMetadata -> {
                documentMetadataMapper.partialUpdate(existingDocumentMetadata, documentMetadataDTO);

                return existingDocumentMetadata;
            })
            .map(documentMetadataRepository::save)
            .map(documentMetadataMapper::toDto);
    }

    /**
     * Get all the documentMetadata.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentMetadataDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DocumentMetadata");
        return documentMetadataRepository.findAll(pageable).map(documentMetadataMapper::toDto);
    }

    /**
     * Get one documentMetadata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentMetadataDTO> findOne(Long id) {
        LOG.debug("Request to get DocumentMetadata : {}", id);
        return documentMetadataRepository.findById(id).map(documentMetadataMapper::toDto);
    }

    /**
     * Delete the documentMetadata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete DocumentMetadata : {}", id);
        documentMetadataRepository.deleteById(id);
    }
}
