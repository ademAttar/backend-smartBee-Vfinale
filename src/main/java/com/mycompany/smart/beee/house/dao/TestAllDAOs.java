package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.dao.*;
import com.mycompany.smart.beee.house.entity.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TestAllDAOs {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Initialisation de tous les DAO
            UserDAO userDAO = new UserDAO(connection);
            FermeDAO fermeDAO = new FermeDAO(connection, userDAO, null);
            SiteApicultureDAO siteDAO = new SiteApicultureDAO(connection, fermeDAO);
            RucheDAO rucheDAO = new RucheDAO(connection, userDAO, siteDAO);
            PlanningVisiteDAO planningDAO = new PlanningVisiteDAO(connection,rucheDAO,userDAO);
            RapportVisiteDAO rapportDAO = new RapportVisiteDAO(connection, planningDAO, userDAO, rucheDAO);
            ComposantRucheDAO composantDAO = new ComposantRucheDAO(connection, rucheDAO);
            CadreDAO cadreDAO = new CadreDAO(connection, composantDAO);
            MesureDAO mesureDAO = new MesureDAO(connection, rucheDAO);

            System.out.println("=== TEST COMPLET DES DAO ===");

            // 1. Test UserDAO
            testUserDAO(userDAO);

            // 2. Test FermeDAO
            Fermier fermier = (Fermier) userDAO.findById(13L); // On réutilise le fermier créé dans testUserDAO
            testFermeDAO(fermeDAO, fermier);

            // 3. Test SiteApicultureDAO
            Ferme ferme = fermeDAO.findById(2L); // On réutilise la ferme créée
            testSiteApicultureDAO(siteDAO, ferme);

            // 4. Test RucheDAO
            SiteApiculture site = siteDAO.findById(1L); // On réutilise le site créé
            Agent agent = (Agent) userDAO.findById(14L); // On réutilise l'agent créé
            testRucheDAO(rucheDAO, site, agent);

            // 5. Test PlanningVisiteDAO
            Ruche ruche = rucheDAO.findById(1L); // On réutilise la ruche créée
            testPlanningVisiteDAO(planningDAO, ruche, agent);

            // 6. Test RapportVisiteDAO
            PlanningVisite planning = planningDAO.findById(1L); // On réutilise le planning créé
            testRapportVisiteDAO(rapportDAO, planning, ruche, agent);

            // 7. Test ComposantRucheDAO
            testComposantRucheDAO(composantDAO, ruche);

            // 8. Test CadreDAO
            ComposantRuche composant = composantDAO.findById(1L); // On réutilise le composant créé
            testCadreDAO(cadreDAO, composant);

            // 9. Test MesureDAO
            testMesureDAO(mesureDAO, ruche);

            System.out.println("=== TESTS TERMINÉS AVEC SUCCÈS ===");

        } catch (SQLException e) {
            System.err.println("Erreur lors des tests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testUserDAO(UserDAO userDAO) throws SQLException {
        System.out.println("\n--- Test UserDAO ---");

        // Création d'un fermier
        Fermier fermier = new Fermier(null, "Dupont", "Jean", "jean.dupont@email.com", "password", "0123456789");
        userDAO.create(fermier);
        System.out.println("Fermier créé avec ID: " + fermier.getId());

        if (fermier.getId() == null) {
            throw new IllegalStateException("L'ID du fermier n'a pas été généré!");
        }

        // Création d'un agent
        Agent agent = new Agent(null, "Martin", "Pierre", "pierre.martin@email.com", "password");
        userDAO.create(agent);
        System.out.println("Agent créé avec ID: " + agent.getId());
    }

    private static void testFermeDAO(FermeDAO fermeDAO, Fermier fermier) throws SQLException {
        System.out.println("\n--- Test FermeDAO ---");

        // Vérification que le fermier existe et a un ID
        if (fermier == null || fermier.getId() == null) {
            throw new IllegalStateException("Le fermier doit être créé et persistant avant de créer une ferme");
        }

        // Création de la ferme avec le fermier
        Ferme ferme = new Ferme(null, "Ferme des Abeilles", 5.5, fermier);
        fermeDAO.create(ferme);
        System.out.println("Ferme créée avec ID: " + ferme.getId());
    }

    private static void testSiteApicultureDAO(SiteApicultureDAO siteDAO, Ferme ferme) throws SQLException {
        System.out.println("\n--- Test SiteApicultureDAO ---");

        // Création
        SiteApiculture site = new SiteApiculture(null, 45.123, 4.567, 300, new Date(), null, ferme);
        siteDAO.create(site);
        System.out.println("Site créé: " + site);

        // Recherche
        SiteApiculture siteTrouve = siteDAO.findById(site.getId());
        System.out.println("Site trouvé: " + siteTrouve);

        // Recherche par ferme
        List<SiteApiculture> sites = siteDAO.findByFerme(ferme.getId());
        System.out.println("Sites de la ferme: " + sites.size());
    }

    private static void testRucheDAO(RucheDAO rucheDAO, SiteApiculture site, Agent agent) throws SQLException {
        System.out.println("\n--- Test RucheDAO ---");

        // Création
        Ruche ruche = new Ruche(null, "RUCHE-001", new Date(), StatutRuche.ACTIVE, agent, null, null,null);
        rucheDAO.create(ruche);
        System.out.println("Ruche créée: " + ruche);

        // Recherche
        Ruche rucheTrouvee = rucheDAO.findById(ruche.getId());
        System.out.println("Ruche trouvée: " + rucheTrouvee);

        // Recherche par site
        List<Ruche> ruches = rucheDAO.findBySite(site.getId());
        System.out.println("Ruches du site: " + ruches.size());

        // Recherche par agent
        List<Ruche> ruchesAgent = rucheDAO.findByAgent(agent.getId());
        System.out.println("Ruches de l'agent: " + ruchesAgent.size());
    }

    private static void testPlanningVisiteDAO(PlanningVisiteDAO planningDAO, Ruche ruche, Agent agent) throws SQLException {
        System.out.println("\n--- Test PlanningVisiteDAO ---");

        // Création
        PlanningVisite planning = new PlanningVisite(null, new Date(System.currentTimeMillis() + 86400000), "Inspection sanitaire");
        planning.setRuche(ruche);
        planning.setAgent(agent);
        planningDAO.create(planning);
        System.out.println("Planning créé: " + planning);

        // Recherche
        PlanningVisite planningTrouve = planningDAO.findById(planning.getId());
        System.out.println("Planning trouvé: " + planningTrouve);

        // Recherche par ruche
        List<PlanningVisite> plannings = planningDAO.findByRuche(ruche.getId());
        System.out.println("Plannings pour la ruche: " + plannings.size());

        // Recherche par agent
        List<PlanningVisite> planningsAgent = planningDAO.findByAgent(agent.getId());
        System.out.println("Plannings de l'agent: " + planningsAgent.size());
    }

    private static void testRapportVisiteDAO(RapportVisiteDAO rapportDAO, PlanningVisite planning, Ruche ruche, Agent agent) throws SQLException {
        System.out.println("\n--- Test RapportVisiteDAO ---");

        // Création
        RapportVisite rapport = new RapportVisite();
        rapport.setDate(new Date());
        rapport.setContenu("Tout semble normal");
        rapport.setDuree(30);
        rapport.setRaison("Inspection routine");
        rapport.setConstatations("Colonie en bonne santé");
        rapport.setActionsPrevues("Vérifier dans 2 semaines");
        rapport.setActionsEffectuees("Nettoyage effectué");
        rapport.setRecommandations("Ajouter un cadre supplémentaire");
        rapport.setEvaluationEffectif(2);
        rapport.setEvaluationSante(2);
        rapport.setEvaluationProductivite(3);
        rapport.setPlanningVisite(planning);
        rapport.setAgent(agent);
        rapport.setRuche(ruche);

        rapportDAO.create(rapport);
        System.out.println("Rapport créé: " + rapport);

        // Recherche
        RapportVisite rapportTrouve = rapportDAO.findById(rapport.getId());
        System.out.println("Rapport trouvé: " + rapportTrouve);

        // Recherche par ruche
        List<RapportVisite> rapports = rapportDAO.findByRuche(ruche.getId());
        System.out.println("Rapports pour la ruche: " + rapports.size());

        // Recherche par agent
        List<RapportVisite> rapportsAgent = rapportDAO.findByAgent(agent.getId());
        System.out.println("Rapports de l'agent: " + rapportsAgent.size());
    }

    private static void testComposantRucheDAO(ComposantRucheDAO composantDAO, Ruche ruche) throws SQLException {
        System.out.println("\n--- Test ComposantRucheDAO ---");

        // Création
        ComposantRuche composant = new ComposantRuche(null, TypeComposant.CORPS_DE_RUCHE, 0);
        composant.setRuche(ruche);
        composantDAO.create(composant);
        System.out.println("Composant créé: " + composant);

        // Recherche
        ComposantRuche composantTrouve = composantDAO.findById(composant.getId());
        System.out.println("Composant trouvé: " + composantTrouve);

        // Recherche par ruche
        List<ComposantRuche> composants = composantDAO.findByRuche(ruche.getId());
        System.out.println("Composants de la ruche: " + composants.size());
    }

    private static void testCadreDAO(CadreDAO cadreDAO, ComposantRuche composant) throws SQLException {
        System.out.println("\n--- Test CadreDAO ---");

        // Création
        Cadre cadre = new Cadre(null, 1, new Date(), TypeCadre.CADRE_A_MIEL);
        cadre.setComposant(composant);
        cadreDAO.create(cadre);
        System.out.println("Cadre créé: " + cadre);

        // Recherche
        List<Cadre> cadres = cadreDAO.findByComposant(composant.getId());
        System.out.println("Cadres trouvés: " + cadres.size());
    }

    private static void testMesureDAO(MesureDAO mesureDAO, Ruche ruche) throws SQLException {
        System.out.println("\n--- Test MesureDAO ---");

        // Création
        Mesure mesure = new Mesure(null, TypeMesure.POIDS, 12.5, "kg", new Date());
        mesure.setRuche(ruche);
        mesureDAO.create(mesure);
        System.out.println("Mesure créée: " + mesure);

        // Recherche
        List<Mesure> mesures = mesureDAO.findByRuche(ruche.getId());
        System.out.println("Mesures pour la ruche: " + mesures.size());

        // Recherche spécifique
        List<Mesure> poids = mesureDAO.findPoidsByRuche(ruche.getId());
        System.out.println("Mesures de poids: " + poids.size());
    }
}
