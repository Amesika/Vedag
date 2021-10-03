# Vedag Api

1. Compilé le projet
    - ./mvnw clean package -Pprod -Dmaven.test.skip=true
    - ./mvnw clean package -Pdev -Dmaven.test.skip=true

2. Créer une image Docker
    - docker build -f ./Api/Dockerfile -t vedag_api .