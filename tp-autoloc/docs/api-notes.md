# Notes API - Projet AutoLoc (Atelier 5)

## A. Endpoints (Tableau récapitulatif)

| Contrôleur | Verbe et Route | Service appelé | Succès | Codes d'erreur |
| :--- | :--- | :--- | :--- | :--- |
| Agence | GET /api/agences | findAll() | 200 | - |
| Agence | GET /api/agences/{id} | findById(id) | 200 | 404 |
| Agence | POST /api/agences | create(agence) | 201 | 400 |
| Agence | PUT /api/agences/{id} | update(id, agence) | 200 | 400, 404 |
| Agence | DELETE /api/agences/{id} | deleteById(id) | 204 | 404 |
| Client | GET /api/clients | findAll() | 200 | - |
| Client | POST /api/clients | create(client) | 201 | 400 (email/permis vide) |
| Contrat | GET /api/contrats | findAll() | 200 | - |
| Contrat | POST /api/contrats | create(contrat) | 201 | 400 |
| Contrat | DELETE /api/contrats/{id} | deleteById(id) | 204 | 404 |
| Paiement | GET /api/paiements | findAll() | 200 | - |
| Paiement | GET /api/paiements/{id} | findById(id) | 200 | 404 |

*(Note : Ce tableau résume les principaux endpoints. Les autres entités comme Employe, Vehicule, Equipement, Reservation, Maintenance suivent exactement les mêmes conventions REST CRUD).*

## B. Annotations Jackson

*   **Association Contrat ↔ Paiement (Composition) :**
    *   `Contrat.paiements` utilise `@JsonManagedReference("contrat-paiements")` (côté visible).
    *   `Paiement.contrat` utilise `@JsonBackReference("contrat-paiements")` (côté masqué).
    *   *Justification :* Le paiement se crée dans le JSON de son parent (cascade = ALL). L'annotation coupe la boucle infinie de sérialisation tout en permettant de voir les paiements depuis le contrat.
*   **Autres associations (Agrégation) :**
    *   Ex: `Agence.vehicules` utilise `@JsonIgnore`.
    *   *Justification :* L'enfant (véhicule) est créé séparément en désignant son parent par son ID (`{"agence": {"id": 1}}`). Le `@JsonIgnore` exclut complètement la liste de la sérialisation pour éviter les boucles.
*   **Différence entre `@JsonBackReference` et `@JsonIgnore` :** `@JsonBackReference` masque un côté d'une association bidirectionnelle (le côté enfant) mais garde le lien logique en mémoire. `@JsonIgnore` exclut totalement un champ de la lecture et de l'écriture JSON.

## C. Postman et Swagger

*   **Postman** sert à **tester** l'API. Il permet de sauvegarder des requêtes, de créer des scénarios (comme le POST d'un contrat avec cascade de paiements) et d'automatiser des vérifications (scripts de test `pm.test`).
*   **Swagger UI** sert à **documenter** et **explorer** l'API. Il est généré automatiquement à partir du code (contrat synchronisé) et permet à un développeur front-end de comprendre l'API sans lire le code Java.
*   *Exemple concret de l'atelier :* Dans Swagger, nous avons pu voir la section *Schemas* et constater que le champ `contrat` de `Paiement` n'était pas exposé (grâce à `@JsonBackReference`), évitant une erreur `StackOverflow` lors de l'appel `GET /api/paiements`.

