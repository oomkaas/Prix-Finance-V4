package com.lemi.prix_finance_v3

import User
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class UserCreateDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val totalBalance: Double
)

class Register : AppCompatActivity() {

    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var username: EditText
    private lateinit var networth: EditText
    private lateinit var loginStatus: TextView
    private lateinit var hideConfirmPassword: ImageView
    private lateinit var hidePassword: ImageView
    private lateinit var btnSignUp: Button
    private var isLoginSliderActive = false
    private lateinit var sliderLoginUser: TextView
    private lateinit var sliderNewUser: TextView
    private lateinit var sliderLeft: Drawable
    private lateinit var sliderRight: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firstname = findViewById(R.id.inpUserName)
        lastname = findViewById(R.id.inpUserLastName)
        username = findViewById(R.id.inpUserEmail)
        networth = findViewById(R.id.inpUserNetWorth)
        password = findViewById(R.id.inpUserPassword)
        confirmPassword = findViewById(R.id.inpUserPassConfirm)
        btnSignUp = findViewById(R.id.btnSignUp)
        hidePassword = findViewById(R.id.hidePasswordToggle)
        hideConfirmPassword = findViewById(R.id.hideConfirmPasswordToggle)
        sliderLoginUser = findViewById(R.id.txtViewLoginUser)
        sliderNewUser = findViewById(R.id.txtViewNewUser)

        setupPasswordVisibilityToggle(password, hidePassword)
        setupPasswordVisibilityToggle(confirmPassword, hideConfirmPassword)

        btnSignUp.setOnClickListener {
            verifyInput(
                firstname.text.toString(),
                lastname.text.toString(),
                networth.text.toString(),
                username.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }

        sliderLoginUser.setOnClickListener {
            toggleSlider(true)
            startActivity(Intent(this, Login::class.java))
        }

        sliderNewUser.setOnClickListener {
            toggleSlider(false)
        }
    }

    private fun toggleSlider(isLogin: Boolean) {
        isLoginSliderActive = isLogin

        val loginColor = if (isLogin) Color.parseColor("#FF0000") else Color.GRAY
        val newUserColor = if (!isLogin) Color.parseColor("#FF0000") else Color.GRAY

        sliderLoginUser.setTextColor(loginColor)
        sliderNewUser.setTextColor(newUserColor)

        val loginBackground = if (isLogin) R.drawable.bg_activeloginslider else R.drawable.bg_loginslider
        val newUserBackground = if (!isLogin) R.drawable.bg_activeregisterslider else R.drawable.bg_registerslider

        sliderLoginUser.setBackgroundResource(loginBackground)
        sliderNewUser.setBackgroundResource(newUserBackground)
    }

    private fun verifyInput(name: String, surname: String, netWorth: String, username: String, password: String, confirmedPass: String) {
        try {
            if (name.isNotEmpty() && surname.isNotEmpty() && netWorth.isNotEmpty()) {
                if (username.isNotEmpty() && username.contains('@')) {
                    if (password.isNotEmpty() && confirmedPass.isNotEmpty() && password.equals(confirmedPass, ignoreCase = true)) {
                        registerUser(name, surname, netWorth.toDouble(), username, password)
                        Toast.makeText(this, "Registration successful. \n You will be redirected to the login promptly", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Please fill out all Password fields correctly: \nClick eye icon to see passwords entered, Verify that they match.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Please fill out Email field correctly: \nEnsure that you include an '@' symbol.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "There are missing fields.\nPlease Fill Out ALL User Details Correctly.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error processing input: ${e.message}", Toast.LENGTH_LONG).show()
            verifyInput(name, surname, username, password, netWorth, confirmedPass)
        }
    }

    private fun registerUser(firstName: String, lastName: String, totalBalance: Double, email: String, password: String) {
        val newUser = UserCreateDto(firstName, lastName, email, password, totalBalance)
        RetrofitInstance.api.createUser(newUser).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Register, "User registered successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Register, Login::class.java))
                    finish()
                } else {
                    Toast.makeText(this@Register, "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@Register, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupPasswordVisibilityToggle(editText: EditText, imageView: ImageView) {
        imageView.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)

            val selection = editText.selectionEnd
            editText.setSelection(selection)

            if (editText.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                editText.inputType = InputType.TYPE_CLASS_TEXT
                imageView.setImageResource(R.drawable.ic_viewpassword)
            } else {
                editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageView.setImageResource(R.drawable.ic_hidepassword)
            }

            editText.setSelection(selection)
        }
    }
}