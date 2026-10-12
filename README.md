# Baozi Store - API REST

API REST simples (Java 17 + Spring Boot 3 + Spring Data JPA) para controle de clientes, produtos e pedidos da Baozi Store.

## Como executar
```bash
mvn spring-boot:run
```
Sobe em `http://localhost:8080` com banco H2 (arquivo em `./data`). Console do banco: `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:file:./data/baozidb`, usuario `sa`, senha vazia).

Para usar MySQL: crie o banco `baozidb` e rode
`mvn spring-boot:run -Dspring-boot.run.profiles=mysql` (ajuste usuario/senha em `application-mysql.properties`).

## Endpoints
| Metodo | Rota | Descricao |
|---|---|---|
| POST | /clientes, /produtos, /pedidos | Criar |
| GET | /clientes/{id}, /produtos/{id}, /pedidos/{id} | Consultar por ID |
| GET | /clientes, /produtos, /pedidos | Listar todos |
| PUT | /clientes/{id}, /produtos/{id}, /pedidos/{id} | Atualizar (opcional) |
| DELETE | /clientes/{id}, /produtos/{id}, /pedidos/{id} | Apagar |

Importe `Baozi-Store.postman_collection.json` no Postman para testar.
