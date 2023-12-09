package com.example.apparchitecturedemo.presenter

import com.example.apparchitecturedemo.model.GameModel
import com.example.apparchitecturedemo.service.response.GameResponse

class MainPresenter(
    private val view: MainContract.View,
    private val gameModel: GameModel
) : MainContract.Presenter {

    override fun getAllGames() {
        view.showLoading()

        gameModel.getAllGames(
            onSuccess = ::onFetchAllGamesSuccess,
            onError = ::onFetchAllGamesError
        )
    }

    private fun onFetchAllGamesSuccess(games: List<GameResponse>) {
        view.hideLoading()
        view.onFetchAllGamesSuccess(games)
    }

    private fun onFetchAllGamesError(errorMessage: String) {
        view.hideLoading()
        view.onFetchAllGamesError(errorMessage)
    }

    override fun onItemClick(item: GameResponse) {
        view.onItemClicked(item.title.orEmpty())
    }
}
