package tn.esprit.autoloc.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Agence {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nom;

        @Column(nullable = false)
        private String ville;

        private String adresse;
        private String telephone;
        @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
        @JsonIgnore
        private List<Vehicule> vehicules = new ArrayList<>();

        @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
        @JsonIgnore
        private List<Employe> employees = new ArrayList<>();

}
