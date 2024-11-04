package com.lemi.prix_finance_v3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.text.InputType.*
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnLoginBiometric: Button
    private lateinit var btnLoginSSO: Button
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var txtViewNewUser: TextView
    private lateinit var hidePassword: ImageView
    private lateinit var viewPassword: ImageView
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        //Initialising Firebase
        FirebaseApp.initializeApp(this)

        // Initializing Firebase Realtime Database reference
        dbRef = FirebaseDatabase.getInstance().reference.child("users")

        // Finding all present views...
        btnLogin = findViewById(R.id.btnLogin)
        btnLoginBiometric = findViewById(R.id.btnLoginBiometrics)
        btnLoginSSO = findViewById(R.id.btnLoginSSO)
        txtViewNewUser = findViewById(R.id.txtViewNewUser)
        password = findViewById(R.id.inpLoginPassword)
        username = findViewById(R.id.inpLoginEmail)
        hidePassword = findViewById(R.id.hideLoginPasswordToggle)

        // setting the password initial state
        password.inputType = TYPE_TEXT_VARIATION_PASSWORD
        hidePassword.setImageResource(R.drawable.ic_hidepassword)

        // calling the toggle setup method
        setupPasswordVisibilityToggle(password, hidePassword)

        //action on button click
        btnLogin.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()

            verifyLoginCredentials(enteredUsername, enteredPassword)
        }


        btnLoginBiometric.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnLoginSSO.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        txtViewNewUser.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

    }

    private fun verifyLoginCredentials(username: String, enteredPassword: String) {
        if (username.isEmpty() || enteredPassword.isEmpty()) {
            showLoginError("Please enter both username and password")
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val query = dbRef.orderByChild("userName").equalTo(username)
                val snapshot = query.get().await()

                if (snapshot.exists()) {
                    val userUID = snapshot.children.first().key
                    val userData = snapshot.children.first().getValue(userLoginCredentials::class.java)

                    if (userData != null && userData.password == enteredPassword) {
                        launch(Dispatchers.Main) {
                            successfulLogin(userData.userUID, userData.userName)
                        }
                    } else {
                        launch(Dispatchers.Main) {
                            showLoginError("Incorrect password")
                        }
                    }
                } else {
                    launch(Dispatchers.Main) {
                        showLoginError("User not found")
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    showLoginError("An error occurred: ${e.message}")
                }
            }
        }
    }

    private fun successfulLogin(userUID: String, userName: String) {
        // Save user data in SharedPreferences
        val prefs = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        prefs.edit().putString("user_uid", userUID).apply()
        prefs.edit().putString("user_name", userName).apply()

        // Start MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showLoginError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setupPasswordVisibilityToggle(editText: EditText?, imageView: ImageView?) {
        imageView?.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(editText?.windowToken ?: return@setOnClickListener, 0)

            val selection = editText.selectionEnd ?: 0
            editText.setSelection(selection)

            if (editText.inputType == TYPE_TEXT_VARIATION_PASSWORD) {
                imageView.setImageResource(R.drawable.ic_viewpassword)
                editText.inputType = TYPE_CLASS_TEXT
            }
            else if (editText.inputType == TYPE_CLASS_TEXT){
                imageView.setImageResource(R.drawable.ic_hidepassword)
                editText.inputType = TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }

}