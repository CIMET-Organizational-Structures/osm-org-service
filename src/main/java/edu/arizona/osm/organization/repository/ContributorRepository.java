package edu.arizona.osm.organization.repository;

import edu.arizona.osm.organization.model.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    Long id(Long id);

}
