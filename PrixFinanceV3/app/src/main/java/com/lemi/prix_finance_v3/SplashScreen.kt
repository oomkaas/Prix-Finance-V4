package com.lemi.prix_finance_v3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var btnGetStarted: Button
    private lateinit var languageSpinner: Spinner
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        val savedLanguage = loadSavedLanguage()
        if (!savedLanguage.isNullOrEmpty()) {
            setAppLocale(savedLanguage)
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnGetStarted = findViewById(R.id.btnGetStarted)
        languageSpinner = findViewById(R.id.language_spinner)

        btnGetStarted.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        languageSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("English", "IsiXhosa", "Afrikaans")
        )

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> changeLanguage("en");
                    1 -> changeLanguage("xh");
                    2 -> changeLanguage("af-RZA")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setAppLocale(languageCode: String) {
        val locale = Locale.forLanguageTag(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun changeLanguage(languageCode: String) {
        setAppLocale(languageCode)
        saveLanguage(languageCode)

        // Instead of recreating the activity, update the UI manually
        updateUIAfterLanguageChange()
    }

    private fun updateUIAfterLanguageChange() {
        // Update UI elements here
        btnGetStarted.text = getString(R.string.txt_btnGetStarted)
        languageSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf(("English"), ("IsiXhosa"), ("afrikaans"))
        )
    }

    private fun saveLanguage(languageCode: String) {
        prefs.edit().putString("language_code", languageCode).apply()
    }

    private fun loadSavedLanguage(): String? {
        return prefs.getString("language_code", null)
    }
}
