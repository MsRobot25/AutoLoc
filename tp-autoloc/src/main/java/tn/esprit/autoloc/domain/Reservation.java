package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.autoloc.domain.enums.StatutReservation;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

@ManyToOne(fetch = FetchType.LAZY)
    private Vehicule vehicule;
@ManyToOne(fetch = FetchType.LAZY)
    private Client client;
@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Contrat contrat;


}