package io.lowcode.platform.service;

import io.lowcode.platform.domain.PublicationStatus;
import io.lowcode.platform.repository.PublicationStatusRepository;
import io.lowcode.platform.service.dto.PublicationStatusDTO;
import io.lowcode.platform.service.mapper.PublicationStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.lowcode.platform.domain.PublicationStatus}.
 */
@Service
@Transactional
public class PublicationStatusService {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationStatusService.class);

    private final PublicationStatusRepository publicationStatusRepository;

    private final PublicationStatusMapper publicationStatusMapper;

    public PublicationStatusService(
        PublicationStatusRepository publicationStatusRepository,
        PublicationStatusMapper publicationStatusMapper
    ) {
        this.publicationStatusRepository = publicationStatusRepository;
        this.publicationStatusMapper = publicationStatusMapper;
    }

    /**
     * Save a publicationStatus.
     *
     * @param publicationStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationStatusDTO save(PublicationStatusDTO publicationStatusDTO) {
        LOG.debug("Request to save PublicationStatus : {}", publicationStatusDTO);
        PublicationStatus publicationStatus = publicationStatusMapper.toEntity(publicationStatusDTO);
        publicationStatus = publicationStatusRepository.save(publicationStatus);
        return publicationStatusMapper.toDto(publicationStatus);
    }

    /**
     * Update a publicationStatus.
     *
     * @param publicationStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationStatusDTO update(PublicationStatusDTO publicationStatusDTO) {
        LOG.debug("Request to update PublicationStatus : {}", publicationStatusDTO);
        PublicationStatus publicationStatus = publicationStatusMapper.toEntity(publicationStatusDTO);
        publicationStatus.setIsPersisted();
        publicationStatus = publicationStatusRepository.save(publicationStatus);
        return publicationStatusMapper.toDto(publicationStatus);
    }

    /**
     * Partially update a publicationStatus.
     *
     * @param publicationStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicationStatusDTO> partialUpdate(PublicationStatusDTO publicationStatusDTO) {
        LOG.debug("Request to partially update PublicationStatus : {}", publicationStatusDTO);

        return publicationStatusRepository
            .findById(publicationStatusDTO.getId())
            .map(existingPublicationStatus -> {
                publicationStatusMapper.partialUpdate(existingPublicationStatus, publicationStatusDTO);

                return existingPublicationStatus;
            })
            .map(publicationStatusRepository::save)
            .map(publicationStatusMapper::toDto);
    }

    /**
     * Get all the publicationStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicationStatusDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PublicationStatuses");
        return publicationStatusRepository.findAll(pageable).map(publicationStatusMapper::toDto);
    }

    /**
     * Get one publicationStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicationStatusDTO> findOne(String id) {
        LOG.debug("Request to get PublicationStatus : {}", id);
        return publicationStatusRepository.findById(id).map(publicationStatusMapper::toDto);
    }

    /**
     * Delete the publicationStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete PublicationStatus : {}", id);
        publicationStatusRepository.deleteById(id);
    }
}
