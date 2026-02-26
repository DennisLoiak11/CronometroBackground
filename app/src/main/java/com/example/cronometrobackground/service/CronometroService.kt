package com.example.cronometrobackground.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.cronometrobackground.repository.ContatoreRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CronometroService : LifecycleService() {

    @Inject
    lateinit var repository: ContatoreRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        // azzera il contatore quando parte il servizio
        repository.azzera()

        // mostrare la notifica obbligatoria
        startForeground(1, createNotification())

        // avvio del lavoro asincrono senza bloccare lo schermo
        lifecycleScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(1000)
                repository.incrementa() // incrementa ogni secondo di 1
            }
        }
        return START_STICKY
    }

    private fun createNotification(): Notification {
        val channelId = "cronometro_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Cronometro", NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Cronometro Attivo")
            .setContentText("Sto contando i secondi in background...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
}