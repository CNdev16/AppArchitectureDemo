package com.example.apparchitecturedemo

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.apparchitecturedemo.adapter.GameAdapter
import com.example.apparchitecturedemo.model.GameModel
import com.example.apparchitecturedemo.service.response.GameResponse

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var gameAdapter: GameAdapter

    private lateinit var gameModel: GameModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

        gameModel = GameModel()

        setupToolbar()
        setupRecyclerView()

        fetchAllGames()
    }

    private fun setupToolbar() {
        toolbar.title = TOOLBAR_TITLE

        setSupportActionBar(toolbar)
    }

    private fun setupRecyclerView() {
        gameAdapter = GameAdapter()
        gameAdapter.callBack = ::onItemClicked

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = gameAdapter
    }

    private fun onItemClicked(item: GameResponse) {
        Toast.makeText(this, "onItemClicked: ${item.title}", Toast.LENGTH_SHORT).show()
    }

    private fun fetchAllGames() {
        showLoading()

        gameModel.getAllGames(
            onSuccess = ::onFetchAllGamesSuccess,
            onError = ::onFetchAllGamesError
        )
    }

    private fun onFetchAllGamesSuccess(games: List<GameResponse>) {
        hideLoading()

        gameAdapter.gameItems = games
    }

    private fun onFetchAllGamesError(errorMessage: String) {
        hideLoading()

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    companion object {
        private const val TOOLBAR_TITLE = "App Architecture Demo"
    }
}
