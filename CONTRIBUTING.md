# Contributing til Alpha Solutions

Denne guide beskriver hvad et nyt teammedlem skal vide for at bidrage til koden.

## Projektstruktur

src/
main/
java/dk/alphasolutions/

controller/    ← HTTP-forespørgsler og routing

dao/           ← Databaseadgang via JDBC

exception/     ← ValidationException

model/         ← Domæneklasser (User, Project, Task, TimeEntry)

security/      ← SecurityUtil til sessionshåndtering

service/       ← Forretningslogik og validering

resources/

static/css/    ← CSS stylesheet

templates/     ← Thymeleaf HTML-sider

schema.sql     ← Databasestruktur

data.sql       ← Testdata

test/
java/dk/alphasolutions/

dao/           ← Integrationstests mod H2

service/       ← Unit tests med Mockito

resources/

schema.sql     ← H2-kompatibelt skema til tests

data.sql       ← Testdata til H2

## Arkitektur

Systemet følger en 3-lags arkitektur:

- **Controller** modtager HTTP-forespørgsler og kalder service-laget
- **Service** indeholder forretningslogik og validering
- **DAO** håndterer SQL-forespørgsler via JDBC og DataSource

Alle klasser bruger konstruktørinjektion – aldrig field injection med @Autowired.

## Branch-strategi

- `main` – produktionsklar kode, må ikke pushes direkte til
- Opret en feature-branch for hver ny funktion: `feature/navn-på-funktion`
- Lav et pull request til main når funktionen er færdig og testet
- CI-pipeline kører automatisk ved pull requests

## Tilføj ny funktionalitet

1. Opret model-klasse i `model/`
2. Opret DAO-klasse i `dao/` med DataSource injection
3. Opret Service-klasse i `service/` med validering
4. Opret Controller i `controller/` med session-tjek
5. Opret Thymeleaf-side i `resources/templates/`
6. Skriv unit tests i `test/service/`
7. Skriv integrationstests i `test/dao/`

## Kør applikationen lokalt

Se README.md for opsætningsinstruktioner.

## Kør tests

```bash
mvn test
```

Tests bruger automatisk H2 in-memory database via `@ActiveProfiles("test")`
– ingen MySQL-forbindelse nødvendig.

## Kodestil

- Engelsk navngivning på variable og metoder
- Brug `ValidationException` til alle valideringsfejl i service-laget
- Tjek altid session med `SecurityUtil.isLoggedIn()` i starten af alle controller-metoder
- Admin-funktioner tjekkes med `SecurityUtil.isAdmin()`
