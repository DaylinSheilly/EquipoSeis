package com.appmovil.movilapp.view.viewHolder

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
        bindingArticulo.precioProducto.text = articulo.precio.toString()
    }
}