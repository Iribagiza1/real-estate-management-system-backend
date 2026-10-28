# Real Estate Management System

**Student:** IRIBAGIZA Umwiza
**Student ID:** 26338
**Project name:** real-estate-management-system
**Course:** Web Technology and Internet (INSY 8322), midterm project
**Date:** 2026-10-28

## Description

A Spring Boot REST API (Spring Data JPA and PostgreSQL) that manages property types, properties, agents, buyers, viewings and offers for a real estate agency.

## Requirements to run

- Java 17
- Maven
- PostgreSQL 17 (or compatible)

## Steps to run the API

1. Create an empty PostgreSQL database named `real_estate_db`.
2. Open `src/main/resources/application.properties` and put your own PostgreSQL username and password in these two lines:

```
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
```

3. In a terminal, in the project folder (the one with `pom.xml`), run:

```
mvn spring-boot:run
```

4. The API runs at `http://localhost:8082`. The tables are created automatically.
5. Import `real-estate-management-system-midterm.postman_collection.json` into Postman to test every requirement.

## Endpoints

| ID | Method and path |
|---|---|
| A2 | CRUD on `/api/property-types`, `/api/properties`, `/api/agents`, `/api/buyers`, `/api/viewings` (`/save`, `/all`, `/{id}`, `/update/{id}`, `/delete/{id}`) |
| B1 | GET `/api/buyers/by-last-name?lastName=` |
| B2 | GET `/api/viewings/by-status?status=` |
| B3 | GET `/api/viewings/between?start=&end=` |
| C1 | GET `/api/agents/by-property-type?name=` |
| C2 | GET `/api/properties/free` |
| C3 | GET `/api/property-types/unused` |
| C4 | GET `/api/buyers/of-agent/{agentId}` |
| D1 | POST `/api/viewings/save` |
| D2 | PATCH `/api/viewings/{id}/status?status=` |
| D3 | PUT `/api/agents/{agentId}/property-types/{propertyTypeId}` |
| D4 | PATCH `/api/agents/{agentId}/property?plotNumber=` |
| E1 | POST `/api/offers/save?viewingId=` |
| E2 | GET `/api/offers/buyer/{nationalId}` |
| E3 | DELETE `/api/offers/delete/{id}` |
| F1 | GET `/api/viewings/stats/by-status` |
| F2 | PATCH `/api/viewings/cancel-day?agentId=&date=` |
| Bonus | GET `/api/dashboard/summary` |
| Bonus | GET `/api/viewings/page?page=0&size=5&sort=viewingDate,desc` |