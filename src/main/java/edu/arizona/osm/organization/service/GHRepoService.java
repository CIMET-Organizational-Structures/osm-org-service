package edu.arizona.osm.organization.service;

import edu.arizona.osm.organization.model.Repo;
import edu.arizona.osm.organization.repository.RepoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GHRepoService implements RepoService {

    private static final Logger logger = LoggerFactory.getLogger(GHRepoService.class);

    private final RepoRepository repoRepository;

    public GHRepoService(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    @Override
    public Optional<Repo> findById(Long id) {
        try {
            return repoRepository.findById(id);
        }
        catch (Exception e) {
            logger.error("Error finding repo by id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Repo>> findAll() {
        try {
            var repos = repoRepository.findAll();
            return repos.isEmpty() ? Optional.empty() : Optional.of(repos);
        }
        catch (Exception e) {
            logger.error("Error finding all repos", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Repo> save(Repo repo) {
        try {
            return Optional.of(repoRepository.save(repo));
        }
        catch (Exception e) {
            logger.error("Error saving repo: {}", repo, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Repo> update(Repo repo) {
        try {
            if (repoRepository.existsById(repo.getId())) {
                return Optional.of(repoRepository.save(repo));
            }
            return Optional.empty();
        }
        catch (Exception e) {
            logger.error("Error updating repo: {}", repo, e);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            if (repoRepository.existsById(id)) {
                repoRepository.deleteById(id);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            logger.error("Error deleting repo by id: {}", id, e);
            return false;
        }
    }

}