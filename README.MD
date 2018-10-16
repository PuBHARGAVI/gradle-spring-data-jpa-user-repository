# User CRUD Application with Spring Data JPA

This application serves as a simple Restful CRUD example service using a User, Group, Role, and Permission schema.  

##Tech Highlights
* Java 8
  - Stream API
* Spring Boot
  - Profile driven design
* Spring AOP
  - Annotation && method pointcut
* Spring Data
  - Projections
  - Hibernate mappings
  - Validation
  - @Query
    - JPQL
    - Native
* Spring Web
  - RestController
  - Validation
  - RestControllerAdvice
    - Custom exception handling
* Postgresql

## Getting Started

These instructions will get you up and running on your local machine.

1. Pull the repository in and open it up with your favorite IDE.
2. Install or setup Postgres 10 (I'm not a big fan of using embedded DBs for samples)
  -  I highly suggest using Docker since it's quick!
   ```
     docker run --name user-app-postgres -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=user_app -p5432:5432  -d postgres
   ```
   - Note: If you don't want to keep it, just run it in interactive and "self-desctruct on quit mode" by adding -it --rm to the docker run command
3. If you need to, use your IDE's "run configuration" to overwrite any properties via environment variables
4. Run UserCrudApplication.java

### Prerequisites
* Postgres 9+
* Java 8 JRE

