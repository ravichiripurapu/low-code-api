package io.lowcode.platform.repository;

import io.lowcode.platform.domain.Org;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Org entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgRepository extends JpaRepository<Org, String> {}
