package com.lemi.prix_finance_v3

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnLoginBiometric: Button
    private lateinit var btnLoginSSO: Button
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var sliderAddUser: TextView
    private lateinit var sliderLoginUser: TextView
    private lateinit var hidePassword: ImageView
    private lateinit var viewPassword: ImageView
    private lateinit var dbRef: DatabaseReference
    private var isLoginSliderActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initializing Firebase
        FirebaseApp.initializeApp(this)
        dbRef = FirebaseDatabase.getInstance().reference.child("users")

        // Finding all present views...
        btnLogin = findViewById(R.id.btnLogin)
        btnLoginBiometric = findViewById(R.id.btnLoginBiometrics) // Initialize here
        btnLoginSSO = findViewById(R.id.btnLoginSSO)
        sliderAddUser = findViewById(R.id.txtViewNewUser)
        sliderLoginUser = findViewById(R.id.txtViewLoginUser)
        password = findViewById(R.id.inpLoginPassword)
        username = findViewById(R.id.inpLoginEmail)
        hidePassword = findViewById(R.id.hideLoginPasswordToggle)

        // Setting the password initial state
        password.inputType = TYPE_TEXT_VARIATION_PASSWORD
        hidePassword.setImageResource(R.drawable.ic_hidepassword)

        // Calling the toggle setup method
        setupPasswordVisibilityToggle(password, hidePassword)

        // Action on button click for normal login
        btnLogin.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()
            verifyLoginCredentials(enteredUsername, enteredPassword)
        }

        // Set up the biometric login button
        btnLoginBiometric.setOnClickListener {
            authenticateUser()
        }

        // SSO button click
        btnLoginSSO.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        sliderLoginUser.setOnClickListener {
            toggleSlider(true)
        }

        sliderAddUser.setOnClickListener {
            toggleSlider(false)
            startActivity(Intent(this, Register::class.java))
        }
    }

    private fun authenticateUser() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                showLoginError("Authentication error: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                // Successful authentication, navigate to the main activity
                successfulLogin("userUID", "userName") // Replace with actual user data as needed
            }

            override fun onAuthenticationFailed() {
                showLoginError("Authentication failed")
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    // Method that handles the color change between the sliders created
    private fun toggleSlider(isLogin: Boolean) {
        isLoginSliderActive = isLogin

        val loginColor = if (isLogin) Color.parseColor("#FF0000") else Color.GRAY
        val newUserColor = if (!isLogin) Color.parseColor("#FF0000") else Color.GRAY

        sliderLoginUser.setTextColor(loginColor)
        sliderAddUser.setTextColor(newUserColor)

        val loginBackground = if (isLogin) R.drawable.bg_activeloginslider else R.drawable.bg_loginslider
        val newUserBackground = if (!isLogin) R.drawable.bg_activeregisterslider else R.drawable.bg_registerslider

        sliderLoginUser.setBackgroundResource(loginBackground)
        sliderAddUser.setBackgroundResource(newUserBackground)
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
            } else if (editText.inputType == TYPE_CLASS_TEXT) {
                imageView.setImageResource(R.drawable.ic_hidepassword)
                editText.inputType = TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }
}