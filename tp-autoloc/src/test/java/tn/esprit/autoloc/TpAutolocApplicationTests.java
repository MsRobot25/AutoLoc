package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.StatutVehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class TpAutolocApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private IAgenceRepository agenceRepository;
    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Test
    void testAgenceRepository() {
        System.out.println("=====TEST AGENCE++++++++++");
        Agence agence = new Agence();
        agence.setNom("agence1");
        agence.setVille("tunis");
        Agence agenceSaved=agenceRepository.save(agence);
        System.out.println("agence cree;"+ agenceSaved);
        List<Agence> agences=agenceRepository.findAll();
        System.out.println("liste des agenvces;"+agences);
        System.out.println("agence par id; " + agenceRepository.findById(agenceSaved.getId()));
        System.out.println("existe ? " + agenceRepository.existsById(agenceSaved.getId()));
        System.out.println("nombre d'agences; " + agenceRepository.count());
        agenceRepository.deleteById(agenceSaved.getId());
        System.out.println("agence supprimee");
        System.out.println("existe encore ? " + agenceRepository.existsById(agenceSaved.getId()));



    }
    @Test
    void testClientRepository() {
        System.out.println("===== TEST CLIENT ++++++++++");

        // 1. Créer deux clients
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed@esprit.tn");
        c1.setTelephone("20111111");
        c1.setNumPermis("TN-123456");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sarra");
        c2.setEmail("sarra@esprit.tn");
        c2.setTelephone("20222222");
        c2.setNumPermis("TN-654321");
        c2.setDateInscription(LocalDate.now());

        Client saved1 = clientRepository.save(c1);
        Client saved2 = clientRepository.save(c2);
        System.out.println("client 1 cree; " + saved1);
        System.out.println("client 2 cree; " + saved2);

        List<Client> clients = clientRepository.findAll();
        System.out.println("liste des clients; " + clients);

        System.out.println("client par id; " + clientRepository.findById(saved1.getId()));

        System.out.println("existe id " + saved1.getId() + " ? "
                + clientRepository.existsById(saved1.getId()));
        System.out.println("existe id 99999 ? "
                + clientRepository.existsById(99999L));

        System.out.println("nombre de clients; " + clientRepository.count());
    }
    @Test
    void testVehiculeRepository() {
        System.out.println("===== TEST VEHICULE ++++++++++");


        Agence agence = new Agence();
        agence.setNom("Agence Centrale");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setVille("Tunis");
        agence.setTelephone("71000000");
        Agence agenceSaved = agenceRepository.save(agence);
        System.out.println("agence cree; " + agenceSaved);


        Vehicule v = new Vehicule();
        v.setImmatriculation("TN-123-TN");
        v.setMarque("Peugeot");
        v.setModele("208");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("80.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        v.setAgence(agenceSaved);   // rattacher le véhicule à l'agence

        Vehicule saved = vehiculeRepository.save(v);
        System.out.println("vehicule cree; " + saved);


      //List<Vehicule> vehicules = vehiculeRepository.findAll();
       // System.out.println("liste des vehicules; " + vehicules);


        System.out.println("nombre de vehicules; " + vehiculeRepository.count());


        saved.setTarifJournalier(new BigDecimal("95.00"));
        saved.setStatut(StatutVehicule.LOUE);
        Vehicule updated = vehiculeRepository.save(saved);
        System.out.println("vehicule modifie; " + updated);

        vehiculeRepository.deleteById(updated.getId());
        System.out.println("vehicule supprime");
    }
}