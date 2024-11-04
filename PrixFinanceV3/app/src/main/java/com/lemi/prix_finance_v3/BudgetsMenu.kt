package com.lemi.prix_finance_v3

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class BudgetsMenu : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var NavigationView: NavigationView
    private lateinit var btnAddIncome: TextView
    private lateinit var btnCreateBudget: TextView
    private lateinit var btnViewBudget: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_budgets_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNavigationView = findViewById(R.id.bottomNavBudgetMenu)
        //NavigationView = findViewById(R.id.bottomNavBudgetMenu)
        btnAddIncome = findViewById(R.id.btnAddIncome)
        btnCreateBudget = findViewById(R.id.btnCreateNewBudget)
        btnViewBudget = findViewById(R.id.btnViewBudgets)

        btnAddIncome.setOnClickListener{
            intent = Intent(this, AddIncome::class.java)
            startActivity(intent)
        }

        btnCreateBudget.setOnClickListener{
            intent = Intent(this, Budget::class.java)
            startActivity(intent)
        }

        btnViewBudget.setOnClickListener{
            intent = Intent(this, ViewBudgets::class.java)
            startActivity(intent)
        }


        // Bottom navigation setup
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itmHome ->{ intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)}

                R.id.itmBudgetManagement -> {intent = Intent(this, BudgetsMenu::class.java)
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