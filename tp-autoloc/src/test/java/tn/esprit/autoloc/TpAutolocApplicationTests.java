package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.StatutVehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.service.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TpAutolocApplicationTests {

    // ══════════════════════════════════════════════════════════
    // INJECTION DES SERVICES
    // ══════════════════════════════════════════════════════════
    @Autowired private IAgenceService agenceService;
    @Autowired private IClientService clientService;
    @Autowired private IVehiculeService vehiculeService;
    @Autowired private IMaintenanceService maintenanceService;
    @Autowired private IEquipementService equipementService;
    @Autowired private IPaiementService paiementService;

    // ══════════════════════════════════════════════════════════
    // TEST DE DÉMARRAGE
    // ══════════════════════════════════════════════════════════
    @Test
    void contextLoads() {
    }

    // ══════════════════════════════════════════════════════════
    // TEST : AgenceService
    // ══════════════════════════════════════════════════════════
    @Test
    public void testAgenceService() {
        System.out.println("\n========== TEST AGENCE SERVICE ==========");

        // 1. Créer une instance
        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("12 Avenue Habib Bourguiba");
        agence.setTelephone("71234567");
        System.out.println("→ Agence créée : " + agence);

        // 2. Enregistrer via le service
        Agence saved = agenceService.create(agence);
        System.out.println("→ Après create() : " + saved);
        System.out.println("   → ID généré : " + saved.getId());
        assertNotNull(saved);
        assertNotNull(saved.getId());

        // 3. findAll()
        List<Agence> toutes = agenceService.findAll();
        System.out.println("→ findAll() : " + toutes.size() + " agence(s)");
        toutes.forEach(a -> System.out.println("     " + a));
        assertFalse(toutes.isEmpty());

        // 4. findById()
        Agence trouvee = agenceService.findById(saved.getId());
        System.out.println("→ findById(" + saved.getId() + ") : " + trouvee);
        assertNotNull(trouvee);
        assertEquals("AutoLoc Tunis", trouvee.getNom());

        // 5. count() → via findAll().size()
        long total = agenceService.findAll().size();
        System.out.println("→ count() : " + total);
        assertTrue(total > 0);

        // 6. update()
        saved.setVille("Sfax");
        Agence modifiee = agenceService.update(saved.getId(), saved);
        System.out.println("→ Après update() : " + modifiee);
        assertEquals("Sfax", modifiee.getVille());

        // 7. deleteById()
        Long id = saved.getId();
        agenceService.deleteById(id);
        System.out.println("→ deleteById(" + id + ") effectué");

        // 8. Vérifier que findById lève ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,
                () -> agenceService.findById(id));
        System.out.println("→ findById(" + id + ") lève bien ResourceNotFoundException ✅");

        System.out.println("========== FIN TEST AGENCE ==========\n");
    }

    // ══════════════════════════════════════════════════════════
    // TEST : ClientService
    // ══════════════════════════════════════════════════════════
    @Test
    public void testClientService() {
        System.out.println("\n========== TEST CLIENT SERVICE ==========");

        // 1. Créer deux clients
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.benali@email.com");
        c1.setTelephone("20123456");
        c1.setNumPermis("P123456");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sarra");
        c2.setEmail("sarra.trabelsi@email.com");
        c2.setTelephone("98765432");
        c2.setNumPermis("P654321");
        c2.setDateInscription(LocalDate.now());

        // 2. Enregistrer via le service
        Client saved1 = clientService.create(c1);
        Client saved2 = clientService.create(c2);
        System.out.println("→ Client 1 sauvé : " + saved1);
        System.out.println("→ Client 2 sauvé : " + saved2);
        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());

        // 3. findAll()
        List<Client> clients = clientService.findAll();
        System.out.println("→ findAll() : " + clients.size() + " client(s)");
        clients.forEach(cl -> System.out.println("     " + cl));
        assertTrue(clients.size() >= 2);

        // 4. findById()
        Client trouve = clientService.findById(saved1.getId());
        System.out.println("→ findById(" + saved1.getId() + ") : " + trouve);
        assertNotNull(trouve);
        assertEquals("Ben Ali", trouve.getNom());

        // 5. count()
        long total = clientService.findAll().size();
        System.out.println("→ count() : " + total);
        assertTrue(total >= 2);

        // 6. deleteById() les deux clients
        Long id1 = saved1.getId();
        Long id2 = saved2.getId();

        clientService.deleteById(id1);
        System.out.println("→ deleteById(" + id1 + ") effectué (client 1)");
        assertThrows(ResourceNotFoundException.class,
                () -> clientService.findById(id1));

        clientService.deleteById(id2);
        System.out.println("→ deleteById(" + id2 + ") effectué (client 2)");
        assertThrows(ResourceNotFoundException.class,
                () -> clientService.findById(id2));

        // 7. count() après suppression
        long totalApres = clientService.findAll().size();
        System.out.println("→ count() après suppressions : " + totalApres);
        assertTrue(totalApres < total);

        System.out.println("========== FIN TEST CLIENT ==========\n");
    }

    // ══════════════════════════════════════════════════════════
    //  TEST : VehiculeService
    // ══════════════════════════════════════════════════════════
    @Test
    public void testVehiculeService() {
        System.out.println("\n========== TEST VEHICULE SERVICE ==========");

        // 1. Créer un véhicule
        Vehicule v = new Vehicule();
        v.setImmatriculation("TN-1234-AB");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("120.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        System.out.println("→ Véhicule créé : " + v);

        // 2. Enregistrer via le service
        Vehicule saved = vehiculeService.create(v);
        System.out.println("→ Après create() : " + saved);
        assertNotNull(saved.getId());

        // 3. findAll()
        List<Vehicule> tous = vehiculeService.findAll();
        System.out.println("→ findAll() : " + tous.size() + " véhicule(s)");
        tous.forEach(ve -> System.out.println("     " + ve));

        // 4. findById()
        Vehicule trouve = vehiculeService.findById(saved.getId());
        System.out.println("→ findById(" + saved.getId() + ") : " + trouve);
        assertNotNull(trouve);
        assertEquals("TN-1234-AB", trouve.getImmatriculation());

        // 5. count()
        long total = vehiculeService.findAll().size();
        System.out.println("→ count() : " + total);
        assertTrue(total > 0);

        // 6. update() — modifier le tarif
        saved.setTarifJournalier(new BigDecimal("150.00"));
        Vehicule modifie = vehiculeService.update(saved.getId(), saved);
        System.out.println("→ Après update tarif : " + modifie.getTarifJournalier());
        assertEquals(new BigDecimal("150.00"), modifie.getTarifJournalier());

        // 7. deleteById()
        Long id = saved.getId();
        vehiculeService.deleteById(id);
        System.out.println("→ deleteById(" + id + ") effectué");
        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.findById(id));

        System.out.println("========== FIN TEST VEHICULE ==========\n");
    }

    // ══════════════════════════════════════════════════════════
    // EXERCICE AUTONOME
    // ══════════════════════════════════════════════════════════
    @Test
    public void testExerciceAutonome() {
        System.out.println("\n========== EXERCICE AUTONOME SERVICES ==========");

        // ─── 1. MAINTENANCE ───────────────────────────────────
        System.out.println("--- Maintenance ---");
        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.now());
        m.setDateFin(LocalDate.now().plusDays(3));
        m.setDescription("Vidange + contrôle freins");

        Maintenance savedM = maintenanceService.create(m);
        System.out.println("→ Maintenance sauvée : " + savedM);
        assertNotNull(savedM.getId());

        Maintenance trouveM = maintenanceService.findById(savedM.getId());
        System.out.println("→ findById(" + savedM.getId() + ") : " + trouveM);
        System.out.println("→ findAll() : " + maintenanceService.findAll().size() + " maintenance(s)");

        // ─── 2. EQUIPEMENT ────────────────────────────────────
        System.out.println("--- Equipement ---");
        Equipement e = new Equipement();
        e.setLibelle("GPS");

        Equipement savedE = equipementService.create(e);
        System.out.println("→ Equipement sauvé : " + savedE);
        assertNotNull(savedE.getId());

        Equipement trouveE = equipementService.findById(savedE.getId());
        System.out.println("→ findById(" + savedE.getId() + ") : " + trouveE);
        System.out.println("→ findAll() : " + equipementService.findAll().size() + " équipement(s)");

        // ─── 3. PAIEMENT (lecture seule) ──────────────────────
        System.out.println("--- Paiement (lecture seule) ---");
        List<Paiement> paiements = paiementService.findAll();
        System.out.println("→ findAll() : " + paiements.size() + " paiement(s)");
        assertNotNull(paiements);

        // ─── SUPPRESSIONS ─────────────────────────────────────
        System.out.println("--- Suppressions ---");
        maintenanceService.deleteById(savedM.getId());
        equipementService.deleteById(savedE.getId());
        assertThrows(ResourceNotFoundException.class,
                () -> maintenanceService.findById(savedM.getId()));
        assertThrows(ResourceNotFoundException.class,
                () -> equipementService.findById(savedE.getId()));

        System.out.println("→ Toutes les entités ont été supprimées avec succès");
    }
}