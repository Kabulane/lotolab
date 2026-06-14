# Plan de développement — LotoLab MVP V1

## Objectif du projet

Construire une application fullstack permettant d’analyser les tirages historiques du Loto français FDJ et de générer des grilles dites “crédibles”, c’est-à-dire statistiquement cohérentes avec les formes observées dans l’historique.

Le projet ne doit jamais être présenté comme un outil de prédiction ou comme un moyen d’augmenter les chances de gagner.  
Son positionnement est celui d’un outil d’analyse statistique, de visualisation et de génération ludique.

## Stack retenue

### Backend

- Java 25
- Spring Boot
- Maven
- Spring Web
- Spring Data MongoDB
- MongoDB
- OpenAPI / Swagger
- Tests unitaires JUnit

### Frontend

- Vue 3
- Vite
- Vuetify
- Librairie de graphiques à définir
- Appels API REST vers le backend

### Dev / livraison

- Git
- GitHub
- Docker Compose pour MongoDB
- README clair
- Commits réguliers par étape fonctionnelle

---

# Principes directeurs

## 1. Codex doit être piloté par petites étapes

Ne pas demander à Codex de générer tout le projet en une seule fois.

Chaque étape doit produire :

- un résultat limité ;
- du code exécutable ;
- des tests quand c’est pertinent ;
- un commit identifiable ;
- une validation manuelle avant de passer à l’étape suivante.

## 2. Le cœur du projet n’est pas le générateur

Le générateur arrive après l’analyse.

Ordre logique :

1. importer les tirages ;
2. calculer les métriques ;
3. exposer les conjectures ;
4. visualiser les distributions historiques ;
5. générer des grilles selon un score.

## 3. Chaque conjecture doit être consultable dans le front

Une conjecture n’est pas seulement une règle interne.

Chaque conjecture doit pouvoir être affichée avec :

- son nom ;
- sa description ;
- sa méthode de calcul ;
- sa représentation graphique ;
- un tableau de distribution historique ;
- des exemples de tirages typiques ;
- des exemples de tirages atypiques ;
- son rôle éventuel dans le score LNI.

## 4. Le projet doit rester crédible

À éviter :

- promesses de prédiction ;
- “optimisation” des chances de gagner ;
- langage marketing trompeur ;
- biais de sélection non expliqué ;
- scores opaques.

À privilégier :

- transparence ;
- analyse historique ;
- visualisation ;
- explication des métriques ;
- génération paramétrable mais honnête.

---

# Modèle conceptuel MVP V1

## Draw

Représente un tirage historique.

Champs attendus :

- id
- drawDate
- numbers : liste triée des 5 numéros principaux
- chanceNumber
- source
- importDate
- metrics

## DrawMetrics

Représente les métriques calculées pour un tirage.

Champs attendus :

- sum
- range
- evenCount
- oddCount
- decadesRepresented
- representedDecades
- lowCount
- highCount
- gaps
- minGap
- maxGap
- averageGap
- consecutivePairs

## Conjecture

Représente une observation statistique consultable.

Champs attendus :

- id
- name
- description
- calculationMethod
- chartType
- buckets
- interpretation
- examplesTypical
- examplesAtypical
- lniWeight

## DistributionBucket

Représente une ligne de distribution historique.

Champs attendus :

- label
- minValue
- maxValue
- count
- percentage

## GeneratedGrid

Représente une grille générée.

Champs attendus :

- numbers
- chanceNumber
- metrics
- lniScore
- profile
- explanations

---

# Conjectures MVP V1

## 1. Somme des numéros

Analyse de la somme des 5 numéros principaux.

Exemple :

```txt
4 + 13 + 22 + 36 + 47 = 122
```

Représentation attendue :

- histogramme par tranches ;
- moyenne historique ;
- médiane ;
- zones fréquentes ;
- zones atypiques.

## 2. Amplitude du tirage

