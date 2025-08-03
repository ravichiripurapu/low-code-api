package io.lowcode.platform.repository;

import io.lowcode.platform.domain.PublicationFormData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PublicationFormData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationFormDataRepository extends JpaRepository<PublicationFormData, Long> {}
