package com.lemi.prix_finance_v3

import User
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private var isLoginSliderActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Finding all present views...
        btnLogin = findViewById(R.id.btnLogin)
        btnLoginBiometric = findViewById(R.id.btnLoginBiometrics)
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
        RetrofitInstance.api.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                lifecycleScope.launch(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val users = response.body()
                        val user = users?.find { it.email == username && it.password == enteredPassword }
                        if (user != null) {
                            MainActivity.Global.userId = user.userId
                            successfulLogin(user.userId.toString(), user.firstName)
                        } else {
                            showLoginError("Invalid email or password")
                        }
                    } else {
                        showLoginError("Failed to retrieve users")
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                lifecycleScope.launch(Dispatchers.Main) {
                    showLoginError("Error: ${t.message}")
                }
            }
        })
    }

    private fun successfulLogin(userUID: String, userName: String) {
        // Save user data in SharedPreferences
        val prefs = this@Login.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        prefs.edit().putString("user_uid", userUID).apply()
        prefs.edit().putString("user_name", userName).apply()

        // Start MainActivity
        this@Login.startActivity(Intent(this@Login, MainActivity::class.java))
        this@Login.finish()
    }

    private fun showLoginError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setupPasswordVisibilityToggle(editText: EditText?, imageView: ImageView?) {
        imageView?.setOnClickListener {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
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