# 💇‍♀️ Studio Aris - Backend

Backend del sistema de gestión para estudio de belleza **Studio Aris**, desarrollado con Spring Boot. Proporciona una API REST para la administración de usuarios, citas, ventas, productos y servicios, con autenticación mediante JWT.

---

## 🚀 Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* PostgreSQL (Supabase)
* Maven

---

## 🔐 Autenticación

El sistema implementa autenticación basada en **JWT**, permitiendo:

* Login de usuarios
* Protección de rutas
* Manejo de roles (ADMIN, USER)

---

## 📌 Funcionalidades principales

### 👤 Usuarios

* Registro de usuarios
* Login con JWT
* Listado de usuarios
* Eliminación de usuarios

### 📅 Citas

* Crear citas
* Cancelar / Atender citas
* Listado de citas activas
* Filtros por cliente, estado y servicio
* Dashboard de citas

### 💰 Ventas

* Registro de ventas
* Dashboard de ventas
* Filtros por fechas
* Generación de reportes PDF

### 📦 Productos y Servicios

* CRUD completo
* Búsqueda dinámica

---

## 🌐 Endpoints principales

### 🔐 Auth

```
POST /api/auth/login
POST /api/auth/register
GET  /api/auth/usuarios
DELETE /api/auth/{username}
```

### 📅 Citas

```
GET    /api/citas
POST   /api/citas
PUT    /api/citas/{id}/cancelar
PUT    /api/citas/{id}/atender
GET    /api/citas/dashboard
```

### 💰 Ventas

```
GET    /api/ventas
POST   /api/ventas
GET    /api/ventas/dashboard
GET    /api/ventas/filtrar
GET    /api/ventas/reporte/pdf
```

---

## ⚙️ Configuración

### 📌 Base de datos (Supabase)

Configurar en `application.properties`:

```
spring.datasource.url=jdbc:postgresql://HOST:PORT/postgres
spring.datasource.username=USER
spring.datasource.password=********
spring.jpa.hibernate.ddl-auto=update
```

---

## 🔄 CORS

Configurado para permitir conexiones desde el frontend desplegado en Vercel.

---

## ▶️ Ejecución

```bash
mvn clean install
mvn spring-boot:run
```

---

## ☁️ Despliegue

Backend desplegado en Railway.

---

## 📌 Notas

* Arquitectura basada en capas (Controller, Service, Repository)
* Uso de DTOs para respuestas personalizadas
* Seguridad implementada con filtros JWT

---

## 👨‍💻 Autor

Desarrollado por Valenzuela Sedano Jhon Richard.

Proyecto enfocado en práctica profesional de desarrollo fullstack (Angular + Spring Boot).
