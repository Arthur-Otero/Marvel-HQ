package com.example.marvelapidesafio.login

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelapidesafio.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginViewModel:ViewModel() {
    val emailValidate: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val passwordValidate: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun validateFields(email: String, password: String){
        when{
            validateEmail(email) && password.isBlank()->{
                emailValidate.postValue(false)
                passwordValidate.postValue(false)
            }
            validateEmail(email)->{
                emailValidate.postValue(false)
                passwordValidate.postValue(true)
            }
            password.isBlank()->{
                emailValidate.postValue(true)
                passwordValidate.postValue(false)
            }
            else->{
                emailValidate.postValue(true)
                passwordValidate.postValue(true)
            }
        }
    }
}

fun validateEmail(email: String):Boolean{
    return email.isBlank() || !email.contains('@') || email.length < 8
}