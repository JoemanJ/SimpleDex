package com.example.simpledex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.simpledex.R
import com.example.simpledex.databinding.FragmentDetailScreenBinding
import com.example.simpledex.repository.database.PokemonModel
import com.example.simpledex.viewmodel.DetailScreenViewmodel

class DetailScreenFragment : Fragment() {
    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var vm: DetailScreenViewmodel

    // Retrieve the Safe Args passed from SearchScreenFragment
    private val args: DetailScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailScreenBinding.inflate(inflater, container, false)

        vm = ViewModelProvider(this).get(DetailScreenViewmodel::class.java)

        val pokemon = PokemonModel().apply {
            id = args.id ?: 0
            name = args.name ?: ""
            spriteUrl = args.sprite ?: ""
            hp = args.hp ?: ""
            atk = args.atk ?: ""
            def = args.def ?: ""
            spatk = args.spatk ?: ""
            spdef = args.spdef ?: ""
            spd = args.spd ?: ""
        }
        vm.setPokemon(pokemon)

        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Download and set the sprite image if the URL exists
        val spriteUrl = args.sprite
        if (!spriteUrl.isNullOrEmpty()) {
            vm.downloadAndSetImage(spriteUrl)
        }

        // 2. Populate the stats text views
        binding.textHpVar.text = args.hp
        binding.textAtkVar.text = args.atk
        binding.textDefVar.text = args.def
        binding.textSpatkVar.text = args.spatk
        binding.textSpdefVar.text = args.spdef
        binding.textSpdVar.text = args.spd
        binding.imageFavoriteIcon.setOnClickListener {
            vm.toggleFavoritePokemon()
        }
    }

    private fun setObservers() {
        vm.imageUri().observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.imagePokemon.setImageURI(uri)
            }
        }
        vm.favorited().observe(viewLifecycleOwner) { favorited ->
            if (favorited){
                binding.imageFavoriteIcon.setImageResource(R.drawable.star_sharp_filled)
            } else {
                binding.imageFavoriteIcon.setImageResource(R.drawable.star_sharp_empty)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
