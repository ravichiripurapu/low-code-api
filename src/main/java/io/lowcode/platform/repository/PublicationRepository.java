package io.lowcode.platform.repository;

import io.lowcode.platform.domain.Publication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Publication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationRepository extends JpaRepository<Publication, String> {}
