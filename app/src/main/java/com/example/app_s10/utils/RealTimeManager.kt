package com.example.app_s10.utils


import com.example.app_s10.model.Game
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealTimeManager {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("games")
    private val authManager = AuthManager()

    fun addGame(game: Game){
        val key = databaseReference.push().key
        if(key!=null){
            game.id = key
            game.userId = authManager.getCurrentUser()?.uid
            databaseReference.child(key).setValue(game)
        }
    }

    fun deleteGame(gameId:String){
        databaseReference.child(gameId).removeValue()
    }

    fun updateGame(gameId:String, updatedGame:Game) {
        databaseReference.child(gameId).setValue(updatedGame)
    }

    fun getGamesFlow(): Flow<List<Game>> {
        val idFilter = authManager.getCurrentUser()?.uid
        return callbackFlow {
            val listener = databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val games = snapshot.children.mapNotNull { snapshot ->
                        val game = snapshot.getValue(Game::class.java)
                        val id = snapshot.key
                        if (game != null && id != null) game.copy(id = id) else null
                    }
                    trySend(games.filter { it.userId == idFilter }).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })

            awaitClose { databaseReference.removeEventListener(listener) }
        }
    }

}