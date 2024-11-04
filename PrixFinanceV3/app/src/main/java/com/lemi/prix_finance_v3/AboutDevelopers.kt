package com.lemi.prix_finance_v3

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.integrity.i

class AboutDevelopers : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navView: NavigationView
    private lateinit var btnAddIncome: TextView
    private lateinit var btnCreateBudget: TextView
    private lateinit var btnViewBudget: TextView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_developers)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets}

        //finding the drawer
        drawerLayout = findViewById(R.id.main)
        navView = findViewById(R.id.navView_aboutDevelopers)


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