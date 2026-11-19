package com.example.simpledex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import com.example.simpledex.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginScreenViewModel(application: Application): AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()

    private var loginSuccess = MutableLiveData<Boolean>(false)

    fun loginSuccess(): LiveData<Boolean>{
        return loginSuccess
    }

    fun tryLogin(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            if (it is FirebaseAuthInvalidCredentialsException){
                Utils.prinToast("Email ou senha inválidos.", application)
            } else {
                Utils.prinToast("Erro ao fazer login.", application)
            }
        }.addOnSuccessListener {
            loginSuccess.value = true
        }
    }
}