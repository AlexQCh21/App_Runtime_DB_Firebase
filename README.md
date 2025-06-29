# 🎮 App de Gestión de Juegos - Android

Una aplicación móvil desarrollada en **Kotlin** que permite a los usuarios gestionar una colección de videojuegos. La app ofrece una experiencia intuitiva para agregar, editar, eliminar y buscar juegos usando Firebase como backend.

**Desarrollado por:** Quezada Chorres Cesar Alexander

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

## 👁️ Vistas de la aplicación
###Vista de Login
![Vista principal](https://github.com/user-attachments/assets/51c4671a-2b95-44dd-85e9-e0d1aafec1bd)


### 1. 📄 `FormularioDialogFragment`
- Es un **diálogo reutilizable** para **agregar o editar** juegos.
- Se activa al pulsar el botón “+” o al editar uno desde la lista.
- Permite ingresar todos los datos del juego con validaciones básicas.

### 2. 📋 `GamesActivity` (vista principal)
- Muestra **una lista de todos los juegos** del usuario.
- Permite:
  - 🗑️ **Eliminar** juegos con botón individual.
  - ✏️ **Editar** juegos reutilizando el formulario.
  - 🔍 **Filtrar** la lista por género o rating desde un campo de búsqueda interactivo.

## 📱 Capturas de pantalla (opcional)
<sub>*Puedes incluir aquí capturas si deseas*</sub>

## 🛠️ Tecnologías usadas

- Kotlin
- Android SDK
- Firebase Realtime Database
- RecyclerView
- DialogFragment
- Material Design (TextInputLayout, RatingBar, Buttons)
- TextInputEditText con ícono personalizado para búsqueda

## 📦 Estructura del Proyecto

