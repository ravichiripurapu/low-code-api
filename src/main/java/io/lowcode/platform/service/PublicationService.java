package io.lowcode.platform.service;

import io.lowcode.platform.domain.Publication;
import io.lowcode.platform.repository.PublicationRepository;
import io.lowcode.platform.service.dto.PublicationDTO;
import io.lowcode.platform.service.mapper.PublicationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.lowcode.platform.domain.Publication}.
 */
@Service
@Transactional
public class PublicationService {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationService.class);

    private final PublicationRepository publicationRepository;

    private final PublicationMapper publicationMapper;

    public PublicationService(PublicationRepository publicationRepository, PublicationMapper publicationMapper) {
        this.publicationRepository = publicationRepository;
        this.publicationMapper = publicationMapper;
    }

    /**
     * Save a publication.
     *
     * @param publicationDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationDTO save(PublicationDTO publicationDTO) {
        LOG.debug("Request to save Publication : {}", publicationDTO);
        Publication publication = publicationMapper.toEntity(publicationDTO);
        publication = publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }

    /**
     * Update a publication.
     *
     * @param publicationDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationDTO update(PublicationDTO publicationDTO) {
        LOG.debug("Request to update Publication : {}", publicationDTO);
        Publication publication = publicationMapper.toEntity(publicationDTO);
        publication.setIsPersisted();
        publication = publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }

    /**
     * Partially update a publication.
     *
     * @param publicationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicationDTO> partialUpdate(PublicationDTO publicationDTO) {
        LOG.debug("Request to partially update Publication : {}", publicationDTO);

        return publicationRepository
            .findById(publicationDTO.getId())
            .map(existingPublication -> {
                publicationMapper.partialUpdate(existingPublication, publicationDTO);

                return existingPublication;
            })
            .map(publicationRepository::save)
            .map(publicationMapper::toDto);
    }

    /**
     * Get all the publications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Publications");
        return publicationRepository.findAll(pageable).map(publicationMapper::toDto);
    }

    /**
     * Get one publication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicationDTO> findOne(String id) {
        LOG.debug("Request to get Publication : {}", id);
        return publicationRepository.findById(id).map(publicationMapper::toDto);
    }

    /**
     * Delete the publication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Publication : {}", id);
        publicationRepository.deleteById(id);
    }
}
