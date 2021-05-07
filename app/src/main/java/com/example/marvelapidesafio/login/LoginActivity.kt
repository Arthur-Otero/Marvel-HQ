package com.example.marvelapidesafio.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.marvelapidesafio.R
import com.example.marvelapidesafio.comics.ComicActivity
import com.example.marvelapidesafio.login.createAccount.CreateAccountActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private val fieldEmail by lazy { findViewById<TextInputEditText>(R.id.fieldEmail) }
    private val fieldPassword by lazy { findViewById<TextInputEditText>(R.id.fieldPassword) }
    private val fieldError by lazy { findViewById<TextView>(R.id.errorMessage) }
    private val googleButton by lazy { findViewById<com.google.android.gms.common.SignInButton>(R.id.googleButton) }

    lateinit var viewModel: LoginViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager:CallbackManager

    private val facebookSignIn = LoginManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        validateFields()
        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        googleSignInConfiguration()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode,resultCode,data)

        if (requestCode == 200) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: Exception) {
                Toast.makeText(this,"Failed to create account",Toast.LENGTH_LONG).show()
            } catch (e: ApiException) {
                Toast.makeText(this,"Failed to create account",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun facebookSignInConfiguration() {
        facebookSignIn.logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
        facebookSignIn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.let { firebaseAuthWithFacebook(it.accessToken) }
            }

            override fun onCancel() {
                Log.d("facebook", "facebook:onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d("facebook", "facebook:onError", error)
            }

        })
    }

    private fun firebaseAuthWithFacebook(token:AccessToken){
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseSignInWithCredential(credential)
    }

    private fun firebaseSignInWithCredential(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this,ComicActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Account successfully created", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to create account", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun googleSignInConfiguration() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        googleButton.setOnClickListener {
            googleSignIn()
        }
    }

    private fun googleSignIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,200)
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        firebaseSignInWithCredential(credential)
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

    fun login(view:View){
        val email = fieldEmail.text.toString()
        val password = fieldPassword.text.toString()

        viewModel.validateFields(email,password)
    }

    fun loginFacebook(view: View){
        facebookSignInConfiguration()
    }

    fun createAccount(view: View){
        val intent = Intent(this,CreateAccountActivity::class.java)
        startActivity(intent)
    }
}