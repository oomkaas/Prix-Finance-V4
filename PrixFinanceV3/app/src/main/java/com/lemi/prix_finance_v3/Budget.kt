package com.lemi.prix_finance_v3

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class Budget : AppCompatActivity() {

    private lateinit var ItemAdapter: ItemAdapter
    private lateinit var displayActiveBudgets : RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navView: NavigationView
    private lateinit var btnAddIncome: TextView
    private lateinit var btnCreateBudget: TextView
    private lateinit var btnEditBudget: TextView
    private lateinit var btnDeleteBudget: TextView
    private lateinit var drawerLayout: DrawerLayout


    //all budget elements; budget title, amountRemaining, status
    private val items = mutableListOf(
        Item("Munch", 50.99, "Completed"),
        Item("Rent", 7500.00, "On Track"),
        Item("Utilities", 120.75, "Completed"),
        Item("Groceries", 3499.99, "Complete"),
        Item("Petrol", 1800.75, "Caution"),
        Item("Alcohol", 1500.00, "Caution"),
        Item("Insurance", 13000.75, "Completed"))

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_budget)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //finding all views page
        displayActiveBudgets = findViewById(R.id.recyclerViewBudgets)
        setupRecyclerView()

        //setupItemClick()



        bottomNavigationView = findViewById(R.id.bottomNavBudget)
        drawerLayout = findViewById(R.id.main)
        navView = findViewById(R.id.navView_Budget)
        btnAddIncome = findViewById(R.id.btnAddIncome)
        btnCreateBudget = findViewById(R.id.btnAddNewBudget)
        btnDeleteBudget = findViewById(R.id.btnDeleteBudget)
        btnEditBudget = findViewById(R.id.btnEditBudget)

        btnAddIncome.setOnClickListener {
            intent = Intent(this, AddIncome::class.java)
            startActivity(intent)
        }

        btnCreateBudget.setOnClickListener {
            intent = Intent(this, Budget::class.java)
            startActivity(intent)
        }

        btnEditBudget.setOnClickListener {
            intent = Intent(this, EditBudget::class.java)
            startActivity(intent)
        }

        btnDeleteBudget.setOnClickListener {
            intent = Intent(this, DeleteBudget::class.java)
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
                    intent = Intent(this, Budget::class.java)
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
        setSupportActionBar(findViewById(R.id.toolBarBudget))
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

    private fun setupRecyclerView() {
        displayActiveBudgets.apply {
            adapter = ItemAdapter(items)
            displayActiveBudgets.adapter = adapter
            displayActiveBudgets.layoutManager = LinearLayoutManager(this@Budget)
            setHasFixedSize(true)
        }
    }

    private fun setupItemClick(recView: RecyclerView){
        recView.setOnClickListener{

        }

    }

}