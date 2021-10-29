# Vedag Api

1. Compilé le projet
    - mvn clean package -Pprod -Dmaven.test.skip=true
    - mvn package -Pdev -Dmaven.test.skip=true
    - mvn package -Ptest -Dmaven.test.skip=true

2. Créer une image Docker
    - docker build -f ./Api/Dockerfile -t vedag_api .

3. Voir les profiles activés
    -  mvn help:active-profiles

## Fonction

### Dettes

    - Definition
        une dette est semble des sommes que l'utilisateur doit à des tières personnes
        actuellement les dettes sont ciblés sur les comptes 1640 et les comptes 5120 qui sont négatif