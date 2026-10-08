package tn.esprit.autoloc.domain;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrat")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;

@OneToMany(mappedBy = "contrat", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
private List<Paiement> paiements=new ArrayList<>() ;

@OneToOne(mappedBy = "contrat", fetch = FetchType.LAZY)
    private Reservation reservation;


}