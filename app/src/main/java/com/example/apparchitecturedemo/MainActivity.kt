package com.example.apparchitecturedemo

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.apparchitecturedemo.adapter.GameAdapter
import com.example.apparchitecturedemo.service.GameService
import com.example.apparchitecturedemo.service.ServiceManager
import com.example.apparchitecturedemo.service.response.GameResponse
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)

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

        val service = ServiceManager.getInstance().create(GameService::class.java)

        service.getAllGames().enqueue(object : retrofit2.Callback<List<GameResponse>> {
            override fun onResponse(
                call: Call<List<GameResponse>>,
                response: Response<List<GameResponse>>
            ) {
                val data = response.body()

                when {
                    (response.isSuccessful && (data != null)) -> onFetchAllGamesSuccess(data)
                    else -> onFetchAllGamesError(throw Throwable("Something went wrong."))
                }
            }

            override fun onFailure(call: Call<List<GameResponse>>, t: Throwable) {
                onFetchAllGamesError(t.localizedMessage ?: "Something went wrong.")
            }
        })
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
