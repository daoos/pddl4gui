# Planification automatique avec PDDL4J et PDDL4GUI 

### PDDL4J

PDDL4J est une librairie open-source (sous licence LGPL) écrite en Java dont l'objectif est de (1) proposer un outil complet intégrant de nombreux planificateurs basés sur le langage PDDL (*Planning Domain Description Language*) et (2) faciliter le développement de nouveaux outils et logiciels Java dédiés à la planification automatique.

La planification automatique est une branche de l'intelligence artificielle qui se focalise sur la réalisation de stratégies ou de séquences d'action dans le but de résoudre un problème donné, généralement pour l'exécution d'agents intelligents, de robots autonomes ou de véhicules autonomes. La librairie PDDL4J intègre donc des algorithmes innovants pour résoudre des problèmes de planification, *i.e.* d’organisation dans le temps de tâches à accomplir pour résoudre un problème donné. La caractéristique principale de la planification est sa dimension temporelle. Souvent ébauchée par une liste des choses à faire, elle se concrétise ensuite par un plan répondant de façon détaillée et concrète aux principaux aspects opérationnels du type : *qui*, *quoi*, *où*, *quand*, *comment*, *combien*.

La librairie PDDL4J fournit :
  - Un parser pour le langage PDDL (en version 3.1) ainsi qu'un ensemble de classes et méthodes permettant de manipuler tous les concepts relatifs à ce langage.
  - Un ensemble de mécanismes permettant d'instancier et simplifier des opérateurs en actions basées sur les propriétés d'inertie.
  - Un ensemble d'heuristiques.
  - Des nombreux exemples de problèmes de planification pouvant être résolu grâce à PDDL4J.

[PDDL4J on github](https://github.com/pellierd/pddl4j)

### PDDL4GUI

PDDL4GUI est une petite application écrite en Java qui fournit une interface graphique à la librairie PDDL4J. PDDL4GUI propose :
  - Une interface pour la résolution de problèmes de planification grâce à PDDL4J.
  - L'intégration de VAL (The plan validation system) qui offre la possibilité de tester la validité des solutions fournies par PDDL4J.
  - Un éditeur PDDL.

Fonctionnalités qui vont être integrées par la suite :
  - Une visualisation des solutions (plans) ;
  - Un comportement Anytime ;
  - ...
  
#### Comment l'utiliser
Télécharger ou cloner le repository.

Sous windows avec la ligne de commande :

    java -javaagent:libs/pddl4j-3.5.0.jar -server -Xms2048m -Xmx2048m -jar pddl4gui.jar

Sous linux avec le script *pddl4gui.sh* disponible à la racine du repository :
    
    ./pddl4gui.sh
    
*Note : les dossiers libs et resources sont nécessaires car non intégré dans le jar.*
