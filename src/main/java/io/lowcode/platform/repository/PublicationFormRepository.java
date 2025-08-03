package io.lowcode.platform.repository;

import io.lowcode.platform.domain.PublicationForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PublicationForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicationFormRepository extends JpaRepository<PublicationForm, Long> {}
