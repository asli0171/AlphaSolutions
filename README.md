# Alpha Solutions – Projektkalkulationsværktøj

Et simpelt webbaseret projektkalkulationsværktøj udviklet for Alpha Solutions,
som understøtter oprettelse af projekter, opgaver og tidsregistrering med
rollebaseret adgangskontrol.

## Link til applikation

Applikationen kører lokalt på http://localhost:8080. Azure-deployment afventer genoprettelse af Student-abonnement.


## Tekniske forudsætninger

- Java 17 eller nyere
- Maven 3.x
- MySQL 
- IntelliJ IDEA 2024.3.2

## Sådan starter du applikationen lokalt

1. Klon repositoriet:
```bash
   git clone https://github.com/[dit-brugernavn]/AlphaSolutions.git
```

2. Opret databasen i MySQL Workbench ved at køre:


   src/main/resources/schema.sql
   src/main/resources/data.sql

3. Opdater `src/main/resources/application-dev.properties` med dine MySQL-kredentialer:
```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/AlphaSolutionsDB
   spring.datasource.username=dinBruger
   spring.datasource.password=ditPassword
```

4. Start applikationen via IntelliJ eller kør:
```bash
   mvn spring-boot:run
```

5. Åbn browseren og gå til:

http://localhost:8080


## Testbrugere

| Brugernavn | Adgangskode    | Rolle |
|------------|----------------|-------|
| admin      | adminpassword  | ADMIN |
| user1      | password1      | USER  |
| user2      | password2      | USER  |

## Kør tests

```bash
mvn test
```

## GitHub Actions CI/CD

Applikationen bygges og testes automatisk ved push til main-branchen
via GitHub Actions. Pipeline konfigurationen findes i
`.github/workflows/ci.yml`.

## Teknologier

- Java 17
- Spring Boot 4.0.5
- Thymeleaf 3.1.3
- MySQL
- JDBC via Spring Boot Starter JDBC 4.0.5
- H2 (til tests)
- Mockito (til unit tests)
- GitHub Actions (CI/CD)
- Azure App Service (deployment konfigureret – afventer adgang)
