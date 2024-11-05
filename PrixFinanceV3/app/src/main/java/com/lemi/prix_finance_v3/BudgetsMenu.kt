package com.lemi.prix_finance_v3

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class BudgetsMenu : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navView: NavigationView
    private lateinit var btnAddIncome: TextView
    private lateinit var btnCreateBudget: TextView
    private lateinit var btnViewBudget: TextView
    private lateinit var drawerLayout: DrawerLayout

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
        drawerLayout = findViewById(R.id.main)
        navView = findViewById(R.id.navView_BudgetsMenu)
        btnAddIncome = findViewById(R.id.btnAddIncome)
        btnCreateBudget = findViewById(R.id.btnCreateNewBudget)
        btnViewBudget = findViewById(R.id.btnViewBudgets)

        btnAddIncome.setOnClickListener {
            intent = Intent(this, AddIncome::class.java)
            startActivity(intent)
        }

        btnCreateBudget.setOnClickListener {
            intent = Intent(this, Budget::class.java)
            startActivity(intent)
        }

        btnViewBudget.setOnClickListener {
            intent = Intent(this, ViewBudgets::class.java)
            startActivity(intent)
        }


        // Bottom navigation setup
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itmHome -> {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                R.id.itmBudgetManagement -> {
                    intent = Intent(this, BudgetsMenu::class.java)
                    startActivity(intent)
                }

                R.id.itmGoals -> {
                    intent = Intent(this, Goals::class.java)
                    startActivity(intent)
                }

                R.id.itmTransactions -> {
                    intent = Intent(this, Transactions::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        // Setup navigation view item selection
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itmDashboard -> {
                    // Handle dashboard action
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.itmNotifications -> {
                    // Handle notifications action
                    startActivity(Intent(this, Notifications::class.java))
                    true
                }

                R.id.itmBudgetManagement -> {
                    // Handle budget management action
                    startActivity(Intent(this, BudgetsMenu::class.java))
                    true
                }

                R.id.itmTransactions -> {
                    // Handle transactions action
                    startActivity(Intent(this, Transactions::class.java))
                    true
                }

                R.id.itmGoals -> {
                    // Handle goals action
                    startActivity(Intent(this, Goals::class.java))
                    true
                }

                R.id.itmAboutDevelopers -> {
                    // Handle goals action
                    startActivity(Intent(this, AboutDevelopers::class.java))
                    true
                }

                R.id.itmAboutDevelopers -> {
                    // Handle goals action
                    startActivity(Intent(this, AboutDevelopers::class.java))
                    true
                }

                R.id.itmSettings -> {
                    // Handle settings action
                    startActivity(Intent(this, Settings::class.java))
                    true
                }

                R.id.itmLogout -> {
                    // Handle logout action
                    true
                }
            }
            toggleDrawer()
            true
        }

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbarBudgetsMenu))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Setup hamburger button click listener
        findViewById<ImageView>(R.id.imgHamburger).setOnClickListener {
            toggleDrawer()
        }
    }

        private fun toggleDrawer() {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                android.R.id.home -> {
                    toggleDrawer()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }

}