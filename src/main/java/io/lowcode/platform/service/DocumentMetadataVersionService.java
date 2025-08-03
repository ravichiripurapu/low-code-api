package io.lowcode.platform.service;

import io.lowcode.platform.domain.DocumentMetadataVersion;
import io.lowcode.platform.repository.DocumentMetadataVersionRepository;
import io.lowcode.platform.service.dto.DocumentMetadataVersionDTO;
import io.lowcode.platform.service.mapper.DocumentMetadataVersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentMetadataVersion}.
 */
@Service
@Transactional
public class DocumentMetadataVersionService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentMetadataVersionService.class);

    private final DocumentMetadataVersionRepository documentMetadataVersionRepository;

    private final DocumentMetadataVersionMapper documentMetadataVersionMapper;

    public DocumentMetadataVersionService(
        DocumentMetadataVersionRepository documentMetadataVersionRepository,
        DocumentMetadataVersionMapper documentMetadataVersionMapper
    ) {
        this.documentMetadataVersionRepository = documentMetadataVersionRepository;
        this.documentMetadataVersionMapper = documentMetadataVersionMapper;
    }

    /**
     * Save a documentMetadataVersion.
     *
     * @param documentMetadataVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentMetadataVersionDTO save(DocumentMetadataVersionDTO documentMetadataVersionDTO) {
        LOG.debug("Request to save DocumentMetadataVersion : {}", documentMetadataVersionDTO);
        DocumentMetadataVersion documentMetadataVersion = documentMetadataVersionMapper.toEntity(documentMetadataVersionDTO);
        documentMetadataVersion = documentMetadataVersionRepository.save(documentMetadataVersion);
        return documentMetadataVersionMapper.toDto(documentMetadataVersion);
    }

    /**
     * Update a documentMetadataVersion.
     *
     * @param documentMetadataVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentMetadataVersionDTO update(DocumentMetadataVersionDTO documentMetadataVersionDTO) {
        LOG.debug("Request to update DocumentMetadataVersion : {}", documentMetadataVersionDTO);
        DocumentMetadataVersion documentMetadataVersion = documentMetadataVersionMapper.toEntity(documentMetadataVersionDTO);
        documentMetadataVersion.setIsPersisted();
        documentMetadataVersion = documentMetadataVersionRepository.save(documentMetadataVersion);
        return documentMetadataVersionMapper.toDto(documentMetadataVersion);
    }

    /**
     * Partially update a documentMetadataVersion.
     *
     * @param documentMetadataVersionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentMetadataVersionDTO> partialUpdate(DocumentMetadataVersionDTO documentMetadataVersionDTO) {
        LOG.debug("Request to partially update DocumentMetadataVersion : {}", documentMetadataVersionDTO);

        return documentMetadataVersionRepository
            .findById(documentMetadataVersionDTO.getId())
            .map(existingDocumentMetadataVersion -> {
                documentMetadataVersionMapper.partialUpdate(existingDocumentMetadataVersion, documentMetadataVersionDTO);

                return existingDocumentMetadataVersion;
            })
            .map(documentMetadataVersionRepository::save)
            .map(documentMetadataVersionMapper::toDto);
    }

    /**
     * Get all the documentMetadataVersions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentMetadataVersionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DocumentMetadataVersions");
        return documentMetadataVersionRepository.findAll(pageable).map(documentMetadataVersionMapper::toDto);
    }

    /**
     * Get one documentMetadataVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentMetadataVersionDTO> findOne(String id) {
        LOG.debug("Request to get DocumentMetadataVersion : {}", id);
        return documentMetadataVersionRepository.findById(id).map(documentMetadataVersionMapper::toDto);
    }

    /**
     * Delete the documentMetadataVersion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete DocumentMetadataVersion : {}", id);
        documentMetadataVersionRepository.deleteById(id);
    }
}
