# Nouvelle vision du projet

## Objectif du projet

Construire une application fullstack permettant d'étudier les formes statistiques des tirages du Loto français FDJ.

L'application poursuit trois objectifs :

1. Calculer les distributions théoriques attendues d'un tirage aléatoire.
2. Mesurer les distributions réellement observées dans l'historique FDJ.
3. Comparer théorie et réalité afin d'identifier les écarts, les convergences et les éventuelles anomalies statistiques.

À partir de cette analyse, l'application pourra également :

* analyser une grille proposée par un utilisateur ;
* calculer un indice de naturalité (LNI) ;
* générer des grilles respectant certaines contraintes statistiques.

Le projet ne cherche pas à prédire les prochains tirages ni à augmenter les probabilités de gain.

Son objectif est l'analyse statistique et la visualisation des structures de tirage.

---

# Principe fondamental

Chaque conjecture doit être étudiée sous trois angles :

## Théorie

Que prédit la combinatoire ?

Exemples :

* distribution pair / impair ;
* distribution des dizaines représentées ;
* distribution des amplitudes ;
* distribution des sommes.

## Historique

Que s'est-il réellement produit dans les tirages FDJ ?

Exemples :

* fréquence observée ;
* évolution temporelle ;
* comparaison par période.

## Écart

Comparer :

Historique - Théorie

Exemples :

* fréquence théorique : 46.8 %
* fréquence observée : 47.2 %
* écart : +0.4 %

L'objectif est de distinguer :

* les comportements attendus ;
* les fluctuations statistiques ;
* les éventuels écarts significatifs.

---

# Architecture métier

## Conjecture

Une conjecture ne représente plus uniquement une métrique historique.

Elle représente désormais une observation analysée selon trois dimensions :

* théorie ;
* historique ;
* comparaison.

Chaque conjecture expose :

* définition ;
* méthode de calcul ;
* distribution théorique ;
* distribution historique ;
* écart ;
* interprétation ;
* exemples typiques ;
* exemples atypiques.

---

# Pages front

## Théorie

Présentation des distributions calculées mathématiquement.

Objectif :

Comprendre à quoi ressemble un tirage parfaitement aléatoire.

## Historique

Présentation des distributions observées.

Objectif :

Comprendre à quoi ressemblent réellement les tirages FDJ.

## Comparaison

Présentation des écarts.

Objectif :

Comparer théorie et réalité.

Pour chaque conjecture :

* graphique théorie ;
* graphique historique ;
* différence ;
* interprétation.

---

# Ordre de développement recommandé

1. Import historique FDJ
2. Calcul des métriques
3. Calcul des distributions historiques
4. Implémentation des distributions théoriques
5. Comparaison théorie / historique
6. Création des pages d'analyse
7. Création du LNI
8. Générateur de grilles
9. Analyse de grilles utilisateur
10. Complétion de grilles à partir de numéros imposés

Le LNI doit être construit à partir de la comparaison entre théorie et historique, et non uniquement à partir des données historiques.
