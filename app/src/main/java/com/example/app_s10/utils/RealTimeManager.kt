package com.example.app_s10.utils


import com.example.app_s10.model.Game
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RealTimeManager {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("games")
    private val authManager = AuthManager()

    fun addGame(game: Game){
        val key = databaseReference.push().key
        if(key!=null){
            game.id = key
            game.userId = authManager.getCurrentUser().toString()
            databaseReference.child(key).setValue(game)
        }
    }


}