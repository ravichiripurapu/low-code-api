package io.lowcode.platform.service;

import io.lowcode.platform.domain.DocumentStatus;
import io.lowcode.platform.repository.DocumentStatusRepository;
import io.lowcode.platform.service.dto.DocumentStatusDTO;
import io.lowcode.platform.service.mapper.DocumentStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentStatus}.
 */
@Service
@Transactional
public class DocumentStatusService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentStatusService.class);

    private final DocumentStatusRepository documentStatusRepository;

    private final DocumentStatusMapper documentStatusMapper;

    public DocumentStatusService(DocumentStatusRepository documentStatusRepository, DocumentStatusMapper documentStatusMapper) {
        this.documentStatusRepository = documentStatusRepository;
        this.documentStatusMapper = documentStatusMapper;
    }

    /**
     * Save a documentStatus.
     *
     * @param documentStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentStatusDTO save(DocumentStatusDTO documentStatusDTO) {
        LOG.debug("Request to save DocumentStatus : {}", documentStatusDTO);
        DocumentStatus documentStatus = documentStatusMapper.toEntity(documentStatusDTO);
        documentStatus = documentStatusRepository.save(documentStatus);
        return documentStatusMapper.toDto(documentStatus);
    }

    /**
     * Update a documentStatus.
     *
     * @param documentStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentStatusDTO update(DocumentStatusDTO documentStatusDTO) {
        LOG.debug("Request to update DocumentStatus : {}", documentStatusDTO);
        DocumentStatus documentStatus = documentStatusMapper.toEntity(documentStatusDTO);
        documentStatus.setIsPersisted();
        documentStatus = documentStatusRepository.save(documentStatus);
        return documentStatusMapper.toDto(documentStatus);
    }

    /**
     * Partially update a documentStatus.
     *
     * @param documentStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentStatusDTO> partialUpdate(DocumentStatusDTO documentStatusDTO) {
        LOG.debug("Request to partially update DocumentStatus : {}", documentStatusDTO);

        return documentStatusRepository
            .findById(documentStatusDTO.getId())
            .map(existingDocumentStatus -> {
                documentStatusMapper.partialUpdate(existingDocumentStatus, documentStatusDTO);

                return existingDocumentStatus;
            })
            .map(documentStatusRepository::save)
            .map(documentStatusMapper::toDto);
    }

    /**
     * Get all the documentStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentStatusDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DocumentStatuses");
        return documentStatusRepository.findAll(pageable).map(documentStatusMapper::toDto);
    }

    /**
     * Get one documentStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStatusDTO> findOne(String id) {
        LOG.debug("Request to get DocumentStatus : {}", id);
        return documentStatusRepository.findById(id).map(documentStatusMapper::toDto);
    }

    /**
     * Delete the documentStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete DocumentStatus : {}", id);
        documentStatusRepository.deleteById(id);
    }
}
