package com.example.simpledex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.viewbinding.ViewBinding
import com.example.simpledex.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterScreenViewModel(application: Application): AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()

    private val registerSuccess = MutableLiveData<Boolean>(false)

    fun registerSuccess(): LiveData<Boolean>{
        return registerSuccess
    }

    fun tryRegister(email: String, password1: String, password2: String){
        if (password1 != password2){
            Utils.prinToast("Senhas diferentes.", application)
            return
        }

        auth.createUserWithEmailAndPassword(email, password1).addOnFailureListener {
            if (it is FirebaseAuthUserCollisionException){
                Utils.prinToast("Email já cadastrado.", application)
            } else {
                Utils.prinToast("Erro ao cadastrar usuário.", application)
            }
        }.addOnSuccessListener {
            registerSuccess.value = true
        }
    }
}