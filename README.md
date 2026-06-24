# 🚗 Automotora — Sistema de Gestión de Vehículos y Ventas

Proyecto Semestral — Arquitectura de Microservicios  
**Asignatura:** DSY1103 Desarrollo FullStack 1  
**Evaluación:** Parcial 3

---

## Integrantes del Equipo

| Nombre | Rol |
|--------|-----|
| Angel Aldea | Backend / Microservicio principal |
| Vicente Villalobos | Gateway / Despliegue |
| Michael  | Tests / Documentación |

---

## Descripción del Proyecto

Sistema de gestión para una concesionaria de vehiculos que permite administrar el inventario de vehículos, los tipos de vehiculo, los vendedores, los clientes y el registro de ventas.

La arquitectura está basada en microservicios con Spring Boot, comunicados a través de un API Gateway centralizado (Spring Cloud Gateway).

---

## Microservicios Implementados

| Microservicio | Puerto | Descripción |
|---------------|--------|-------------|
| **automotora** (microservicio principal) | `8080` | Gestión de vehículos, tipos, clientes, vendedores y ventas |
| **gateway** | `8090` | API Gateway que centraliza el enrutamiento |
| **eureka-server** | `8761` | Service Discovery — registro y descubrimiento de servicios |

### Entidades del microservicio principal

| Entidad | Endpoints base | Descripción |
|---------|---------------|-------------|
| `Vehiculo` | `/api/vehiculos` | CRUD + búsqueda por marca, año y tipo |
| `TipoVehiculo` | `/tipovehiculos` | Gestión de categorías de vehículos |
| `Cliente` | `/api/clientes` | Registro y administración de clientes |
| `Vendedor` | `/api/vendedores` | Registro y administración de vendedores |
| `Venta` | `/api/ventas` | Registro de ventas, anulación, búsqueda por vendedor/cliente |

---

## Rutas del Gateway (puerto 8090)

Todas las peticiones al Gateway en puerto `8090` se redirigen al microservicio en `8080`:

| Prefijo de ruta | Destino |
|-----------------|---------|
| `/api/vehiculos/**` | automotora:8080 |
| `/tipovehiculos/**` | automotora:8080 |
| `/api/clientes/**` | automotora:8080 |
| `/api/vendedores/**` | automotora:8080 |
| `/api/ventas/**` | automotora:8080 |
| `/doc/**` | automotora:8080 (Swagger UI) |

 El Gateway usa `lb://automotora` (load balancer) — en lugar de tener la URL 
 hardcodeada, le pregunta a Eureka en qué dirección está corriendo el microservicio.

---

## Documentación Swagger / OpenAPI

| Entorno | URL |
|---------|-----|
| Local (directo) | http://localhost:8080/doc/swagger-ui.html |
| Local (vía Gateway) | http://localhost:8090/doc/swagger-ui.html |
| API Docs JSON | http://localhost:8080/v3/api-docs |

---

## Requisitos previos

- Java 21
- Maven 3.3
- MySQL 8.0 (o Docker)
- Docker Desktop (para despliegue con contenedores)

---

## Instrucciones de Ejecución Local (desde el IDE)

**Orden de arranque obligatorio:** Eureka → Automotora → Gateway

**1. Base de datos MySQL:**
```sql
CREATE DATABASE automotora;
```

**2. Iniciar Eureka Server** (carpeta `eureka-server/`):
```bash
./mvnw spring-boot:run
```
Verificar en: http://localhost:8761  
Ahí aparece el panel web con los servicios registrados.

**3. Iniciar microservicio Automotora** (carpeta raíz):
```bash
./mvnw spring-boot:run
```
Al arrancar se registra automáticamente en Eureka.  
Verificar Swagger: http://localhost:8080/doc/swagger-ui.html

**4. Iniciar Gateway** (carpeta `gateway/`):
```bash
./mvnw spring-boot:run
```
El Gateway consulta a Eureka para encontrar el microservicio usando `lb://automotora`.  
Acceso vía Gateway: http://localhost:8090/api/vehiculos
```

**5. Acceder a Swagger UI:**
```
http://localhost:8080/doc/swagger-ui.html
```

---

### Opción 2 — Con Docker Compose (recomendado)

```bash
# Desde la raíz del proyecto
docker compose up --build
```

Servicios levantados:
- MySQL en puerto `3306`
- Microservicio automotora en puerto `8080`
- Gateway en puerto `8090`

Para detener:
```bash
docker compose down
```
Para detener y eliminar volúmenes (reset BD):
```bash
docker compose down -v
```

---

##  Pruebas Unitarias

Las pruebas están en `src/test/java/com/mcqueen/automotora/` y siguen la estructura Given–When–Then.

### Tests de Service (`service/`)
| Clase | Casos |
|-------|-------|
| `VehiculoServiceTest` | obtenerTodos, obtenerPorId, guardar, patente duplicada, eliminar |
| `VentaServiceTest` | obtenerTodos, crear exitosa, vehículo ya vendido, anular |
| `ClienteServiceTest` | obtenerTodos, obtenerPorId, guardar, RUT duplicado, actualizar, eliminar |
| `VendedorServiceTest` | obtenerTodos, obtenerPorId, guardar, RUT duplicado, eliminar |

### Tests de Controller (`controller/`) — con @WebMvcTest
| Clase | Casos |
|-------|-------|
| `VehiculoControllerTest` | GET lista, GET por id, GET 404, POST crear, DELETE, DELETE 404 |
| `ClienteControllerTest` | GET lista, GET por id, GET 404, POST crear, DELETE |
| `VendedorControllerTest` | GET lista, GET por id, POST crear, DELETE |

**Ejecutar todas las pruebas:**
```bash
./mvnw test
```

---

## Estructura del Proyecto

```
automotora/
├── src/
│   ├── main/
│   │   ├── java/com/mcqueen/automotora/
│   │   │   ├── AutomotoraApplication.java
│   │   │   ├── Controller/         # Capa controladores (CSR)
│   │   │   ├── service/            # Capa servicios / lógica de negocio
│   │   │   ├── Repository/         # Capa repositorios JPA
│   │   │   ├── model/              # Entidades JPA
│   │   │   ├── DTO/                # Objetos de transferencia de datos
│   │   │   ├── config/             # SwaggerConfig, DataInitializer
│   │   │   └── exception/          # GlobalExceptionHandler
│   │   └── resources/
│   │       └── application.yml     # Configuración con perfiles
├── src/test/java/com/mcqueen/automotora/
│   ├── service/                # Tests de lógica de negocio
│   └── controller/             # Tests de endpoints HTTP (@WebMvcTest)
├── eureka-server/              # Service Discovery
│   ├── src/main/resources/application.yml
│   └── pom.xml
├── gateway/                    # API Gateway (usa lb:// con Eureka)
│   ├── src/main/resources/application.yml
│   └── pom.xml
├── Dockerfile
├── docker-compose.yml          # MySQL + Eureka + Automotora + Gateway
└── pom.xml
└── README.md
```

---

## Reglas de Negocio Implementadas

- La **patente** del vehículo debe ser única y de exactamente 6 caracteres (se guarda en mayusculas).
- El **RUT** de clientes y vendedores debe ser único.
- Un **vehiculo** no puede venderse si ya tiene una venta en estado `COMPLETADA`.
- Las ventas pueden **anularse** cambiando su estado a `ANULADA`, pero no si ya estaban anuladas.
- El **precio total** de la venta se toma directamente del precio del vehículo al momento de la venta.
