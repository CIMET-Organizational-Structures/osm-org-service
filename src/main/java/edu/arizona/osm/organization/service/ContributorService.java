package edu.arizona.osm.organization.service;

import edu.arizona.osm.organization.model.Contributor;

import java.util.List;
import java.util.Optional;

public interface ContributorService {

    Optional<Contributor> findById(Long id);

    Optional<List<Contributor>> findAll();

    Optional<Contributor> save(Contributor contributor);

    Optional<Contributor> update(Contributor contributor);

    boolean delete(Long id);

}