Analyse de l’écart entre le plus grand et le plus petit numéro.

Exemple :

```txt
47 - 4 = 43
```

Représentation attendue :

- histogramme ;
- amplitude moyenne ;
- amplitudes les plus fréquentes ;
- amplitudes rares.

## 3. Répartition pair / impair

Analyse du nombre de numéros pairs et impairs.

Exemples :

```txt
2 pairs / 3 impairs
3 pairs / 2 impairs
```

Représentation attendue :

- bar chart ;
- pourcentage par combinaison ;
- mise en évidence des profils les plus fréquents.

## 4. Nombre de dizaines représentées

Découpage retenu :

```txt
0-9
10-19
20-29
30-39
40-49
```

Représentation attendue :

- nombre de dizaines présentes dans chaque tirage ;
- distribution historique ;
- exemples de tirages couvrant 2, 3, 4 ou 5 dizaines.

## 5. Écarts consécutifs

Analyse des écarts entre les numéros triés.

Exemple :

```txt
Tirage : 4, 13, 22, 36, 47
Écarts : 9, 9, 14, 11
```

Représentation attendue :

- distribution des écarts ;
- minGap ;
- maxGap ;
- averageGap ;
- détection des tirages compacts ou très dispersés.

## 6. Présence de numéros consécutifs

Analyse du nombre de paires consécutives.

Exemples :

```txt
12, 13 => 1 paire consécutive
12, 13, 14 => 2 paires consécutives
```

Représentation attendue :

- bar chart ;
- fréquence des tirages sans consécutif ;
- fréquence des tirages avec 1, 2 ou plus paires consécutives.

## 7. Répartition bas / haut

Découpage retenu :

```txt
Bas : 1 à 24
Haut : 25 à 49
```

Représentation attendue :

- distribution 0/5, 1/4, 2/3, 3/2, 4/1, 5/0 ;
- exemples associés.

---

# Architecture backend cible

## Packages proposés

```txt
com.lotolab
├── LotoLabApplication
├── draw
│   ├── Draw
│   ├── DrawRepository
│   ├── DrawController
│   ├── DrawService
│   └── DrawMapper
├── metrics
│   ├── DrawMetrics
│   ├── DrawMetricsService
│   └── DrawMetricsCalculator
├── conjecture
│   ├── Conjecture
│   ├── ConjectureController
│   ├── ConjectureService
│   ├── ConjectureDefinition
│   └── DistributionBucket
├── generator
│   ├── GeneratedGrid
│   ├── GeneratorController
│   ├── GeneratorService
│   ├── LniScoreService
│   └── GenerationRequest
├── importfdj
│   ├── FdjImportController
│   ├── FdjImportService
│   └── FdjCsvParser
└── common
    ├── ApiError
    └── ValidationUtils
```

## Endpoints MVP V1

```txt
GET /api/health
GET /api/draws
POST /api/import/fdj
GET /api/conjectures
GET /api/conjectures/{id}
POST /api/generator/grids
```

## Endpoints optionnels V1+

```txt
GET /api/stats/summary
GET /api/draws/{id}/metrics
POST /api/generator/score
```

---

# Architecture frontend cible

## Structure proposée

```txt
src
├── main.js
├── App.vue
├── router
│   └── index.js
├── api
│   ├── drawsApi.js
│   ├── conjecturesApi.js
│   └── generatorApi.js
├── views
│   ├── DashboardView.vue
│   ├── ConjecturesView.vue
│   ├── ConjectureDetailView.vue
│   ├── GeneratorView.vue
│   └── DrawsView.vue
├── components
│   ├── AppLayout.vue
│   ├── StatCard.vue
│   ├── ConjectureCard.vue
│   ├── DistributionChart.vue
│   ├── DistributionTable.vue
│   ├── GeneratedGridCard.vue
│   └── LniScoreChip.vue
└── composables
    ├── useConjectures.js
    ├── useGenerator.js
    └── useDraws.js
```

