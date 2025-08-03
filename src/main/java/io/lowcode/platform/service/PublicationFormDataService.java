package io.lowcode.platform.service;

import io.lowcode.platform.domain.PublicationFormData;
import io.lowcode.platform.repository.PublicationFormDataRepository;
import io.lowcode.platform.service.dto.PublicationFormDataDTO;
import io.lowcode.platform.service.mapper.PublicationFormDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.lowcode.platform.domain.PublicationFormData}.
 */
@Service
@Transactional
public class PublicationFormDataService {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationFormDataService.class);

    private final PublicationFormDataRepository publicationFormDataRepository;

    private final PublicationFormDataMapper publicationFormDataMapper;

    public PublicationFormDataService(
        PublicationFormDataRepository publicationFormDataRepository,
        PublicationFormDataMapper publicationFormDataMapper
    ) {
        this.publicationFormDataRepository = publicationFormDataRepository;
        this.publicationFormDataMapper = publicationFormDataMapper;
    }

    /**
     * Save a publicationFormData.
     *
     * @param publicationFormDataDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationFormDataDTO save(PublicationFormDataDTO publicationFormDataDTO) {
        LOG.debug("Request to save PublicationFormData : {}", publicationFormDataDTO);
        PublicationFormData publicationFormData = publicationFormDataMapper.toEntity(publicationFormDataDTO);
        publicationFormData = publicationFormDataRepository.save(publicationFormData);
        return publicationFormDataMapper.toDto(publicationFormData);
    }

    /**
     * Update a publicationFormData.
     *
     * @param publicationFormDataDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationFormDataDTO update(PublicationFormDataDTO publicationFormDataDTO) {
        LOG.debug("Request to update PublicationFormData : {}", publicationFormDataDTO);
        PublicationFormData publicationFormData = publicationFormDataMapper.toEntity(publicationFormDataDTO);
        publicationFormData = publicationFormDataRepository.save(publicationFormData);
        return publicationFormDataMapper.toDto(publicationFormData);
    }

    /**
     * Partially update a publicationFormData.
     *
     * @param publicationFormDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicationFormDataDTO> partialUpdate(PublicationFormDataDTO publicationFormDataDTO) {
        LOG.debug("Request to partially update PublicationFormData : {}", publicationFormDataDTO);

        return publicationFormDataRepository
            .findById(publicationFormDataDTO.getId())
            .map(existingPublicationFormData -> {
                publicationFormDataMapper.partialUpdate(existingPublicationFormData, publicationFormDataDTO);

                return existingPublicationFormData;
            })
            .map(publicationFormDataRepository::save)
            .map(publicationFormDataMapper::toDto);
    }

    /**
     * Get all the publicationFormData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicationFormDataDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PublicationFormData");
        return publicationFormDataRepository.findAll(pageable).map(publicationFormDataMapper::toDto);
    }

    /**
     * Get one publicationFormData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicationFormDataDTO> findOne(Long id) {
        LOG.debug("Request to get PublicationFormData : {}", id);
        return publicationFormDataRepository.findById(id).map(publicationFormDataMapper::toDto);
    }

    /**
     * Delete the publicationFormData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PublicationFormData : {}", id);
        publicationFormDataRepository.deleteById(id);
    }
}
