package com.example.app_s10.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        const val TAG = "AuthManager"
    }

    // Verifica si hay un usuario logueado
    fun checkCurrentUser(): String? {
        val currentUser = auth.currentUser
        return currentUser?.let {
            "Usuario ya autenticado: ${it.email}"
        }
    }

    // Devuelve el usuario actual
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Registra un usuario con email y password
    fun signUpUser(
        email: String,
        password: String,
        onResult: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()  // Opcional
                    onResult(true, user, null)
                } else {
                    val errorMessage = task.exception?.message ?: "Error desconocido"
                    Log.e(TAG, "Error al registrar: $errorMessage")
                    onResult(false, null, errorMessage)
                }
            }
    }

    //Inicia sesión del usuario
    fun signInUser(
        email: String,
        password: String,
        onResult: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onResult(true, user, null)
                } else {
                    val error = task.exception?.message ?: "Error desconocido"
                    Log.e(TAG, "Error al iniciar sesión: $error")
                    onResult(false, null, error)
                }
            }
    }

    //Restablecer contraseña
    fun sendPasswordReset(email: String, onResult: (Boolean, String?) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val error = task.exception?.message ?: "Error al enviar el correo"
                    onResult(false, error)
                }
            }
    }

    //Verificación del email
    fun sendEmailVerification(user: FirebaseUser?, onResult: (Boolean, String?) -> Unit) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val error = task.exception?.message ?: "Error al enviar verificación"
                    onResult(false, error)
                }
            }
    }

    //Iniciar como anónimo
    fun signInAnonymously(onResult: (Boolean, FirebaseUser?, String?) -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onResult(true, user, null)
                } else {
                    val error = task.exception?.message ?: "Error al iniciar sesión como invitado"
                    onResult(false, null, error)
                }
            }
    }

    // Cerrar sesión
    fun logout() {
        auth.signOut()
    }
}
