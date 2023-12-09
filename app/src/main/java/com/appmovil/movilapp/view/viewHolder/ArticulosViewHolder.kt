package com.appmovil.movilapp.view.viewHolder

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.movilapp.databinding.RvHomeInventarioBinding
import com.appmovil.movilapp.model.Articulo


class ArticulosViewHolder (binding: RvHomeInventarioBinding):RecyclerView.ViewHolder(binding.root) {
    val bindingArticulo = binding

    fun setArticuloInventory(articulo: Articulo){
        // Asigna los valores a las vistas en el ViewHolder
        bindingArticulo.nombreProducto.text = articulo.nombre
        bindingArticulo.idProducto.text = articulo.codigo.toString()
        val precio = "$ "+ articulo.precio.toString()
        bindingArticulo.precioProducto.text = precio
        selectArticuloInventory(articulo)
    }

    fun selectArticuloInventory(articulo: Articulo){
        bindingArticulo.cvInventory.setOnClickListener{
            //  TODO: agregar navegación hacia HU5 y pasarle el articulo
        }
    }
}