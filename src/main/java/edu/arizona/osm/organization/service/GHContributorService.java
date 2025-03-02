package edu.arizona.osm.organization.service;

import edu.arizona.osm.organization.model.Contributor;
import edu.arizona.osm.organization.repository.ContributorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GHContributorService implements ContributorService {

    private static final Logger logger = LoggerFactory.getLogger(GHContributorService.class);

    private final ContributorRepository contributorRepository;

    public GHContributorService(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    @Override
    public Optional<Contributor> findById(Long id) {
        try {
            return contributorRepository.findById(id);
        }
        catch (Exception e) {
            logger.error("Error finding contributor by id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Contributor>> findAll() {
        try {
            var contributors = contributorRepository.findAll();
            return contributors.isEmpty() ? Optional.empty() : Optional.of(contributors);
        }
        catch (Exception e) {
            logger.error("Error finding all contributors", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contributor> save(Contributor contributor) {
        try {
            return Optional.of(contributorRepository.save(contributor));
        }
        catch (Exception e) {
            logger.error("Error saving contributor: {}", contributor, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contributor> update(Contributor contributor) {
        try {
            if (contributorRepository.existsById(contributor.getId())) {
                return Optional.of(contributorRepository.save(contributor));
            }
            return Optional.empty();
        }
        catch (Exception e) {
            logger.error("Error updating contributor: {}", contributor, e);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            if (contributorRepository.existsById(id)) {
                contributorRepository.deleteById(id);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            logger.error("Error deleting contributor by id: {}", id, e);
            return false;
        }
    }

}