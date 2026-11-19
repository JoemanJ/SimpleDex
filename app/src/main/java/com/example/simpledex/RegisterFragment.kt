package com.example.simpledex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simpledex.databinding.FragmentLoginBinding
import com.example.simpledex.databinding.FragmentRegisterBinding
import com.example.simpledex.ui.MainActivity
import com.example.simpledex.viewmodel.LoginScreenViewModel
import com.example.simpledex.viewmodel.RegisterScreenViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: RegisterScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(RegisterScreenViewModel::class.java)

        binding.buttonRegister.setOnClickListener {
                vm.tryRegister(binding.editEmail.text.toString(),
                binding.editPassword1.text.toString(),
                binding.editPassword2.text.toString())

//            if (success) startActivity(Intent(context, MainActivity::class.java))
        }

        setObservers()

        return binding.root
    }

    fun setObservers(){
        vm.registerSuccess().observe(viewLifecycleOwner, {success ->
            if(success) {
                startActivity(Intent(context, MainActivity::class.java))
                requireActivity().finish()
            }
        })
    }
}