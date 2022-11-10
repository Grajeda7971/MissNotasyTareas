package com.example.missnotasytareas.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.missnotasytareas.controlador.NotasApp.Companion.db
import com.example.missnotasytareas.modelo.Notas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    val notasList = MutableLiveData<List<Notas>>()
    val parametroBusqueda = MutableLiveData<String>()

    fun iniciar(){
        viewModelScope.launch {
            notasList.value= withContext(Dispatchers.IO){
                db.notasDao().getAll()
            }
        }
    }

    fun buscarNota() {
        viewModelScope.launch {
            notasList.value= withContext(Dispatchers.IO){
                db.notasDao().getByName(parametroBusqueda.value!!)
            }
        }
    }
}

