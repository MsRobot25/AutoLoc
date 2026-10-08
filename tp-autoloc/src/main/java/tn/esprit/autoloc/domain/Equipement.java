package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
@ManyToMany(mappedBy = "equipements",fetch = FetchType.LAZY)
    private List<Vehicule> vehicules=new ArrayList<>();
}