## Pages MVP V1

### Dashboard

Objectif :

- donner une vision globale des données analysées.

Contenu :

- nombre de tirages importés ;
- date du premier tirage ;
- date du dernier tirage ;
- somme moyenne ;
- amplitude moyenne ;
- accès rapide aux conjectures.

### Conjectures

Objectif :

- lister les conjectures disponibles.

Contenu :

- cartes de conjectures ;
- description courte ;
- type de graphique ;
- bouton vers le détail.

### Détail conjecture

Objectif :

- rendre chaque conjecture compréhensible.

Contenu :

- titre ;
- description ;
- méthode de calcul ;
- graphique ;
- tableau des buckets ;
- exemples typiques ;
- exemples atypiques ;
- explication du rôle dans le LNI.

### Générateur

Objectif :

- générer des grilles selon des paramètres simples.

Contenu :

- nombre de grilles ;
- LNI minimum ;
- LNI maximum ;
- mode de génération ;
- option exclure les tirages déjà sortis ;
- résultats avec explication.

### Historique des tirages

Objectif :

- consulter les tirages importés.

Contenu :

- tableau ;
- date ;
- numéros ;
- chance ;
- somme ;
- amplitude ;
- parité ;
- lien vers détail éventuel.

---

# Étapes de développement avec Codex

## Étape 0 — Initialisation du repo GitHub

Objectif :

- créer ou préparer le repository GitHub ;
- avoir une base propre pour le projet.

Actions :

- créer le repo GitHub ;
- cloner le repo localement ;
- ajouter un README initial ;
- ajouter un `.gitignore` adapté Java + Node + IDE ;
- vérifier que le premier commit est bien poussé.

Livrable attendu :

```txt
README.md
.gitignore
```

Critères de validation :

- le repo est accessible sur GitHub ;
- le projet local est lié au remote ;
- un premier commit existe.

Commit suggéré :

```txt
chore: initialize repository
```

---

## Étape 1 — Création du backend Spring Boot

Objectif :

- créer l’application backend Java 25 ;
- vérifier qu’elle démarre correctement.

Actions :

- générer le projet Spring Boot ;
- configurer Maven ;
- ajouter un endpoint health ;
- ajouter Swagger/OpenAPI ;
- ajouter une configuration de base ;
- créer un test de démarrage.

Livrable attendu :

```txt
backend/
├── pom.xml
├── src/main/java/...
├── src/test/java/...
└── README.md éventuel
```

Critères de validation :

- `mvn test` passe ;
- l’application démarre ;
- `/api/health` répond ;
- Swagger est accessible.

Commit suggéré :

```txt
feat: initialize Spring Boot backend
```

---

## Étape 2 — Ajout de MongoDB avec Docker Compose

Objectif :

- disposer d’une base MongoDB locale reproductible.

Actions :

- ajouter un `docker-compose.yml` ;
- configurer MongoDB ;
- connecter Spring Boot à MongoDB ;
- créer une configuration locale ;
- vérifier la connexion au démarrage.

Livrable attendu :

```txt
docker-compose.yml
backend/src/main/resources/application.yml
```

Critères de validation :

- MongoDB démarre avec Docker Compose ;
- le backend se connecte à MongoDB ;
- les tests ne dépendent pas d’une base locale non maîtrisée.

Commit suggéré :

```txt
chore: add MongoDB local environment
```

---

## Étape 3 — Modèle Draw

Objectif :

- créer le modèle de tirage historique.

Actions :

- créer l’entité/document `Draw` ;
- créer le repository MongoDB ;
- créer les DTO nécessaires ;
- exposer un endpoint de lecture simple ;
- ajouter quelques données de test si nécessaire.

Livrable attendu :

```txt
Draw
DrawRepository
DrawService
DrawController
DrawDto
```

Critères de validation :

- possibilité de lister les tirages ;
- structure des numéros cohérente ;
- les 5 numéros principaux sont stockés triés ;
- le numéro Chance est stocké séparément.

