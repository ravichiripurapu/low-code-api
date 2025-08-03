package io.lowcode.platform.service;

import io.lowcode.platform.domain.PublicationFormVersion;
import io.lowcode.platform.repository.PublicationFormVersionRepository;
import io.lowcode.platform.service.dto.PublicationFormVersionDTO;
import io.lowcode.platform.service.mapper.PublicationFormVersionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.lowcode.platform.domain.PublicationFormVersion}.
 */
@Service
@Transactional
public class PublicationFormVersionService {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationFormVersionService.class);

    private final PublicationFormVersionRepository publicationFormVersionRepository;

    private final PublicationFormVersionMapper publicationFormVersionMapper;

    public PublicationFormVersionService(
        PublicationFormVersionRepository publicationFormVersionRepository,
        PublicationFormVersionMapper publicationFormVersionMapper
    ) {
        this.publicationFormVersionRepository = publicationFormVersionRepository;
        this.publicationFormVersionMapper = publicationFormVersionMapper;
    }

    /**
     * Save a publicationFormVersion.
     *
     * @param publicationFormVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationFormVersionDTO save(PublicationFormVersionDTO publicationFormVersionDTO) {
        LOG.debug("Request to save PublicationFormVersion : {}", publicationFormVersionDTO);
        PublicationFormVersion publicationFormVersion = publicationFormVersionMapper.toEntity(publicationFormVersionDTO);
        publicationFormVersion = publicationFormVersionRepository.save(publicationFormVersion);
        return publicationFormVersionMapper.toDto(publicationFormVersion);
    }

    /**
     * Update a publicationFormVersion.
     *
     * @param publicationFormVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationFormVersionDTO update(PublicationFormVersionDTO publicationFormVersionDTO) {
        LOG.debug("Request to update PublicationFormVersion : {}", publicationFormVersionDTO);
        PublicationFormVersion publicationFormVersion = publicationFormVersionMapper.toEntity(publicationFormVersionDTO);
        publicationFormVersion.setIsPersisted();
        publicationFormVersion = publicationFormVersionRepository.save(publicationFormVersion);
        return publicationFormVersionMapper.toDto(publicationFormVersion);
    }

    /**
     * Partially update a publicationFormVersion.
     *
     * @param publicationFormVersionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicationFormVersionDTO> partialUpdate(PublicationFormVersionDTO publicationFormVersionDTO) {
        LOG.debug("Request to partially update PublicationFormVersion : {}", publicationFormVersionDTO);

        return publicationFormVersionRepository
            .findById(publicationFormVersionDTO.getId())
            .map(existingPublicationFormVersion -> {
                publicationFormVersionMapper.partialUpdate(existingPublicationFormVersion, publicationFormVersionDTO);

                return existingPublicationFormVersion;
            })
            .map(publicationFormVersionRepository::save)
            .map(publicationFormVersionMapper::toDto);
    }

    /**
     * Get all the publicationFormVersions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicationFormVersionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PublicationFormVersions");
        return publicationFormVersionRepository.findAll(pageable).map(publicationFormVersionMapper::toDto);
    }

    /**
     * Get one publicationFormVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicationFormVersionDTO> findOne(String id) {
        LOG.debug("Request to get PublicationFormVersion : {}", id);
        return publicationFormVersionRepository.findById(id).map(publicationFormVersionMapper::toDto);
    }

    /**
     * Delete the publicationFormVersion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete PublicationFormVersion : {}", id);
        publicationFormVersionRepository.deleteById(id);
    }
}
