# 🎮 App de Gestión de Juegos - GameVault

Una aplicación móvil desarrollada en **Kotlin** que permite a los usuarios gestionar una colección de videojuegos. La app ofrece una experiencia intuitiva para agregar, editar, eliminar y buscar juegos usando Firebase como backend.

**Desarrollado por:** Quezada Chorres Cesar Alexander

---

## ✨ Características principales

- 📋 Registro de juegos con los siguientes campos:
  - Título
  - Género
  - Plataforma
  - Año de lanzamiento
  - Calificación (rating)
  - Estado de completado
  - Descripción
- 📝 Edición de juegos ya registrados (se reutiliza el mismo formulario de registro)
- 🗑️ Eliminación de juegos desde la lista principal
- 🔍 Búsqueda combinada por **género** y **rating** (por ejemplo: `rpg 4.5`)
- ☁️ Integración con **Firebase Realtime Database**

---

## 👁️ Vistas de la aplicación

### 🧾 Vista de Login
<img src="https://github.com/user-attachments/assets/51c4671a-2b95-44dd-85e9-e0d1aafec1bd" alt="Vista login" width="300"/>

---

### 🏠 Vista principal de la App
<img src="https://github.com/user-attachments/assets/b4249eff-ba44-43b6-afb9-a787f001cee1" alt="Vista principal" width="300"/>

---

## 🧩 Componentes principales

### 1. 📄 `FormularioDialogFragment`
- Es un **diálogo reutilizable** para **agregar o editar** juegos.
- Se activa al pulsar el botón “+” o al editar uno desde la lista.
- Permite ingresar todos los datos del juego con validaciones básicas.

<img src="https://github.com/user-attachments/assets/9f109558-2c1f-40c9-85d4-09da4aba3560" alt="Formulario" width="300"/>

---

### 2. 📋 `GamesActivity` (vista principal)
- Muestra **una lista de todos los juegos** del usuario.
- Permite:

#### 🗑️ Eliminación del juego Hollow Knight
**Antes:**
<img src="https://github.com/user-attachments/assets/b98115a5-9c14-4e4d-945f-b3e9217f0956" alt="Antes eliminación" width="300"/>

**Después:**
<img src="https://github.com/user-attachments/assets/269534d9-da74-4600-b4b0-078c80816fe1" alt="Después eliminación" width="300"/>

#### ✏️ Edición de juegos reutilizando el formulario
<img src="https://github.com/user-attachments/assets/4ced78df-2476-4be5-b0b0-25dd918dfc06" alt="Edición" width="300"/>

#### 🔍 Filtrado de juegos por género y calificación

**Filtrar por género:**
<img src="https://github.com/user-attachments/assets/d069a9ff-336e-4c8d-97fc-70333add82cb" width="300"/>

**Filtrar por rating:**
<img src="https://github.com/user-attachments/assets/2f2a0e73-f2df-47ed-b52e-d267afcc6db7" width="300"/>

---
