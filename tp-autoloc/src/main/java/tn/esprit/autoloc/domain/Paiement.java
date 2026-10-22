package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.autoloc.domain.enums.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiement")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal montant;

    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;
    @JsonBackReference("contrat-paiements")
    @ManyToOne(fetch = FetchType.LAZY)
    private Contrat contrat;

}