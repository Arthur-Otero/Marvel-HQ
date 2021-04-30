package com.example.marvelapidesafio.login.createAccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.marvelapidesafio.R
import com.example.marvelapidesafio.comics.ComicActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class CreateAccountActivity : AppCompatActivity() {
    private val fieldName by lazy { findViewById<TextInputEditText>(R.id.fieldName) }
    private val fieldEmail by lazy { findViewById<TextInputEditText>(R.id.fieldEmailCreate) }
    private val fieldPassword by lazy { findViewById<TextInputEditText>(R.id.fieldPasswordCreate) }
    private val button by lazy { findViewById<Button>(R.id.save) }
    private val backButton by lazy { findViewById<ImageView>(R.id.back) }
    private val fieldError by lazy { findViewById<TextView>(R.id.fieldError) }

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var viewModel: CreateAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        viewModel = ViewModelProviders.of(this).get(CreateAccountViewModel::class.java)

        validateField()

        button.setOnClickListener {
            createAccount()
        }

        backButton.setOnClickListener {
            onBackPressed()
        }

        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun validateField() {
        viewModel.nameValidate.observe(this){
            if (it)
                fieldName.error = null
            else
                fieldName.error = "Required Field"

            navigate()
        }

        viewModel.emailLiveData.observe(this){
            if (it)
                fieldEmail.error = null
            else if(!it && !fieldEmail.text.isNullOrBlank())
                fieldEmail.error = "Field out of format"
            else
                fieldEmail.error = "Required Field"

            navigate()
        }

        viewModel.passwordValidate.observe(this){
            if (it)
                fieldPassword.error = null
            else if(!it && !fieldPassword.text.isNullOrBlank())
                fieldEmail.error = "Field out of format"
            else
                fieldPassword.error = "Required Field"

            navigate()
        }
    }

    private fun navigate() {
        if (viewModel.emailLiveData.value == true && viewModel.nameValidate.value == true && viewModel.passwordValidate.value == true){
            createUserWithEmailPass(fieldEmail.text.toString(),fieldPassword.text.toString())
        }
    }

    private fun createAccount() {
        val name = fieldName.text.toString()
        val email = fieldEmail.text.toString()
        val password = fieldPassword.text.toString()

        viewModel.validateFields(name,email, password)
    }

    private fun createUserWithEmailPass(email:String,pass:String){
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task->
            if (task.isSuccessful){
                val user = firebaseAuth.currentUser
                val profileUpdate = userProfileChangeRequest {
                    displayName = fieldName.text.toString()
                }
                user!!.updateProfile(profileUpdate)
                val intent = Intent(this,ComicActivity::class.java)
                startActivity(intent)
                Toast.makeText(this,"Account successfully created",Toast.LENGTH_LONG).show()
            }else{
                Log.d("error", task.exception?.message!!)
                fieldError.visibility = View.VISIBLE
                fieldError.text = task.exception?.message!!
            }
        }
    }
}
