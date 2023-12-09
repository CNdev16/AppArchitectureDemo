package com.example.apparchitecturedemo.service

import com.example.apparchitecturedemo.service.response.GameResponse
import retrofit2.Call
import retrofit2.http.GET

interface GameService {

    @GET("games")
    fun getAllGames(): Call<List<GameResponse>>
}
