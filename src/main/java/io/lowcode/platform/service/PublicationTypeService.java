package io.lowcode.platform.service;

import io.lowcode.platform.domain.PublicationType;
import io.lowcode.platform.repository.PublicationTypeRepository;
import io.lowcode.platform.service.dto.PublicationTypeDTO;
import io.lowcode.platform.service.mapper.PublicationTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.lowcode.platform.domain.PublicationType}.
 */
@Service
@Transactional
public class PublicationTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationTypeService.class);

    private final PublicationTypeRepository publicationTypeRepository;

    private final PublicationTypeMapper publicationTypeMapper;

    public PublicationTypeService(PublicationTypeRepository publicationTypeRepository, PublicationTypeMapper publicationTypeMapper) {
        this.publicationTypeRepository = publicationTypeRepository;
        this.publicationTypeMapper = publicationTypeMapper;
    }

    /**
     * Save a publicationType.
     *
     * @param publicationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationTypeDTO save(PublicationTypeDTO publicationTypeDTO) {
        LOG.debug("Request to save PublicationType : {}", publicationTypeDTO);
        PublicationType publicationType = publicationTypeMapper.toEntity(publicationTypeDTO);
        publicationType = publicationTypeRepository.save(publicationType);
        return publicationTypeMapper.toDto(publicationType);
    }

    /**
     * Update a publicationType.
     *
     * @param publicationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationTypeDTO update(PublicationTypeDTO publicationTypeDTO) {
        LOG.debug("Request to update PublicationType : {}", publicationTypeDTO);
        PublicationType publicationType = publicationTypeMapper.toEntity(publicationTypeDTO);
        publicationType.setIsPersisted();
        publicationType = publicationTypeRepository.save(publicationType);
        return publicationTypeMapper.toDto(publicationType);
    }

    /**
     * Partially update a publicationType.
     *
     * @param publicationTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicationTypeDTO> partialUpdate(PublicationTypeDTO publicationTypeDTO) {
        LOG.debug("Request to partially update PublicationType : {}", publicationTypeDTO);

        return publicationTypeRepository
            .findById(publicationTypeDTO.getId())
            .map(existingPublicationType -> {
                publicationTypeMapper.partialUpdate(existingPublicationType, publicationTypeDTO);

                return existingPublicationType;
            })
            .map(publicationTypeRepository::save)
            .map(publicationTypeMapper::toDto);
    }

    /**
     * Get all the publicationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicationTypeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PublicationTypes");
        return publicationTypeRepository.findAll(pageable).map(publicationTypeMapper::toDto);
    }

    /**
     * Get one publicationType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicationTypeDTO> findOne(String id) {
        LOG.debug("Request to get PublicationType : {}", id);
        return publicationTypeRepository.findById(id).map(publicationTypeMapper::toDto);
    }

    /**
     * Delete the publicationType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete PublicationType : {}", id);
        publicationTypeRepository.deleteById(id);
    }
}
