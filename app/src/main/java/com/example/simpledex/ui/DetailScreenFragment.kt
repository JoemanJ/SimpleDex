package com.example.simpledex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simpledex.R
import com.example.simpledex.databinding.FragmentDetailScreenBinding
import com.example.simpledex.repository.database.PokemonModel
import com.example.simpledex.viewmodel.DetailScreenViewmodel
import com.google.android.material.snackbar.Snackbar

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
            id = args.id.toString()
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
        binding.name.text = args.name
        binding.textHpVar.text = args.hp
        binding.textAtkVar.text = args.atk
        binding.textDefVar.text = args.def
        binding.textSpatkVar.text = args.spatk
        binding.textSpdefVar.text = args.spdef
        binding.textSpdVar.text = args.spd

        binding.imageFavoriteIcon.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirme")
                .setMessage("Tem certeza que deseja fazer isso?")
                .setPositiveButton("Sim", { dialog, bt ->
                    vm.toggleFavoritePokemon()
                })
                .setNegativeButton("Não", {dialog, bt -> {}})
                .create()
                .show()
        }
        binding.imageBackIcon.setOnClickListener {

            findNavController().popBackStack()
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
                val snack = Snackbar.make(binding.root, "Removido dos favoritos", Snackbar.LENGTH_SHORT)
                snack.setAction("Desfazer", {vm.toggleFavoritePokemon()})
                snack.show()
            } else {
                binding.imageFavoriteIcon.setImageResource(R.drawable.star_sharp_empty)
                val snack = Snackbar.make(binding.root, "Adicionado aos favoritos", Snackbar.LENGTH_SHORT)
                snack.setAction("Desfazer", {vm.toggleFavoritePokemon()})
                snack.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
