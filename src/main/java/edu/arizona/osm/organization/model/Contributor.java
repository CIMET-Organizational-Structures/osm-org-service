package edu.arizona.osm.organization.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "contributor")
public class Contributor extends RepresentationModel<Contributor> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String name;

    @ManyToMany(mappedBy = "contributors", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Repo> repos;

}
