package com.example.simpledex.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.simpledex.R
import com.example.simpledex.databinding.FragmentLoginBinding
import com.example.simpledex.databinding.FragmentSearchScreenBinding
import com.example.simpledex.viewmodel.LoginScreenViewModel
import com.example.simpledex.viewmodel.SearchScreenViewmodel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: LoginScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(LoginScreenViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            val success = vm.tryLogin(binding.editEmail.text.toString(),
                binding.editPassword.text.toString())

            if (success) startActivity(Intent(context, MainActivity::class.java))
        }
        binding.textCadastrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }
}