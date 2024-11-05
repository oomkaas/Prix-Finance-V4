package com.lemi.prix_finance_v3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lemi.prix_finance_v3.databinding.ActivityMainBinding

class Goals : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goals)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNavigationView = findViewById(R.id.bottomNavGoals)

        // Bottom navigation setup
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itmHome ->{ intent = Intent(this, MainActivity::class.java)
                startActivity(intent)}

                R.id.itmBudgetManagement -> {intent = Intent(this, Budget::class.java)
                startActivity(intent)}

                R.id.itmGoals -> {intent = Intent(this, Goals::class.java)
                startActivity(intent)}

                R.id.itmTransactions -> {intent = Intent(this, Transactions::class.java)
                startActivity(intent)}
            }
            true
        }
    }
}