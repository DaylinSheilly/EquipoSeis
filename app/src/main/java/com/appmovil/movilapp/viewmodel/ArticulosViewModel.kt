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

    fun getListArticulos(){
        viewModelScope.launch {
            _listArticulos.value = repository.getListArticulo()

        }
    }
}