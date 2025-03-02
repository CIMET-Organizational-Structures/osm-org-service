package edu.arizona.osm.organization.controller;

import edu.arizona.osm.organization.model.Contributor;
import edu.arizona.osm.organization.service.ContributorService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contributors")
public class ContributorController {

    private final ContributorService contributorService;

    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contributor> getContributor(@PathVariable Long id) {
        return contributorService.findById(id).map(contributor -> {
            addHateoasTo(contributor);
            return ResponseEntity.ok(contributor);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Contributor>> getAllContributors() {
        return contributorService.findAll().map(contributors -> {
            contributors.forEach(this::addHateoasTo);
            return ResponseEntity.ok(contributors);
        }).orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Contributor> createContributor(@RequestBody Contributor contributor) {
        if (contributorService.findById(contributor.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return contributorService.save(contributor).map(savedContributor -> {
            addHateoasTo(savedContributor);
            return ResponseEntity.ok(savedContributor);
        }).orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contributor> updateContributor(@PathVariable Long id, @RequestBody Contributor contributor) {
        if (contributorService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return contributorService.update(contributor).map(updatedContributor -> {
            addHateoasTo(updatedContributor);
            return ResponseEntity.ok(updatedContributor);
        }).orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContributor(@PathVariable Long id) {
        try {
            if (contributorService.findById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            if (contributorService.delete(id)) {
                return ResponseEntity.noContent().build();
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void addHateoasTo(Contributor contributor) {
        contributor.add(
                WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(ContributorController.class).getContributor(contributor.getId()))
                    .withSelfRel(),
                WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(ContributorController.class).createContributor(contributor))
                    .withRel("createContributor"),
                WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(ContributorController.class)
                        .updateContributor(contributor.getId(), contributor))
                    .withRel("updateContributor"),
                WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(ContributorController.class)
                        .deleteContributor(contributor.getId()))
                    .withRel("deleteContributor"));
    }

}