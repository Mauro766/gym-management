# Gym Management

Sistema de gestión para gimnasios desarrollado con **JavaFX** y **Spring Boot**.

El objetivo del proyecto es permitir que el propietario o administrador de un gimnasio pueda gestionar socios, pagos, vencimientos y demás información relacionada con la administración del gimnasio.

---

## 🏗️ Arquitectura

El proyecto está dividido en dos aplicaciones independientes:

```text
JavaFx-Gym/
│
├── backend/
│   ├── pom.xml
│   └── src/
│
├── frontend/
│   ├── pom.xml
│   └── src/
│
├── .gitignore
└── README.md
```

### Backend

Desarrollado con:

* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
* Maven

El backend funciona como una API REST encargada de manejar la lógica de negocio y el acceso a la base de datos.

### Frontend

Desarrollado con:

* Java
* JavaFX
* FXML
* CSS
* Maven

El frontend proporciona la interfaz gráfica utilizada por el administrador del gimnasio.

---

## 🔄 Funcionamiento

La comunicación entre las aplicaciones funciona de la siguiente manera:

```text
┌──────────────────────┐
│      JavaFX          │
│      Frontend        │
└──────────┬───────────┘
           │
           │ HTTP / REST
           ▼
┌──────────────────────┐
│     Spring Boot      │
│       Backend        │
└──────────┬───────────┘
           │
           │ JPA / JDBC
           ▼
┌──────────────────────┐
│        MySQL         │
│      Database        │
└──────────────────────┘
```

El frontend **no accede directamente a MySQL**. Toda la comunicación con la base de datos pasa por el backend.

---

# 🚀 Instalación

## 1. Requisitos

Antes de ejecutar el proyecto es necesario tener instalado:

* [Java JDK 17](https://adoptium.net/)
* [Git](https://git-scm.com/)
* MySQL
* IntelliJ IDEA u otro IDE compatible con Java
* Maven

> Si el proyecto utiliza Maven Wrapper, no será necesario instalar Maven manualmente.

---

## 2. Clonar el repositorio

Clonar el proyecto:

```bash
git clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
```

Ingresar al proyecto:

```bash
cd JavaFx-Gym
```

---

# 🗄️ Base de datos

Crear una base de datos MySQL:

```sql
CREATE DATABASE gym_management;
```

Luego configurar las credenciales de conexión del backend.

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gym_management
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

### ⚠️ Seguridad

No subir contraseñas, claves API ni información sensible al repositorio.

Las configuraciones privadas deben mantenerse fuera de Git o mediante variables de entorno.

---

# ⚙️ Ejecutar el Backend

Ingresar a la carpeta:

```bash
cd backend
```

Ejecutar:

```bash
mvn spring-boot:run
```

Si el proyecto utiliza Maven Wrapper:

### Windows

```cmd
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

El backend estará disponible en el puerto configurado en `application.properties`.

Por ejemplo:

```text
http://localhost:8080
```

---

# 🖥️ Ejecutar el Frontend

Abrir otra terminal.

Ingresar a:

```bash
cd frontend
```

Ejecutar:

```bash
mvn javafx:run
```

O utilizando Maven Wrapper:

### Windows

```cmd
mvnw.cmd javafx:run
```

### Linux / macOS

```bash
./mvnw javafx:run
```

El frontend se conectará con la API del backend.

---

# 📁 Estructura del proyecto

## Backend

```text
backend/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── gym/
    │   │           ├── controller/
    │   │           ├── model/
    │   │           ├── repository/
    │   │           ├── service/
    │   │           └── ...
    │   │
    │   └── resources/
    │       └── application.properties
    │
    └── test/
```

## Frontend

```text
frontend/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── gym/
        │
        └── resources/
            └── com/
                └── gym/
                    ├── css/
                    └── view/
```

---

# 👥 Funcionalidades

Actualmente el sistema está orientado a la gestión de:

* Socios
* Datos de los socios
* Pagos
* Estado de pago
* Vencimientos
* Administración del gimnasio
* Visualización de información mediante dashboard

Se agregarán nuevas funcionalidades a medida que avance el desarrollo.

---

# 🌱 Flujo de trabajo con Git

Crear una nueva rama para cada funcionalidad:

```bash
git checkout -b feature/nombre-funcionalidad
```

Ejemplo:

```bash
git checkout -b feature/gestion-pagos
```

Después de realizar los cambios:

```bash
git add .
```

```bash
git commit -m "feat: add payment management"
```

Subir la rama:

```bash
git push -u origin feature/gestion-pagos
```

Luego crear un Pull Request para integrar los cambios a `main`.

---

# 📌 Convención de commits

Se recomienda utilizar mensajes de commit descriptivos.

Ejemplos:

```text
feat: add member registration
feat: add payment management
fix: correct payment expiration
refactor: improve member service
docs: update project documentation
chore: update dependencies
```

---

# 🔐 Variables de entorno

Las credenciales y configuraciones sensibles no deben almacenarse directamente en el repositorio.

Ejemplo:

```env
DB_URL=jdbc:mysql://localhost:3306/gym_management
DB_USERNAME=root
DB_PASSWORD=password
```

Cada desarrollador debe configurar sus propias variables de entorno.

---

# 🤝 Contribución

1. Clonar el repositorio.
2. Crear una nueva rama.
3. Realizar los cambios.
4. Probar la aplicación.
5. Crear un commit.
6. Subir la rama.
7. Crear un Pull Request.

---

# 📄 Licencia

Este proyecto se encuentra actualmente en desarrollo.

La licencia y las condiciones de distribución serán definidas posteriormente.

---

## 👨‍💻 Desarrollo

Proyecto desarrollado como sistema de gestión para gimnasios.

**Gym Management**
