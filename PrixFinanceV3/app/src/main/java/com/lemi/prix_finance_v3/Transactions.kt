package com.lemi.prix_finance_v3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
//import android.widget.LinearLayout
//import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
//import com.github.mikephil.charting.charts.BarChart
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.BarData
//import com.github.mikephil.charting.data.BarDataSet
//import com.github.mikephil.charting.data.BarEntry
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
//import java.text.SimpleDateFormat
//import java.util.*
//
class Transactions : AppCompatActivity() {
//
//    private lateinit var transactionContainer: LinearLayout
//    //private lateinit var barChart: BarChart
//    private val transactionsByDay = mutableMapOf<String, Float>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_transactions)
//
//        transactionContainer = findViewById(R.id.transactionContainer)
//       // barChart = findViewById(R.id.barChart)
//        val btnAddTransaction: Button = findViewById(R.id.btnAddTransaction)
//
//        btnAddTransaction.setOnClickListener {
//            val intent = Intent(this, AddTransaction::class.java)
//            startActivityForResult(intent, ADD_TRANSACTION_REQUEST)
//        }
//
//        setupBarChart()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == ADD_TRANSACTION_REQUEST && resultCode == RESULT_OK) { // Line 72
//            data?.let {
//                val name = it.getStringExtra("transaction_name") ?: ""
//                val amount = it.getStringExtra("transaction_amount") ?: ""
//                val category = it.getStringExtra("transaction_category") ?: ""
//                val date = it.getStringExtra("transaction_date") ?: ""
//
//                // Display transaction in the list
//                displayTransaction(name, amount, category, date)
//
//                // Update the bar chart with the transaction amount
//              val amountFloat = amount.replace("ZAR ", "").toFloatOrNull() ?: 0f
//                val dayOfWeek = getDayOfWeekFromDate(date)
//                transactionsByDay[dayOfWeek] = (transactionsByDay[dayOfWeek] ?: 0f) + amountFloat
//                updateBarChart()
//            }
//        }
//    }
//
//    private fun displayTransaction(name: String, amount: String, category: String, date: String) {
//        val transactionLayout = LinearLayout(this).apply {
//            orientation = LinearLayout.VERTICAL
//        }
//
//        val nameAmountTextView = TextView(this).apply {
//            text = "$name\t\t\t$amount"
//            textSize = 18f
//            setTextColor(ContextCompat.getColor(this@Transactions, R.color.bg_night))
//            setTypeface(typeface, android.graphics.Typeface.BOLD)
//        }
//
//        val categoryDateTextView = TextView(this).apply {
//            text = "$category\t\t\t$date"
//            textSize = 14f
//            setTextColor(ContextCompat.getColor(this@Transactions, R.color.gunmetal))
//            setTypeface(typeface, android.graphics.Typeface.ITALIC)
//        }
//
//        transactionLayout.addView(nameAmountTextView)
//        transactionLayout.addView(categoryDateTextView)
//        transactionContainer.addView(transactionLayout)
//    }
//
//    private fun setupBarChart() {
//        val bgNightColor = ContextCompat.getColor(this, R.color.bg_night)
//        val gunmetalColor = ContextCompat.getColor(this, R.color.gunmetal)
//
//        val daysOfWeek = listOf("Mon",
//            "Tue",
//            "Wed",
//            "Thu",
//            "Fri",
//            "Sat",
//            "Sun")
//
//        // Setting up x-axis
////        barChart.xAxis.apply {
////            valueFormatter = IndexAxisValueFormatter(daysOfWeek)
////            granularity = 1f
////            isGranularityEnabled = true
////            position = XAxis.XAxisPosition.BOTTOM
////            textColor = bgNightColor
////            axisLineColor = bgNightColor
////        }
////
////        // Setting up y-axis
////        barChart.axisLeft.apply {
////            axisMinimum = 0f
////            textColor = bgNightColor
////            axisLineColor = bgNightColor
////        }
////        barChart.axisRight.isEnabled = false
////
////        barChart.description.isEnabled = false
////        barChart.legend.isEnabled = false
////
////        updateBarChart()
////    }
////
////    private fun updateBarChart() {
////        val bgNightColor = ContextCompat.getColor(this, R.color.bg_night)
////        val gunmetalColor = ContextCompat.getColor(this, R.color.gunmetal)
////
////        val barEntries = mutableListOf<BarEntry>()
////        val daysOfWeek = listOf("Mon",
////            "Tue",
////            "Wed",
////            "Thu",
////            "Fri",
////            "Sat",
////            "Sun")
////
////        daysOfWeek.forEachIndexed { index, day ->
////            val expense = transactionsByDay[day] ?: 0f
////            barEntries.add(BarEntry(index.toFloat(), expense))
////        }
////
////        val barDataSet = BarDataSet(barEntries, "Weekly Expenses").apply {
////            color = gunmetalColor
////            valueTextColor = bgNightColor
////        }
////
////        barChart.data = BarData(barDataSet).apply {
////            barWidth = 0.7f
////        }
////        barChart.invalidate()
////    }
////
////    private fun getDayOfWeekFromDate(dateString: String): String {
////        return try {
////            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
////            val date = sdf.parse(dateString) ?: return ""
////            val calendar = Calendar.getInstance().apply {
////                time = date
////            }
////            when (calendar.get(Calendar.DAY_OF_WEEK)) {
////                Calendar.MONDAY -> "Mon"
////                Calendar.TUESDAY -> "Tue"
////                Calendar.WEDNESDAY -> "Wed"
////                Calendar.THURSDAY -> "Thu"
////                Calendar.FRIDAY -> "Fri"
////                Calendar.SATURDAY -> "Sat"
////                Calendar.SUNDAY -> "Sun"
////                else -> ""
////            }
////        } catch (e: Exception) {
////            ""
////        }
////    }
////
////    companion object {
////        private const val ADD_TRANSACTION_REQUEST = 1
   //}
}