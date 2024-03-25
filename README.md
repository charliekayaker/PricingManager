# Pricing API 

## Responsabilidades
- [Consulta de precios](docs/diagrams/search_prices_flow.png): la búsqueda de precios se puede realizar por producto, 
cadena y una fecha de aplicación.
  - URI: /prices/search?product_id={productID}&brand_id={brandID}&apply_at={applyAt}
## Detalles de diseño
[Documentación ADR](docs/adrs/0001-create-project.md)

### Dependencias
- Java 17
- Maven compiler plugin 3.11.0
- Spring-boot-starter-web 3.2.2
- Spring-boot-starter-test 3.2.2
- Mockito-junit-jupiter 5.1
- Spring-boot-starter-data-jpa (Hibernate + Spring Data JPA + Spring ORM)
- H2 Database 2.2.224
- Flyway core 10.7.1

## Consultas
- Correo electrónico: (carlosalfonso.it@gmail.com)
