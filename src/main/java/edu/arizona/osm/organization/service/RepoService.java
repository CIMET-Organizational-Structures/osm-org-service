package edu.arizona.osm.organization.service;

import edu.arizona.osm.organization.model.Repo;

import java.util.List;
import java.util.Optional;

public interface RepoService {

    Optional<Repo> findById(Long id);

    Optional<List<Repo>> findAll();

    Optional<Repo> save(Repo repo);

    Optional<Repo> update(Repo repo);

    boolean delete(Long id);

}