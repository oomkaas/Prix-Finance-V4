package com.lemi.prix_finance_v3

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class Transactions : AppCompatActivity() {

    data class Transaction(
        val amount: Double,
        val category: String,
        val description: String
    )

    private lateinit var editTxtTransactionName: EditText
    private lateinit var editTxtTransactionAmount: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        editTxtTransactionName = findViewById(R.id.editTxtCategory)
        editTxtTransactionAmount = findViewById(R.id.editTxtTransactionAmount)
        val btnAddTransaction: Button = findViewById(R.id.btnAddTransaction)

        // Check and request the notification permission when the save button is clicked
        btnAddTransaction.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is granted, proceed with sending the notification
                sendTransactionNotification()
            } else {
                // Request the permission
                requestNotificationPermission()
            }
        }

        // Create the notification channel
        createNotificationChannel()
    }

    private fun sendTransactionNotification() {
        val transactionName = findViewById<EditText>(R.id.editTxtCategory).text.toString()
        val transactionAmount = findViewById<EditText>(R.id.editTxtTransactionAmount).text.toString()

        // Create notification channel (for Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "transactions_channel"
            val channelName = "Transaction Notifications"
            val channelDescription = "Notifications for transactions"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(this, "transactions_channel")
            .setSmallIcon(R.drawable.ic_notifications) // your notification icon
            .setContentTitle("Transaction Made")
            .setContentText("Transaction: $transactionName, Amount:R$transactionAmount")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notificationBuilder.build())

        Toast.makeText(this, "Notification sent!", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and is not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Transaction Notifications"
            val descriptionText = "Notifications for added transactions"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("TRANSACTION_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        // Request the permission
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1
        )
    }

    // Handle the permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission was granted, send the notification
            sendTransactionNotification()
        } else {
            // Permission was denied
            Toast.makeText(this, "Permission denied to post notifications", Toast.LENGTH_SHORT).show()
        }
    }
}