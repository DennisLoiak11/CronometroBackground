package com.example.cronometrobackground.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.cronometrobackground.repository.ContatoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel // Hilt crea questo ViewModel
class CronometroViewModel @Inject constructor(

    //injection del repository
    private val repository: ContatoreRepository,
    application: Application

) : AndroidViewModel(application) {

    val secondi: StateFlow<Int> = repository.secondi
}