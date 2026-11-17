package com.example.simpledex.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import com.example.simpledex.R
import com.example.simpledex.repository.database.PokemonModel
import com.example.simpledex.repository.pokeapi.Client
import com.example.simpledex.repository.pokeapi.PokemonEntity
import com.example.simpledex.repository.pokeapi.PokemonService
import com.example.simpledex.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchScreenViewmodel(application: Application) : AndroidViewModel(application) {
    private val pokemonService: PokemonService =
        Client.createService(PokemonService::class.java, baseUrl = Constants.POKEAPI_BASE_URL)

    private val pokemon = MutableLiveData<PokemonModel?>()

    fun pokemon(): LiveData<PokemonModel?>{
        return pokemon;
    }

    fun onDataHandled(){
        pokemon.value = null
    }

    fun searchPokemon(name: String){
        pokemonService.searchPokemon(name).enqueue(object : Callback<PokemonEntity> {
            override fun onResponse(
                call: Call<PokemonEntity?>,
                response: Response<PokemonEntity?>
            ) {
                if (response.isSuccessful){
                    pokemon.value = PokemonModel.fromPokemonEntity(response.body())
                } else {
                    pokemon.value = null
                    Toast.makeText(application, "Pokemon não encontrado", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PokemonEntity?>, t: Throwable) {
                pokemon.value = null
                Toast.makeText(application, "Erro ao consultar a API", Toast.LENGTH_LONG).show()
            }
        })
    }
}