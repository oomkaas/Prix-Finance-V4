package com.lemi.prix_finance_v3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class Register : AppCompatActivity() {

    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var username: EditText
    private lateinit var loginStatus: TextView
    private lateinit var hideConfirmPassword: ImageView
    private lateinit var hidePassword: ImageView
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
        password = findViewById(R.id.inpUserPassword)
        confirmPassword = findViewById(R.id.inpUserPassConfirm)
        btnSignUp = findViewById(R.id.btnSignUp)
        hidePassword = findViewById(R.id.hidePasswordToggle)
        hideConfirmPassword = findViewById(R.id.hideConfirmPasswordToggle)


        setupPasswordVisibilityToggle(password, hidePassword)
        setupPasswordVisibilityToggle(confirmPassword, hideConfirmPassword)

        btnSignUp.setOnClickListener {
            verifyInput(
                firstname.text.toString(),
                lastname.text.toString(),
                username.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }
    }

    //method that verifies user input before submitting to store in noSQL db: Firebase
    private fun verifyInput(name: String, surname: String, username: String, password: String, confirmedPass: String) {
        //try and catch so user input does not crash app when errors occur, catch any Exceptions and repeat
        try {
            //if statement to check if the name and surname input is not empty,
            // if it is then it will need the user to add it by displaying error in else
            if (name.isNotEmpty() && surname.isNotEmpty()){
                //if statement to check if the username input is not empty and contains an @ symbol,
                // if it is then it will need the user to fix it by displaying error in else
                if( username.isNotEmpty() && username.contains('@')){
                    //if statement to check if the password and confirmedPassword input is not empty and that the two entries equal,
                    // if it is empty and not equal then it will need the user to fix it by displaying error in else
                    if(password.isNotEmpty() && confirmedPass.isNotEmpty() &&  password.equals(confirmedPass, ignoreCase = true) ){
                        //if all of the above is true, then  the user will be registered successfully
                        registerUser(username, password)
                        Toast.makeText(this, "Registration successful. \n Your Will Be Redirected To The Login Promptly", Toast.LENGTH_LONG).show()

                    }
                    else{
                        Toast.makeText(this, "Please fill out all Password fields correctly: " +
                                "nClick eye icon to see passwords entered, Verify that they match.", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(this, "Please fill out Email field correctly: " +
                            "\nEnsure that you include an '@' symbol.", Toast.LENGTH_LONG).show()
                }
            }
            else {
                Toast.makeText(this, "There are missing fields.\nPlease Fill Out ALL User Details Correctly. " +
                        "\n\bENSURE THAT: \n" , Toast.LENGTH_LONG).show()
            }
        }
        catch (e: Exception) {
            Toast.makeText(this, "Error processing input: ${e.message}", Toast.LENGTH_LONG).show()
            verifyInput(name, surname, username, password, confirmedPass)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerUser(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    saveAdditionalUserInfo(email)
                    loginStatus.setOnClickListener{
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(this, "Registration failed: \nCheck all Input fields and try again. \nError: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //saving the user last name and first name to noSQL database
    private fun saveAdditionalUserInfo(email: String) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName("$firstname $lastname")
                .build()

            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "User profile updated successfully", Toast.LENGTH_SHORT).show()
                    }
                }
        }
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
            }
            else{
                editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageView.setImageResource(R.drawable.ic_hidepassword)
            }

            editText.setSelection(selection)
        }
    }



}