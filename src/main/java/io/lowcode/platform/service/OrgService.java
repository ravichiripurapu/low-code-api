package io.lowcode.platform.service;

import io.lowcode.platform.domain.Org;
import io.lowcode.platform.repository.OrgRepository;
import io.lowcode.platform.service.dto.OrgDTO;
import io.lowcode.platform.service.mapper.OrgMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link io.lowcode.platform.domain.Org}.
 */
@Service
@Transactional
public class OrgService {

    private static final Logger LOG = LoggerFactory.getLogger(OrgService.class);

    private final OrgRepository orgRepository;

    private final OrgMapper orgMapper;

    public OrgService(OrgRepository orgRepository, OrgMapper orgMapper) {
        this.orgRepository = orgRepository;
        this.orgMapper = orgMapper;
    }

    /**
     * Save a org.
     *
     * @param orgDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgDTO save(OrgDTO orgDTO) {
        LOG.debug("Request to save Org : {}", orgDTO);
        Org org = orgMapper.toEntity(orgDTO);
        org = orgRepository.save(org);
        return orgMapper.toDto(org);
    }

    /**
     * Update a org.
     *
     * @param orgDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgDTO update(OrgDTO orgDTO) {
        LOG.debug("Request to update Org : {}", orgDTO);
        Org org = orgMapper.toEntity(orgDTO);
        org.setIsPersisted();
        org = orgRepository.save(org);
        return orgMapper.toDto(org);
    }

    /**
     * Partially update a org.
     *
     * @param orgDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrgDTO> partialUpdate(OrgDTO orgDTO) {
        LOG.debug("Request to partially update Org : {}", orgDTO);

        return orgRepository
            .findById(orgDTO.getId())
            .map(existingOrg -> {
                orgMapper.partialUpdate(existingOrg, orgDTO);

                return existingOrg;
            })
            .map(orgRepository::save)
            .map(orgMapper::toDto);
    }

    /**
     * Get all the orgs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Orgs");
        return orgRepository.findAll(pageable).map(orgMapper::toDto);
    }

    /**
     * Get one org by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrgDTO> findOne(String id) {
        LOG.debug("Request to get Org : {}", id);
        return orgRepository.findById(id).map(orgMapper::toDto);
    }

    /**
     * Delete the org by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Org : {}", id);
        orgRepository.deleteById(id);
    }
}
