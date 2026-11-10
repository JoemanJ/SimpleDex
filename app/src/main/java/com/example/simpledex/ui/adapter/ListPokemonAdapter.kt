package com.example.simpledex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpledex.databinding.PokemonCardBinding
import com.example.simpledex.repository.pokeapi.PokemonEntity
import com.example.simpledex.ui.viewHolder.PokemonCardVH

class ListPokemonAdapter(
    var onItemClick: ((PokemonEntity) -> Unit)? = null
): RecyclerView.Adapter<PokemonCardVH>() {
    private var pokemons: List<PokemonEntity> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonCardVH {
        val item = PokemonCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PokemonCardVH(item)
    }

    override fun onBindViewHolder(
        holder: PokemonCardVH,
        position: Int
    ) {
        holder.bindVH(pokemons[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return pokemons.count()
    }

    fun updatePokemonList(list: List<PokemonEntity>){
        pokemons = list
        notifyDataSetChanged()
    }
}