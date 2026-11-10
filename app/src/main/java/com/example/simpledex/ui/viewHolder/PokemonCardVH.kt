package com.example.simpledex.ui.viewHolder

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.simpledex.R
import com.example.simpledex.databinding.PokemonCardBinding
import com.example.simpledex.repository.database.PokemonModel
import com.example.simpledex.repository.pokeapi.PokemonEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class PokemonCardVH(private val binding: PokemonCardBinding) : RecyclerView.ViewHolder(binding.root) {
    private var downloadJob: Job? = null

    fun bindVH(pokemon: PokemonEntity, onItemClick: ((PokemonEntity) -> Unit)? = null) {
        binding.name.text = pokemon.name

        binding.root.setOnClickListener {
            onItemClick?.invoke(pokemon)
        }

        // Reset image to default pokeball placeholder when recycled
        binding.sprite.setImageResource(R.drawable.pok__ball_icon)

        downloadJob?.cancel()

        val url = pokemon.frontDefaultSprite
        if (!url.isNullOrEmpty()) {
            downloadAndSetImage(url)
        }
    }

    fun bindVH(pokemon: PokemonModel, onItemClick: ((PokemonModel) -> Unit)? = null){
        binding.name.text = pokemon.name

        binding.root.setOnClickListener {
            onItemClick?.invoke(pokemon)
        }

        // Reset image to default pokeball placeholder when recycled
        binding.sprite.setImageResource(R.drawable.pok__ball_icon)

        downloadJob?.cancel()

        val url = pokemon.spriteUrl
        if (!url.isNullOrEmpty()) {
            downloadAndSetImage(url)
        }
    }

    private fun downloadAndSetImage(urlString: String) {
        downloadJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                // Create a temporary file in the app's internal cache directory
                val tempFile = File.createTempFile("pokemon_", ".png", binding.root.context.cacheDir)

                // Download from URL and copy stream to the temp file
                URL(urlString).openStream().use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                // Switch back to the Main thread to update the ImageView
                withContext(Dispatchers.Main) {
                    // Set the ImageView source from the temporary file URI
                    binding.sprite.setImageURI(Uri.fromFile(tempFile))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
