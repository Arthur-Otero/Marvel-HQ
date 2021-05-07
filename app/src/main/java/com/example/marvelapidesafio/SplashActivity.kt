package com.example.marvelapidesafio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.marvelapidesafio.comics.ComicActivity
import com.example.marvelapidesafio.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()
        Handler().postDelayed({
            checkLogin()
        }, 3000)
    }

    private fun checkLogin(){
        Log.d("Teste","Teste")
        val user = firebaseAuth.currentUser
        if (user != null){
            val userName =user.displayName!!
            Log.d("Usuario",userName)
            val intent = Intent(this,ComicActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}