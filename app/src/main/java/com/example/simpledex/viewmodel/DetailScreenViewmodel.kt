package com.example.simpledex.viewmodel

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.simpledex.repository.database.FirebaseUtils
import com.example.simpledex.repository.database.PokemonModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

class DetailScreenViewmodel(application: Application) : AndroidViewModel(application) {
//    private var DAO: PokemonDao = MyDatabase.getDatabase(application).PokemonDao()
    private var db = FirebaseFirestore.getInstance()

    private var pokemon = MutableLiveData<PokemonModel?>()
    private var favorited = MutableLiveData<Boolean>(false)
    private val imageUri = MutableLiveData<Uri?>()

    fun pokemon(): LiveData<PokemonModel?> {
        return pokemon
    }

    fun setPokemon(p: PokemonModel){
        pokemon.value = p
//        favorited.value = DAO.getPokemonById(pokemon.value?.id!!).count() == 1
        db.collection("pokemons").document(p.id.toString()).get().addOnSuccessListener {
            favorited.value = true
        }.addOnFailureListener {
            favorited.value = false
        }
    }

    fun favorited(): LiveData<Boolean> {
        return favorited
    }

    fun imageUri(): LiveData<Uri?> {
        return imageUri
    }

    fun downloadAndSetImage(urlString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Create a temporary file in the app's internal cache directory
                val tempFile = File.createTempFile("pokemon_", ".png", getApplication<Application>().cacheDir)

                // Download from URL and copy stream to the temp file
                URL(urlString).openStream().use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                // Post the Uri to LiveData
                imageUri.postValue(Uri.fromFile(tempFile))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavoritePokemon() {
        val currentPokemon = pokemon.value
        if (currentPokemon != null) {
            val isFav = favorited.value ?: false
            if (!isFav) {
//                DAO.insert(currentPokemon)
                FirebaseUtils.insertPokemon(pokemon.value!!, db)
                Toast.makeText(application, "${pokemon.value?.name} adicionado aos favoritos.", Toast.LENGTH_SHORT).show()
                favorited.value = true
            } else {
//                DAO.delete(currentPokemon)
                FirebaseUtils.removePokemon(pokemon.value?.id.toString(), db)
                Toast.makeText(application, "${pokemon.value?.name} removido dos favoritos.", Toast.LENGTH_SHORT).show()
                favorited.value = false
            }
        } else {
            throw IllegalArgumentException()
        }
    }
}
