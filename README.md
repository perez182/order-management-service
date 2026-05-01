# Order Management Service

Este microservicio actúa como un orquestador que gestiona la comunicación entre el servicio de clientes (`customer-service`) y el servicio de pedidos (`order-service`). Proporciona una interfaz unificada para la gestión de órdenes.

## Tecnologías y Versiones

- **Java:** 17
- **Spring Boot:** 3.5.14
- **Spring Cloud:** 2025.0.2
- **Maven:** 3.x
- **Docker:** Engine 20.x+ / Desktop
- **SpringDoc OpenAPI (Swagger UI):** 2.8.16
- **Resilience4j:** Para manejo de Circuit Breaker.

## Requisitos Previos

- JDK 17 instalado.
- Maven instalado (o usar el `mvnw` incluido).
- Docker y Docker Compose (opcional, para despliegue en contenedores).

## Cómo Levantar la Aplicación

### Importante: Funcionamiento en Ecosistema

Para el correcto funcionamiento de este servicio en un entorno de contenedores, **no se debe ejecutar de forma individual** ni este servicio ni los otros dos repositorios a los que se conecta (`customer-service` y `order-service`).

Para correr todo el ecosistema de forma correcta mediante Docker, se debe utilizar el **super docker compose** ubicado en este repositorio:
👉 [https://github.com/perez182/infra-docker-services-nova.git](https://github.com/perez182/infra-docker-services-nova.git)

### Ejecución Local (Maven)

Si se prefiere ejecutar la aplicación directamente con Maven (fuera de Docker), **no es necesario** correr el super docker compose. Sin embargo, debes asegurarte de que los servicios de los que depende estén disponibles en las URLs configuradas en `application.properties`.

1. Clona el repositorio.
2. Abre una terminal en la raíz del proyecto.
3. Ejecuta el siguiente comando:
   ```bash
   ./mvnw spring-boot:run
   ```
   O si tienes Maven instalado:
   ```bash
   mvn spring-boot:run
   ```

La aplicación estará disponible en: `http://localhost:8084`

### Ejecución con Docker (Individual - Solo Desarrollo/Pruebas)

Si deseas construir la imagen localmente:

1. Construir la imagen:
   ```bash
   docker build -t order-management-service .
   ```
2. Ejecutar el contenedor:
   ```bash
   docker run -p 8084:8084 order-management-service
   ```

## Documentación API (Swagger)

Una vez que la aplicación esté corriendo, puedes acceder a la documentación interactiva en:
`http://localhost:8084/swagger-ui.html`

## Endpoints Principales

- **POST http://localhost:8084/order-management:** Crea la orden de compra

- **Actuator Health:** `http://localhost:8084/actuator/health`
