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
import com.example.simpledex.databinding.FragmentListScreenBinding
import com.example.simpledex.ui.adapter.ListPokemonAdapter
import com.example.simpledex.viewmodel.ListScreenViewmodel

class ListScreenFragment : Fragment() {
    private var _binding: FragmentListScreenBinding? = null
    private val binding get() = _binding!!

    private val adapter = ListPokemonAdapter()

    private lateinit var vm: ListScreenViewmodel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentListScreenBinding.inflate(inflater, container, false)

        vm = ViewModelProvider(this).get(ListScreenViewmodel::class.java)

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
        binding.buttonNext.setOnClickListener { vm.getNextPokemon() }

        vm.getFirstPokemon()
    }

    fun setObservers(){
        vm.pokemons().observe(viewLifecycleOwner) { pokemons ->
            if(pokemons != null){
                adapter.updatePokemonList(pokemons)
            } else{
                Toast.makeText(context, "Algo deu errado ao carregar a lista", Toast.LENGTH_LONG).show()
            }
        }

        vm.selectedPokemon().observe(viewLifecycleOwner) { pokemon ->
            if (pokemon != null) {
                val action = ListScreenFragmentDirections.actionListScreenFragmentToDetailScreenFragment(
                    id = pokemon.id,
                    name = pokemon.name,
                    sprite = pokemon.sprites?.frontDefault ?: pokemon.frontDefaultSprite,
                    hp = pokemon.hp?.toString() ?: "0",
                    atk = pokemon.atk?.toString() ?: "0",
                    def = pokemon.def?.toString() ?: "0",
                    spatk = pokemon.spatk?.toString() ?: "0",
                    spdef = pokemon.spdef?.toString() ?: "0",
                    spd = pokemon.spd?.toString() ?: "0"
                )
                findNavController().navigate(action)
                vm.clearSelectedPokemon()
            }
        }
    }
}