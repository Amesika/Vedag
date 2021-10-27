# Dêttes

## Objectif

L'objectif est répresenté les dêttes sur une échelles de temps d'environ 5 ans minimum.<br>
Afin de pouvoir faire une prise de décision.<br>

## Fonctions
01. Définition d'une dêtte
02. Créer les dettes
03. Modification des dêttes
04. Supprimer des dêttes
05. Interface de modification des dêttes
06. Interface de création d'une dêtte
07. Caluler les informations d'un dettes
08. Lister les dêttes
09. Afficher la liste des dêttes
10. Afficher le graphe des dêttes
11. Afficher la graphe d'une dêttes

### 01 - Définition d'une dêtte
    
    Une dêtte est un solde des comptes banques (5120) négatif et des soldes des comptes dêttes  (1640).<br>
    Une dêtte comporte :
        - nom de la dêtte
        - description de la dêtte
        - La somme de la dêttes
        - Le nombre des échéances
        - Liste des dates d'échéance
        - La somme de l'échéance
        - Date de début l'échéance
        - Date de fin de l'échéance
        - Taux de la dêtte
        - Pourcentage d' l'avance du remboursement
        - Somme actuelle
        - Somme du
        - Compte comptable au quelle il est associé

### 02 - Créer les dettes
    - La création d'une dêtte est Toujour Associer à un compte comptable et un espace de travail.
    - Cette création se fait à la création d'un compte (1640 ou 5120)
    - S'il la dêtte n'est pas créer en même temps que le compte, elle peux être créé ultérieurement
    par un mecanisement de mise à jour. 

### 03 - Modification des dêttes
    - La modification de ce fait à chaque mise à jour du journal.
    - La modification peux être effectué par un mécanisement de mise à jour
    - Une interface de modification se mise en place pour entré des informations complémentaire 
    nécessaire à la mise à jour complète des dêttes.

### 04 - Supprimer des dêttes
    - la suppression de la dêtte est effectuée uniquement quand les comptes sont supprimées 

### 05 - Interface d'entré modification dêtte
    - Une interface web pour modifier une dêttes, l'interface doit contenir :        
        - Somme actuelle (en lecture seul)
        - La somme de la dêttes
        - La somme de l'échéance
        - Date de début l'échéance 
        - Date de fin de l'échéance
        - Taux de la dêtte
        - Compte comptable au quelle il est associé
        - nom de la dêtte
        - description de la dêtte

### 06 - Interface de création d'une dêtte
    - Un bouton d'actuallisation sera le mécanisme de création des dêttes

### 07 - Caluler les informations d'un dettes
    - Nous allons lister les informations à calculer pour la dêtte
        - le pourcentage de progression
        - la liste des dates écheances
        - la somme dû au final

### 08 - Lister les dêttes
    - Nous avons desoin de récuper la liste de tous les dêttes avec les données définit au chapitre **### 01**

### 09 - Afficher la liste des dêttes
    - Une fois que cette liste est disponible nous devons est capable de le représenté de manière structuré (jolie)

### 10 - Afficher le graphe des dêttes
    - l' afficha d' un graphe d'une dette, sur l'abscisse nous aurons les dates d'échances <br>
    sur l'ordonner nous arrons la montan de l'échance.
    - Avoir une échelle de temps sur 5 ans.

### 11 - Afficher la graphe d'une dêttes
    - Reprendre la graphe individuelle et le fair pour tous les dêttes.
    Et notre objectif sera attein.
