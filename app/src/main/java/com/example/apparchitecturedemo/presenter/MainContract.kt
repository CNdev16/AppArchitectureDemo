package com.example.apparchitecturedemo.presenter

import com.example.apparchitecturedemo.service.response.GameResponse

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()

        fun onItemClicked(name: String)

        fun onFetchAllGamesSuccess(games: List<GameResponse>)
        fun onFetchAllGamesError(errorMessage: String)
    }

    interface Presenter {
        fun getAllGames()

        fun onItemClick(item: GameResponse)
    }
}
