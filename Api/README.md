# Vedag Api

1. Compilé le projet
    - ./mvnw clean package -Pprod -Dmaven.test.skip=true
    - ./mvnw clean package -Pdev -Dmaven.test.skip=true

2. Créer une image Docker
    - docker build -f ./Api/Dockerfile -t vedag_api .

## Fonction

### Dettes

    - Definition
        une dette est semble des sommes que l'utilisateur doit à des tières personnes
        actuellement les dettes sont ciblés sur les comptes 1640 et les comptes 5120 qui sont négatif