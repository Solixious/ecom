### Purpose
E-commerce application backend

### Setup
1. Fork the repository
2. Clone the forked repository locally. `git@github.com:<username>/ecom.git`
3. Create a postgres database named `ecom`
4. Update the username and password for flyway in `pom.xml` and application.properties`
5. Execute the flyway db migration. `mvn flyway:migrate`
6. Build the project to generate JOOQ classes. `mvn clean install`

