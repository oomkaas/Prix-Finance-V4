package com.lemi.prix_finance_v3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AddIncome : AppCompatActivity() {

    private lateinit var editTxtTransactionName: EditText
    private lateinit var editTxtTransactionAmount: EditText
    private lateinit var navView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var addIncome: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        editTxtTransactionName = findViewById(R.id.editTxtCategory)
        editTxtTransactionAmount = findViewById(R.id.editTxtAmountToAdd)
        addIncome = findViewById(R.id.btnAddIncome)

        //navigations
        bottomNavigationView = findViewById(R.id.bottomNavAddIncome)
        drawerLayout = findViewById(R.id.main)
        navView = findViewById(R.id.navView_addIncome)

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


        // Setup navigation view item selection
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itmDashboard -> {
                    // Handle dashboard action
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }


                R.id.itmBudgetManagement -> {
                    // Handle budget management action
                    startActivity(Intent(this, Budget::class.java))
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
        setSupportActionBar(findViewById(R.id.toolBarAddIncome))
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


}