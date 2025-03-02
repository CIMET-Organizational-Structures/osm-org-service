package edu.arizona.osm.organization.controller;

import edu.arizona.osm.organization.model.Repo;
import edu.arizona.osm.organization.service.RepoService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repos")
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repo> getRepo(@PathVariable Long id) {
        return repoService.findById(id).map(repo -> {
            addHateoasTo(repo);
            return ResponseEntity.ok(repo);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Repo>> getAllRepos() {
        return repoService.findAll().map(repos -> {
            repos.forEach(this::addHateoasTo);
            return ResponseEntity.ok(repos);
        }).orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Repo> createRepo(@RequestBody Repo repo) {
        if (repoService.findById(repo.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return repoService.save(repo).map(savedRepo -> {
            addHateoasTo(savedRepo);
            return ResponseEntity.ok(savedRepo);
        }).orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repo> updateRepo(@PathVariable Long id, @RequestBody Repo repo) {
        if (repoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return repoService.update(repo).map(updatedRepo -> {
            addHateoasTo(updatedRepo);
            return ResponseEntity.ok(updatedRepo);
        }).orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepo(@PathVariable Long id) {
        try {
            if (repoService.findById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            if (repoService.delete(id)) {
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

    private void addHateoasTo(Repo repo) {
        repo.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RepoController.class).getRepo(repo.getId()))
                    .withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RepoController.class).createRepo(repo))
                    .withRel("createRepo"),
                WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(RepoController.class).updateRepo(repo.getId(), repo))
                    .withRel("updateRepo"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RepoController.class).deleteRepo(repo.getId()))
                    .withRel("deleteRepo"));
    }

}