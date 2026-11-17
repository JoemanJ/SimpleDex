package com.example.simpledex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simpledex.databinding.FragmentFavoritesScreenBinding
import com.example.simpledex.ui.adapter.FavoritePokemonAdapter
import com.example.simpledex.viewmodel.FavoritesScreenViewmodel

class FavoritesScreenFragment : Fragment() {
    private var _binding: FragmentFavoritesScreenBinding? = null
    private val binding get() = _binding!!
    private val adapter = FavoritePokemonAdapter()
    private lateinit var vm: FavoritesScreenViewmodel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFavoritesScreenBinding.inflate(inflater, container, false)

        vm = ViewModelProvider(this).get(FavoritesScreenViewmodel::class.java)

        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onItemClick = { pokemon ->
            vm.selectPokemon(pokemon.name)
        }

        binding.recyclerPokemons.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerPokemons.adapter = adapter

        vm.getFavorites()
    }

    fun setObservers(){
        vm.pokemons().observe(viewLifecycleOwner) { pokemons ->
            if(pokemons != null){
                adapter.updatePokemonList(pokemons)
            } else{
                Toast.makeText(context, "Algo deu errado ao carregar a lista", Toast.LENGTH_LONG).show()
            }
        }

        vm.selectedPokemon().observe(viewLifecycleOwner) {pokemon ->
            if (pokemon != null) {
                val action =
                    FavoritesScreenFragmentDirections.actionFavoritesScreenFragmentToDetailScreenFragment(
                        id = pokemon.id,
                        name = pokemon.name,
                        sprite = pokemon.spriteUrl,
                        hp = pokemon.hp ?: "0",
                        atk = pokemon.atk ?: "0",
                        def = pokemon.def ?: "0",
                        spatk = pokemon.spatk ?: "0",
                        spdef = pokemon.spdef ?: "0",
                        spd = pokemon.spd ?: "0"
                    )
                findNavController().navigate(action)
                vm.clearSelectedPokemon()
            }
        }
    }
}