Commit suggéré :

```txt
feat: add draw document and read API
```

---

## Étape 4 — Calcul des métriques

Objectif :

- calculer les métriques fondamentales d’un tirage.

Actions :

- créer `DrawMetrics` ;
- créer `DrawMetricsCalculator` ;
- calculer les métriques MVP ;
- ajouter des tests unitaires précis.

Métriques attendues :

- somme ;
- amplitude ;
- nombre de pairs ;
- nombre d’impairs ;
- dizaines représentées ;
- nombre de dizaines représentées ;
- répartition bas / haut ;
- écarts consécutifs ;
- minGap ;
- maxGap ;
- averageGap ;
- paires consécutives.

Critères de validation :

- tests unitaires sur plusieurs tirages ;
- cas limites testés ;
- calculs déterministes ;
- pas de dépendance à la base dans le calculateur pur.

Commit suggéré :

```txt
feat: compute draw metrics
```

---

## Étape 5 — Import historique FDJ

Objectif :

- importer les tirages historiques depuis un fichier source.

Actions :

- créer un service d’import ;
- parser le fichier FDJ ;
- extraire uniquement les données nécessaires ;
- normaliser les numéros ;
- calculer les métriques à l’import ;
- stocker les tirages en base ;
- gérer les doublons.

Critères de validation :

- import possible depuis un fichier local ;
- doublons évités ;
- erreurs de parsing remontées proprement ;
- métriques calculées automatiquement.

Commit suggéré :

```txt
feat: import FDJ historical draws
```

---

## Étape 6 — Définition des conjectures

Objectif :

- transformer les observations statistiques en objets métier consultables.

Actions :

- créer un modèle `Conjecture`;
- créer des définitions statiques pour les conjectures MVP ;
- créer un service capable de calculer les distributions historiques ;
- créer les endpoints `/api/conjectures` et `/api/conjectures/{id}`.

Conjectures V1 :

- somme des numéros ;
- amplitude ;
- pair / impair ;
- dizaines représentées ;
- écarts consécutifs ;
- numéros consécutifs ;
- répartition bas / haut.

Critères de validation :

- chaque conjecture expose description et méthode de calcul ;
- chaque conjecture expose une distribution historique ;
- chaque distribution contient count et percentage ;
- les conjectures sont consommables facilement par le front.

Commit suggéré :

```txt
feat: expose conjecture analysis API
```

---

## Étape 7 — Calcul du LNI

Objectif :

- créer un score simple de naturalité statistique.

Actions :

- définir une première version du score ;
- comparer les métriques d’une grille aux distributions historiques ;
- retourner un score de 0 à 100 ;
- fournir une explication par métrique.

Principe V1 :

- plus une métrique tombe dans une zone historiquement fréquente, plus elle contribue positivement ;
- les pondérations restent simples ;
- le score est explicable.

Critères de validation :

- le score est déterministe ;
- le score renvoie des explications ;
- les cas très atypiques ont un score faible ;
- les cas équilibrés ont généralement un score plus élevé.

Commit suggéré :

```txt
feat: add LNI scoring service
```

---

## Étape 8 — Générateur de grilles

Objectif :

- générer des grilles selon une plage de LNI.

Actions :

- créer `GenerationRequest`;
- créer `GeneratedGrid`;
- générer des combinaisons aléatoires ;
- calculer leurs métriques ;
- calculer leur LNI ;
- conserver uniquement les grilles dans la plage demandée ;
- limiter le nombre d’essais pour éviter les boucles infinies.

Paramètres V1 :

- nombre de grilles ;
- LNI minimum ;
- LNI maximum ;
- inclure ou non le numéro Chance ;
- exclure ou non les grilles déjà sorties ;
- mode de génération.

Modes possibles :

```txt
NATURAL
ATYPICAL_PLAUSIBLE
CONTROLLED_CHAOS
```

