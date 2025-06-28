package com.example.app_s10.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_s10.R
import com.example.app_s10.model.Game
import com.google.android.material.button.MaterialButton

class GameAdapter(
    private var games: List<Game>,
    private val onDeleteClick: (String) -> Unit,
    private val onEditClick: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var originalGames: List<Game> = games.toList() // copia original para filtrar

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvGameTitle)
        val tvGenre: TextView = itemView.findViewById(R.id.tvGameGenre)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBarItem)
        val anioLanzamiento: TextView = itemView.findViewById(R.id.anio_lanzamiento)
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btnDelete)
        val btnEdit: MaterialButton = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.tvTitle.text = game.title
        holder.tvGenre.text = game.genre
        holder.ratingBar.rating = game.rating
        holder.anioLanzamiento.text = game.releaseYear.toString()

        holder.btnDelete.setOnClickListener {
            game.id?.let { onDeleteClick(it) }
        }

        holder.btnEdit.setOnClickListener {
            onEditClick(game)
        }
    }

    override fun getItemCount(): Int = games.size

    fun updateGames(newGames: List<Game>) {
        originalGames = newGames.toList()
        games = newGames
        notifyDataSetChanged()
    }

    fun filterByGenre(query: String) {
        games = if (query.isBlank()) {
            originalGames
        } else {
            originalGames.filter {
                it.genre.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}
