package com.example.cronometrobackground.component

import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cronometrobackground.service.CronometroService
import com.example.cronometrobackground.views.CronometroViewModel

@Composable
fun SchermataCronometro(
    viewModel: CronometroViewModel // Hilt ci dà il ViewModel!
) {
    // Raccogliamo i dati dal flusso. Ogni volta che il Service aggiorna il repository,
    // questa variabile 'secondi' si aggiorna da sola, e Compose ridisegna lo schermo!
    val secondi by viewModel.secondi.collectAsState()

    val context = LocalContext.current // Ci serve per gli Intent

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Secondi passati:",
            fontSize = 24.sp
        )
        Text(
            text = "$secondi",
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                // START SERVICE
                val intent = Intent(context, CronometroService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            }) {
                Text("START")
            }

            Button(onClick = {
                // STOP SERVICE
                val intent = Intent(context, CronometroService::class.java)
                context.stopService(intent)
            }) {
                Text("STOP")
            }
        }
    }
}