package com.example.app_s10.views

import android.os.Bundle
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

class GamesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private var gameList: List<Game> = emptyList()

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

        recyclerView = findViewById(R.id.my_games)
        realTimeManager = RealTimeManager()




        lifecycleScope.launch {
            realTimeManager.getGamesFlow().collect { games ->
                gameList = games
                gameAdapter.updateGames(gameList)
            }
        }

        showGames()
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

}