Critères de validation :

- génération stable ;
- pas de boucle infinie ;
- réponse explicable ;
- endpoint utilisable par le front.

Commit suggéré :

```txt
feat: generate grids from LNI constraints
```

---

## Étape 9 — Création du frontend Vue 3 + Vuetify

Objectif :

- initialiser le frontend.

Actions :

- créer le projet Vue 3 ;
- installer Vuetify ;
- configurer le routing ;
- créer le layout principal ;
- créer une navigation simple.

Livrable attendu :

```txt
frontend/
├── package.json
├── src/
└── vite.config.js
```

Critères de validation :

- `npm install` fonctionne ;
- `npm run dev` fonctionne ;
- l’application affiche un layout ;
- navigation entre les pages.

Commit suggéré :

```txt
feat: initialize Vue Vuetify frontend
```

---

## Étape 10 — Connexion frontend / backend

Objectif :

- consommer l’API depuis Vue.

Actions :

- créer les fichiers API ;
- gérer l’URL backend ;
- afficher les données du dashboard ;
- gérer les erreurs simples ;
- ajouter un état loading.

Critères de validation :

- le front appelle réellement le backend ;
- les erreurs API sont visibles ;
- les données ne sont pas mockées sauf explicitement.

Commit suggéré :

```txt
feat: connect frontend to backend API
```

---

## Étape 11 — Page Conjectures

Objectif :

- afficher la liste des conjectures.

Actions :

- créer `ConjecturesView`;
- créer `ConjectureCard`;
- appeler `/api/conjectures`;
- afficher les informations principales ;
- ajouter la navigation vers le détail.

Critères de validation :

- toutes les conjectures MVP sont listées ;
- chaque carte est compréhensible ;
- chaque carte mène à une page détail.

Commit suggéré :

```txt
feat: display conjecture list
```

---

## Étape 12 — Page détail d’une conjecture

Objectif :

- rendre une conjecture consultable en détail.

Actions :

- créer `ConjectureDetailView`;
- afficher description et méthode de calcul ;
- afficher un graphique ;
- afficher un tableau de distribution ;
- afficher exemples typiques et atypiques ;
- afficher le rôle dans le LNI.

Critères de validation :

- chaque conjecture est compréhensible sans lire le code ;
- la représentation historique est claire ;
- le tableau correspond au graphique ;
- les données viennent du backend.

Commit suggéré :

```txt
feat: add conjecture detail view
```

---

## Étape 13 — Page Générateur

Objectif :

- permettre la génération paramétrable.

Actions :

- créer `GeneratorView`;
- ajouter formulaire de génération ;
- appeler `/api/generator/grids`;
- afficher les grilles générées ;
- afficher le LNI ;
- afficher les explications.

Critères de validation :

- l’utilisateur peut choisir une plage de LNI ;
- les grilles générées respectent la demande ;
- chaque grille a une explication ;
- le rendu est propre.

Commit suggéré :

```txt
feat: add configurable grid generator
```

---

## Étape 14 — Page Historique

Objectif :

- consulter les tirages importés.

Actions :

- créer `DrawsView`;
- afficher un tableau paginé ;
- afficher les métriques principales ;
- ajouter filtres simples si pertinent.

Critères de validation :

- les tirages sont consultables ;
- les données sont lisibles ;
- les métriques clés sont visibles.

Commit suggéré :

```txt
feat: add historical draws view
```

---

## Étape 15 — README et documentation

Objectif :

- rendre le projet compréhensible et lançable.

Actions :

- documenter le positionnement du projet ;
- documenter l’installation ;
- documenter le lancement backend ;
- documenter le lancement frontend ;
- documenter l’import des données ;
- documenter les endpoints ;
- documenter les limites statistiques.

Sections README recommandées :

```txt
- Présentation
- Avertissement
- Stack technique
- Fonctionnalités
- Architecture
- Installation
- Lancement local
- Import des données FDJ
- API
- Roadmap
- Limites
```

