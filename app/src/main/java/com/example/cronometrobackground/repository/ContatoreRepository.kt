package com.example.cronometrobackground.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContatoreRepository @Inject constructor(){
    private val _secondi: MutableStateFlow<Int> = MutableStateFlow(0)
    //StateFlow serve a val secondi per assumere il valore di _secondi che cambia nel tempo perchè è MutableStateFlow
    val secondi: StateFlow<Int> = _secondi.asStateFlow()

    fun incrementa() {
        _secondi.value += 1
    }

    fun azzera() {
        _secondi.value = 0
    }
}