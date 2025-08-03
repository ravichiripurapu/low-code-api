package io.lowcode.platform.service;

import io.lowcode.platform.domain.PublicationForm;
import io.lowcode.platform.repository.PublicationFormRepository;
import io.lowcode.platform.service.dto.PublicationFormDTO;
import io.lowcode.platform.service.mapper.PublicationFormMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.lowcode.platform.domain.PublicationForm}.
 */
@Service
@Transactional
public class PublicationFormService {

    private static final Logger LOG = LoggerFactory.getLogger(PublicationFormService.class);

    private final PublicationFormRepository publicationFormRepository;

    private final PublicationFormMapper publicationFormMapper;

    public PublicationFormService(PublicationFormRepository publicationFormRepository, PublicationFormMapper publicationFormMapper) {
        this.publicationFormRepository = publicationFormRepository;
        this.publicationFormMapper = publicationFormMapper;
    }

    /**
     * Save a publicationForm.
     *
     * @param publicationFormDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationFormDTO save(PublicationFormDTO publicationFormDTO) {
        LOG.debug("Request to save PublicationForm : {}", publicationFormDTO);
        PublicationForm publicationForm = publicationFormMapper.toEntity(publicationFormDTO);
        publicationForm = publicationFormRepository.save(publicationForm);
        return publicationFormMapper.toDto(publicationForm);
    }

    /**
     * Update a publicationForm.
     *
     * @param publicationFormDTO the entity to save.
     * @return the persisted entity.
     */
    public PublicationFormDTO update(PublicationFormDTO publicationFormDTO) {
        LOG.debug("Request to update PublicationForm : {}", publicationFormDTO);
        PublicationForm publicationForm = publicationFormMapper.toEntity(publicationFormDTO);
        publicationForm = publicationFormRepository.save(publicationForm);
        return publicationFormMapper.toDto(publicationForm);
    }

    /**
     * Partially update a publicationForm.
     *
     * @param publicationFormDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublicationFormDTO> partialUpdate(PublicationFormDTO publicationFormDTO) {
        LOG.debug("Request to partially update PublicationForm : {}", publicationFormDTO);

        return publicationFormRepository
            .findById(publicationFormDTO.getId())
            .map(existingPublicationForm -> {
                publicationFormMapper.partialUpdate(existingPublicationForm, publicationFormDTO);

                return existingPublicationForm;
            })
            .map(publicationFormRepository::save)
            .map(publicationFormMapper::toDto);
    }

    /**
     * Get all the publicationForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PublicationFormDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PublicationForms");
        return publicationFormRepository.findAll(pageable).map(publicationFormMapper::toDto);
    }

    /**
     * Get one publicationForm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublicationFormDTO> findOne(Long id) {
        LOG.debug("Request to get PublicationForm : {}", id);
        return publicationFormRepository.findById(id).map(publicationFormMapper::toDto);
    }

    /**
     * Delete the publicationForm by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PublicationForm : {}", id);
        publicationFormRepository.deleteById(id);
    }
}
