package com.example.simpledex.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import com.example.simpledex.repository.database.PokemonModel
import com.example.simpledex.repository.pokeapi.Client
import com.example.simpledex.repository.pokeapi.MultiPokemonSearch
import com.example.simpledex.repository.pokeapi.PokemonEntity
import com.example.simpledex.repository.pokeapi.PokemonService
import com.example.simpledex.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListScreenViewmodel(application: Application): AndroidViewModel(application) {
    private val pokemonService: PokemonService =
        Client.createService(PokemonService::class.java, baseUrl = Constants.POKEAPI_BASE_URL)

    private var lastGot = 0

    private val pokemons = MutableLiveData<List<PokemonEntity>?>()
    private val selectedPokemon = MutableLiveData<PokemonModel?>()

    fun pokemons(): LiveData<List<PokemonEntity>?>{
        return pokemons;
    }

    fun selectedPokemon(): LiveData<PokemonModel?>{
        return selectedPokemon
    }

    fun clearSelectedPokemon(){
        selectedPokemon.value = null
    }

    fun onDataHandled(){
        pokemons.value = null
    }

    fun getFirstPokemon(){
        pokemonService.getNextPokemon(0).enqueue(object : Callback<MultiPokemonSearch> {
            override fun onResponse(
                call: Call<MultiPokemonSearch?>,
                response: Response<MultiPokemonSearch?>
            ) {
                if (response.isSuccessful){
                    val newPokemons = response.body()?.pokemon ?: emptyList()
                    pokemons.value = newPokemons
                    lastGot = 15
                } else {
                    pokemons.value = null
                    Toast.makeText(application, "Não foram encontrados mais pokemons", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MultiPokemonSearch?>, t: Throwable) {
                pokemons.value = null
                Toast.makeText(application, "Erro ao consultar a API", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun selectPokemon(name: String){
        pokemonService.searchPokemon(name).enqueue(object : Callback<PokemonEntity> {
            override fun onResponse(
                call: Call<PokemonEntity?>,
                response: Response<PokemonEntity?>
            ) {
                if (response.isSuccessful){
                    selectedPokemon.value = PokemonModel.fromPokemonEntity(response.body())
                } else {
                    Toast.makeText(application, "Erro ao obter detalhes do pokemon", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PokemonEntity?>, t: Throwable) {
                Toast.makeText(application, "Erro ao consultar a API", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getNextPokemon(){
        pokemonService.getNextPokemon(lastGot).enqueue(object : Callback<MultiPokemonSearch> {
            override fun onResponse(
                call: Call<MultiPokemonSearch?>,
                response: Response<MultiPokemonSearch?>
            ) {
                if (response.isSuccessful){
                    val newPokemons = response.body()?.pokemon ?: emptyList()
                    val currentList = pokemons.value ?: emptyList()
                    pokemons.value = currentList + newPokemons
                    lastGot += newPokemons.count()
                } else {
                    pokemons.value = null
                    Toast.makeText(application, "Não foram encontrados mais pokemons", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MultiPokemonSearch?>, t: Throwable) {
                pokemons.value = null
                Toast.makeText(application, "Erro ao consultar a API", Toast.LENGTH_LONG).show()
            }
        })
    }
}