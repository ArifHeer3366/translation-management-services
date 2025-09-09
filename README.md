ranslation Management Service

A Spring Boot API-driven service for managing translations across multiple locales with context-based tags. This project is designed to be scalable, performant, and secure, supporting large datasets efficiently.

Table of Contents

Features

Technical Requirements

Database Schema

API Endpoints

Performance

Security

Setup & Installation

Testing & Scalability

Optional / Plus Features

Features

Store translations for multiple locales (en, fr, es, etc.)

Tag translations for context (e.g., mobile, desktop)

Create, update, view, and search translations by key, tag, locale, or content

JSON export endpoint for frontend applications (e.g., Vue.js)

High-performance endpoints (<200ms for CRUD, <500ms for export)

Populate database with 100k+ records for scalability testing

Technical Requirements

Java 17+, Spring Boot 3+

Spring Data JPA (Hibernate)

PostgreSQL/MySQL (configurable)

JWT token-based authentication

SOLID principles, optimized SQL, scalable DB schema

Database Schema

translations

Column	Type	Notes
id	BIGINT PK	Auto-increment
key	VARCHAR	Translation key
locale	VARCHAR	en, fr, es
value	TEXT	Translated value
created_at	TIMESTAMP	Default CURRENT_TIMESTAMP
updated_at	TIMESTAMP	Auto update on change

tags

Column	Type	Notes
id	BIGINT PK	Auto-increment
name	VARCHAR	Context tag

translation_tags (Many-to-Many)

Column	Type	Notes
translation_id	BIGINT FK	References translations.id
tag_id	BIGINT FK	References tags.id
API Endpoints
Method	Endpoint	Description
POST	/api/translations	Create or update a translation
GET	/api/translations	Get all translations
GET	/api/translations/search	Search by key, tag, locale, or content
GET	/api/translations/export	Export translations as JSON for frontend
POST	/api/tags	Create a new tag
GET	/api/tags	Get all tags
POST	/api/auth/login	Authenticate user and receive JWT token
Performance

CRUD endpoints: <200ms response time

JSON export: <500ms even with 100k+ translations

Database indices: translations(key, locale), translation_tags(tag_id)

JSON export uses Jackson streaming API for memory efficiency

Optional in-memory caching for repeated requests

Security

JWT token-based authentication

Protected endpoints: /api/translations/** and /api/tags/**

curl --location 'http://localhost:8085/auth/login' \
--header 'Content-Type: application/json' \
--data '{
"username": "admin",
"password": "admin123"
}'

Example Authorization header:

Authorization: Bearer <JWT_TOKEN>

Setup & Installation
Prerequisites

Java 17+

Maven 3.8+

PostgreSQL or MySQL database

Steps

Clone the repository:

git clone <repo_url>
cd translation-management-service


Configure application.yml or application.properties with your database credentials:

spring:
datasource:
url: jdbc:postgresql://localhost:5432/translation_db
username: postgres
password: postgres
jpa:
hibernate:
ddl-auto: update


Build and run:

mvn clean install
mvn spring-boot:run


Access Swagger UI for API documentation:

http://localhost:8080/swagger-ui.html

Testing & Scalability
Populate Database with 100k+ Records

Use the provided TestDataGenerator or CommandLineRunner to insert bulk data:

@Component
public class TestDataGenerator implements CommandLineRunner {
@Autowired private TranslationRepository repo;

     @PostConstruct
    public void loadTestData() {
        for (int i = 1; i <= 100000; i++) {
            TranslationCreateDTO dto = new TranslationCreateDTO();
            dto.setNamespace("ns" + (i % 100));
            dto.setKey("key" + i);
            dto.setTags(Set.of("tag" + (i % 10)));
            dto.setValues(Map.of("en", "Hello " + i, "fr", "Bonjour " + i, "es", "Hola " + i));

            translationService.createTranslation(dto);
        }
    }


Validate JSON export performance (<500ms)


Includes controller, service, repository, and performance tests

Optional / Plus Features

Docker setup (Dockerfile + docker-compose.yml)

CDN support for static JSON export

Swagger/OpenAPI documentation

Contact

For any questions, please reach out to: arif.hussain6788@gmail.com