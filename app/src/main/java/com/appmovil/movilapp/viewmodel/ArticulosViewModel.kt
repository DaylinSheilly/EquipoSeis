package com.appmovil.movilapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.repository.ArticuloRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticulosViewModel @Inject constructor(
    private val repository: ArticuloRepository
): ViewModel() {

    private val _listArticulos = MutableLiveData<MutableList<Articulo>>()
    val listInventory: LiveData<MutableList<Articulo>> get() = _listArticulos

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState
    fun getListArticulos(){
        viewModelScope.launch {
            _listArticulos.value = repository.getListArticulo()

        }
    }
    fun guardarProducto(codigo: String, nombre: String, precio: String, cantidad: String){
        viewModelScope.launch {
            val nuevoArticulo = Articulo(codigo = codigo.toInt(), nombre = nombre, precio = precio.toInt(), cantidad = cantidad.toInt())
            _progresState.value = true
            try {
                repository.guardarProducto(nuevoArticulo)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }
}