package com.lemi.prix_finance_v3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Transactions : AppCompatActivity() {

    private lateinit var transactionContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        transactionContainer = findViewById(R.id.transactionContainer)
        val btnAddTransaction: Button = findViewById(R.id.btnAddTransaction)

        btnAddTransaction.setOnClickListener {
            val intent = Intent(this, AddTransaction::class.java)
            startActivityForResult(intent, ADD_TRANSACTION_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TRANSACTION_REQUEST && resultCode == RESULT_OK) {
            data?.let {
                val name = it.getStringExtra("transaction_name") ?: ""
                val amount = it.getStringExtra("transaction_amount") ?: ""
                val category = it.getStringExtra("transaction_category") ?: ""
                val date = it.getStringExtra("transaction_date") ?: ""
                displayTransaction(name, amount, category, date)
            }
        }
    }

    private fun displayTransaction(name: String, amount: String, category: String, date: String) {
        val transactionLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val nameAmountTextView = TextView(this).apply {
            text = "$name\t\t\t$amount"
            textSize = 18f
            setTextColor(resources.getColor(R.color.bg_night))
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        }

        val categoryDateTextView = TextView(this).apply {
            text = "$category\t\t\t$date"
            textSize = 14f
            setTextColor(resources.getColor(R.color.gunmetal))
            setTypeface(typeface, android.graphics.Typeface.ITALIC)
        }

        transactionLayout.addView(nameAmountTextView)
        transactionLayout.addView(categoryDateTextView)

        transactionContainer.addView(transactionLayout)
    }

    companion object {
        private const val ADD_TRANSACTION_REQUEST = 1
    }
}
