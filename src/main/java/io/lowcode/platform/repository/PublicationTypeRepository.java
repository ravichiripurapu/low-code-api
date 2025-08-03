package io.lowcode.platform.repository;

import io.lowcode.platform.domain.PublicationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PublicationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationTypeRepository extends JpaRepository<PublicationType, String> {}
