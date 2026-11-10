package com.example.simpledex.repository.pokeapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon/{name}")
    fun searchPokemon(@Path("name") name: String): Call<PokemonEntity>

    @GET("pokemon?limit=15")
    fun getNextPokemon(@Query("offset") offset: Int): Call<MultiPokemonSearch>
}