package com.lemi.prix_finance_v3

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Budget : AppCompatActivity() {

    private lateinit var ItemAdapter: ItemAdapter
    private lateinit var displayActiveBudgets : RecyclerView


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
        setContentView(R.layout.activity_view_budgets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //finding all views page
        displayActiveBudgets = findViewById(R.id.recyclerViewBudgets)
        setupRecyclerView()

        //setupItemClick()



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