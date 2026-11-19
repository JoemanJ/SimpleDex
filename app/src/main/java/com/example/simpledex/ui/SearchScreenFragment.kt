package com.example.simpledex.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.application
import androidx.navigation.fragment.findNavController
import com.example.simpledex.R
import com.example.simpledex.databinding.FragmentSearchScreenBinding
import com.example.simpledex.viewmodel.SearchScreenViewmodel

class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {
    private var _binding: FragmentSearchScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: SearchScreenViewmodel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchScreenBinding.inflate(inflater, container, false)

        binding.buttonSearch.setOnClickListener {
            vm.searchPokemon(binding.editSearch.text.toString())
        }

        vm = ViewModelProvider(this).get(SearchScreenViewmodel::class.java)

        setObserver()
        return binding.root
    }
    private fun setObserver(){
        vm.pokemon().observe(viewLifecycleOwner) { pokemon ->
            if (pokemon != null){
                val action = SearchScreenFragmentDirections.actionSearchScreenFragmentToDetailScreenFragment(
                    id = pokemon.id.toInt(),
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
                vm.onDataHandled()
            }
        }
    }
}