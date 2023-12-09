package com.example.apparchitecturedemo.model

import com.example.apparchitecturedemo.service.GameService
import com.example.apparchitecturedemo.service.ServiceManager
import com.example.apparchitecturedemo.service.response.GameResponse
import retrofit2.Call
import retrofit2.Response

class GameModel {
    fun getAllGames(onSuccess: (List<GameResponse>) -> Unit, onError: (String) -> Unit) {
        val service = ServiceManager.getInstance().create(GameService::class.java)

        service.getAllGames().enqueue(object : retrofit2.Callback<List<GameResponse>> {
            override fun onResponse(
                call: Call<List<GameResponse>>,
                response: Response<List<GameResponse>>
            ) {
                val data = response.body()

                when {
                    (response.isSuccessful && (data != null)) -> onSuccess(data)
                    else -> onError(throw Throwable("Something went wrong."))
                }
            }

            override fun onFailure(call: Call<List<GameResponse>>, t: Throwable) {
                onError(t.localizedMessage ?: "Something went wrong.")
            }
        })
    }
}
