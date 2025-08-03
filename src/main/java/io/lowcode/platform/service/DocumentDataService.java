package io.lowcode.platform.service;

import io.lowcode.platform.domain.DocumentData;
import io.lowcode.platform.repository.DocumentDataRepository;
import io.lowcode.platform.service.dto.DocumentDataDTO;
import io.lowcode.platform.service.mapper.DocumentDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentData}.
 */
@Service
@Transactional
public class DocumentDataService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentDataService.class);

    private final DocumentDataRepository documentDataRepository;

    private final DocumentDataMapper documentDataMapper;

    public DocumentDataService(DocumentDataRepository documentDataRepository, DocumentDataMapper documentDataMapper) {
        this.documentDataRepository = documentDataRepository;
        this.documentDataMapper = documentDataMapper;
    }

    /**
     * Save a documentData.
     *
     * @param documentDataDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentDataDTO save(DocumentDataDTO documentDataDTO) {
        LOG.debug("Request to save DocumentData : {}", documentDataDTO);
        DocumentData documentData = documentDataMapper.toEntity(documentDataDTO);
        documentData = documentDataRepository.save(documentData);
        return documentDataMapper.toDto(documentData);
    }

    /**
     * Update a documentData.
     *
     * @param documentDataDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentDataDTO update(DocumentDataDTO documentDataDTO) {
        LOG.debug("Request to update DocumentData : {}", documentDataDTO);
        DocumentData documentData = documentDataMapper.toEntity(documentDataDTO);
        documentData = documentDataRepository.save(documentData);
        return documentDataMapper.toDto(documentData);
    }

    /**
     * Partially update a documentData.
     *
     * @param documentDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentDataDTO> partialUpdate(DocumentDataDTO documentDataDTO) {
        LOG.debug("Request to partially update DocumentData : {}", documentDataDTO);

        return documentDataRepository
            .findById(documentDataDTO.getId())
            .map(existingDocumentData -> {
                documentDataMapper.partialUpdate(existingDocumentData, documentDataDTO);

                return existingDocumentData;
            })
            .map(documentDataRepository::save)
            .map(documentDataMapper::toDto);
    }

    /**
     * Get all the documentData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentDataDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DocumentData");
        return documentDataRepository.findAll(pageable).map(documentDataMapper::toDto);
    }

    /**
     * Get one documentData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentDataDTO> findOne(Long id) {
        LOG.debug("Request to get DocumentData : {}", id);
        return documentDataRepository.findById(id).map(documentDataMapper::toDto);
    }

    /**
     * Delete the documentData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete DocumentData : {}", id);
        documentDataRepository.deleteById(id);
    }
}
