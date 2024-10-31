package com.lemi.prix_finance_v3

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var displayActiveBudgets : RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var addNewBudget: Button
    private lateinit var searchBudgets: SearchView
    private lateinit var adapter: ItemAdapter

    private val items = mutableListOf(
        Item("Munch", 50.99, "Completed"),
        Item("Rent", 7500.00, "On Track"),
        Item("Utilities", 120.75, "Completed"),
        Item("Groceries", 3499.99, "Complete"),
        Item("Petrol", 1800.75, "Caution"),
        Item("Alcohol", 1500.00, "Caution"),
        Item("Insurance", 13000.75, "Completed")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        displayActiveBudgets = findViewById(R.id.recyclerViewHome)
        addNewBudget = findViewById(R.id.btnAddBudget)
        searchBudgets = findViewById(R.id.searchbar)

        setupRecyclerView()

        displayActiveBudgets.setOnClickListener {
            // Handle RecyclerView click
            val position = displayActiveBudgets.getChildAdapterPosition(it)
            if (position != RecyclerView.NO_POSITION) {
                val item = items[position]
                Toast.makeText(this, "Clicked on ${item.title}", Toast.LENGTH_SHORT).show()
            }
        }

        addNewBudget.setOnClickListener {
            // Add new budget logic
            addItem()
        }

        searchBudgets.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterItems(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText ?: "")
                return true
            }
        })

    }

    private fun addItem() {
        val newItem = Item("New Budget", 0.00, "Pending")
        items.add(newItem)
        adapter.notifyItemInserted(items.size - 1)
    }

    private fun filterItems(query: String) {
        val filteredItems = items.filter { item ->
            item.title.lowercase().contains(query.lowercase())
        }
        adapter.submitList(filteredItems)
    }

    private fun setupRecyclerView() {
        displayActiveBudgets.apply {
            adapter = ItemAdapter(items)
            displayActiveBudgets.adapter = adapter
            displayActiveBudgets.layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }
}

