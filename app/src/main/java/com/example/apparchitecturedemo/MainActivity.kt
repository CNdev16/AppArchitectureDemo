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
import com.example.apparchitecturedemo.presenter.MainContract
import com.example.apparchitecturedemo.presenter.MainPresenter
import com.example.apparchitecturedemo.service.response.GameResponse

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var gameAdapter: GameAdapter

    private lateinit var gameModel: GameModel

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

        gameModel = GameModel()
        mainPresenter = MainPresenter(this, gameModel)

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
        gameAdapter.callBack = mainPresenter::onItemClick

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = gameAdapter
    }

    private fun fetchAllGames() {
        mainPresenter.getAllGames()
    }

    override fun onFetchAllGamesSuccess(games: List<GameResponse>) {
        gameAdapter.gameItems = games
    }

    override fun onFetchAllGamesError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onItemClicked(name: String) {
        Toast.makeText(this, "onItemClicked: $name", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TOOLBAR_TITLE = "App Architecture Demo"
    }
}