Commit suggéré :

```txt
docs: add project documentation
```

---

## Étape 16 — Nettoyage MVP V1

Objectif :

- rendre le projet propre avant éventuel déploiement.

Actions :

- supprimer les mocks inutiles ;
- vérifier les logs ;
- améliorer les messages d’erreur ;
- vérifier les noms de composants ;
- vérifier les tests ;
- vérifier le build front ;
- vérifier le build back ;
- vérifier Docker Compose ;
- relire le README.

Critères de validation :

- backend démarre ;
- frontend démarre ;
- tests backend OK ;
- build frontend OK ;
- import fonctionnel ;
- génération fonctionnelle.

Commit suggéré :

```txt
chore: polish MVP v1
```

---

# Stratégie de commits

## Rythme conseillé

Un commit par étape fonctionnelle validée.

Ne pas attendre la fin du projet pour commit.

## Format simple

```txt
feat: add draw metrics calculation
fix: handle duplicate imported draws
docs: update README with import instructions
chore: add docker compose for MongoDB
test: add metrics calculator tests
```

## Règle personnelle

Avant chaque commit :

```txt
1. relire les fichiers modifiés
2. lancer les tests disponibles
3. vérifier que l’application démarre si l’étape le nécessite
4. vérifier que le commit correspond à une seule intention
```

---

# Stratégie GitHub

## Repository

Créer un repository dédié.

Nom possible :

```txt
lotolab
loto-lab
fdj-loto-lab
loto-naturalness-lab
```

## Branches

Pour un projet solo MVP, rester simple :

```txt
main
```

Option plus propre :

```txt
main
develop
feature/...
```

Pour avancer vite, `main` avec commits propres suffit.

## Issues GitHub

Créer des issues simples pour suivre le MVP :

```txt
1. Initialize backend
2. Add MongoDB
3. Add Draw model
4. Compute metrics
5. Import FDJ historical data
6. Expose conjectures
7. Add LNI score
8. Add generator
9. Initialize frontend
10. Add conjecture detail page
11. Add generator page
12. Add README
```

---

# Définition de fini du MVP V1

Le MVP V1 est terminé quand :

- le backend démarre localement ;
- MongoDB démarre via Docker Compose ;
- un historique FDJ peut être importé ;
- les métriques sont calculées ;
- les conjectures MVP sont exposées par API ;
- le front affiche les conjectures ;
- chaque conjecture a une page détail ;
- le générateur produit des grilles selon une plage de LNI ;
- le projet est documenté ;
- le repo GitHub contient tout le code ;
- les principales étapes sont commit correctement.

---

# Roadmap après MVP V1

## V1.1

- meilleure gestion des fichiers FDJ ;
- import automatique ;
- pagination avancée ;
- filtres historiques ;
- comparaison historique vs théorie combinatoire.

## V1.2

- pondération personnalisable du LNI ;
- profils de génération ;
- export CSV ;
- partage d’une grille ;
- amélioration des graphiques.

## V2

- déploiement public ;
- SEO ;
- pages explicatives par conjecture ;
- mode “analyse ta grille” ;
- historique des grilles générées ;
- monitoring simple.

## V3

- moteur statistique plus avancé ;
- simulation de milliers de grilles ;
- comparaison entre périodes historiques ;
- analyse des changements de règles du Loto ;
- dashboard public enrichi.

---

# Notes importantes

Le projet doit rester honnête :

- il analyse le passé ;
- il génère des grilles cohérentes avec des patterns historiques ;
- il ne prédit pas l’avenir ;
- il ne modifie pas les probabilités réelles de gain.

Formulation produit recommandée :

```txt
LotoLab génère des grilles statistiquement naturelles à partir des formes observées dans l’historique des tirages.
```

Formulation à éviter :

```txt
LotoLab augmente vos chances de gagner.
```
