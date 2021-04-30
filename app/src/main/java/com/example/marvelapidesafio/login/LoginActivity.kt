package com.example.marvelapidesafio.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.marvelapidesafio.R
import com.example.marvelapidesafio.comics.ComicActivity
import com.example.marvelapidesafio.login.createAccount.CreateAccountActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val fieldEmail by lazy { findViewById<TextInputEditText>(R.id.fieldEmail) }
    private val fieldPassword by lazy { findViewById<TextInputEditText>(R.id.fieldPassword) }
    private val fieldError by lazy { findViewById<TextView>(R.id.errorMessage) }

    lateinit var viewModel: LoginViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        validateFields()
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun validateFields() {
        viewModel.emailValidate.observe(this){
            if (it)
                fieldEmail.error = null
            else if(!it && !fieldEmail.text.isNullOrBlank())
                fieldEmail.error = "Field out of format"
            else
                fieldEmail.error = "Required field"

            navigation()
        }
        viewModel.passwordValidate.observe(this){
            if (it)
                fieldPassword.error = null
            else
                fieldPassword.error = "Required field"

            navigation()
        }
    }

    private fun navigation(){
        if (viewModel.passwordValidate.value == true && viewModel.emailValidate.value == true){
            sigInFirebase(fieldEmail.text.toString(),fieldPassword.text.toString())
        }
    }

    fun login(view:View){
        val email = fieldEmail.text.toString()
        val password = fieldPassword.text.toString()

        viewModel.validateFields(email,password)
    }

    fun createAccount(view: View){
        val intent = Intent(this,CreateAccountActivity::class.java)
        startActivity(intent)
    }

    private fun sigInFirebase(email:String,password:String){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                val intent = Intent(this,ComicActivity::class.java)
                startActivity(intent)
            }else{
                Log.d("error", task.exception?.message!!)
                fieldError.visibility = VISIBLE
                fieldError.text = task.exception?.message!!
            }
        }
    }
}