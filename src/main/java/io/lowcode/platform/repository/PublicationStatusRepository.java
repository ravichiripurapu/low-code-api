package io.lowcode.platform.repository;

import io.lowcode.platform.domain.PublicationStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PublicationStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationStatusRepository extends JpaRepository<PublicationStatus, String> {}
