package com.example.app_s10.views

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_s10.R
import com.example.app_s10.model.Game
import com.example.app_s10.recyclerView.GameAdapter
import com.example.app_s10.utils.RealTimeManager
import kotlinx.coroutines.launch
import android.text.TextWatcher
import android.text.Editable
import android.widget.ImageView


class GamesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private var gameList: List<Game> = emptyList()
    private lateinit var searchView: SearchView
    private lateinit var btnRegresar: ImageView

    private lateinit var realTimeManager: RealTimeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_games)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRegresar = findViewById(R.id.regresar)
        recyclerView = findViewById(R.id.my_games)
        realTimeManager = RealTimeManager()


        btnRegresar.setOnClickListener{
            finish()
        }

        lifecycleScope.launch {
            realTimeManager.getGamesFlow().collect { games ->
                gameList = games
                gameAdapter.updateGames(gameList)
            }
        }

        showGames()

        val searchEditText = findViewById<EditText>(R.id.searchEditText)


        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                filterGames(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun showGames() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        gameAdapter = GameAdapter(
            games = gameList,
            onDeleteClick = { gameId ->
                realTimeManager.deleteGame(gameId)
            },
            onEditClick = { game ->
                val dialog = FormularioDialogFragment()

                val bundle = Bundle()
                bundle.putParcelable("game", game) // Asegúrate de que Game implemente Parcelable
                dialog.arguments = bundle

                dialog.show(supportFragmentManager, "FormularioDialog")
            }
        )
        recyclerView.adapter = gameAdapter
    }

    private fun filterGames(query: String) {
        val words = query.lowercase().split(" ")

        var genreFilter: String? = null
        var ratingFilter: Float? = null

        for (word in words) {
            val rating = word.toFloatOrNull()
            if (rating != null) {
                ratingFilter = rating
            } else {
                genreFilter = word
            }
        }

        val filtered = gameList.filter { game ->
            val matchesGenre = genreFilter?.let {
                game.genre.lowercase().contains(it)
            } ?: true

            val matchesRating = ratingFilter?.let {
                game.rating >= it
            } ?: true

            matchesGenre && matchesRating
        }

        gameAdapter.updateGames(filtered)
    }


}
