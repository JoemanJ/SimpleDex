package com.example.simpledex.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import com.example.simpledex.repository.database.MyDatabase
import com.example.simpledex.repository.database.PokemonDao
import com.example.simpledex.repository.database.PokemonModel
import com.example.simpledex.repository.pokeapi.Client
import com.example.simpledex.repository.pokeapi.PokemonEntity
import com.example.simpledex.repository.pokeapi.PokemonService
import com.example.simpledex.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesScreenViewmodel(application: Application): AndroidViewModel(application) {
    private val pokemonService: PokemonService =
        Client.createService(PokemonService::class.java, baseUrl = Constants.POKEAPI_BASE_URL)
    private val pokemons = MutableLiveData<List<PokemonModel>?>()
    private val selectedPokemon = MutableLiveData<PokemonEntity?>()

    private val DAO: PokemonDao = MyDatabase.getDatabase(application).PokemonDao()

    fun pokemons(): LiveData<List<PokemonModel>?>{
        return pokemons
    }

    fun selectedPokemon(): LiveData<PokemonEntity?>{
        return selectedPokemon
    }

    fun clearSelectedPokemon(){
        selectedPokemon.value = null
    }

    fun selectPokemon(name: String){
        pokemonService.searchPokemon(name).enqueue(object : Callback<PokemonEntity> {
            override fun onResponse(
                call: Call<PokemonEntity?>,
                response: Response<PokemonEntity?>
            ) {
                if (response.isSuccessful){
                    selectedPokemon.value = response.body()
                } else {
                    Toast.makeText(application, "Erro ao obter detalhes do pokemon", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PokemonEntity?>, t: Throwable) {
                Toast.makeText(application, "Erro ao consultar a API", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getFavorites(){
        pokemons.value = DAO.getSavedPokemon()
    }
}