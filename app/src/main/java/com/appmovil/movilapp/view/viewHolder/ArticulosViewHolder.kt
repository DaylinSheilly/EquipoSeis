package com.appmovil.movilapp.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.appmovil.movilapp.databinding.RvHomeInventarioBinding
import com.appmovil.movilapp.model.Articulo
import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import com.appmovil.movilapp.R

class ArticulosViewHolder (binding: RvHomeInventarioBinding, navController: NavController):RecyclerView.ViewHolder(binding.root) {
    val bindingArticulo = binding
    val navController = navController

    fun setArticuloInventory(articulo: Articulo){
        // Asigna los valores a las vistas en el ViewHolder
        bindingArticulo.nombreProducto.text = articulo.nombre
        bindingArticulo.idProducto.text = articulo.codigo.toString()
        val precio = "$ "+ articulo.precio.toString()
        bindingArticulo.precioProducto.text = precio
        val context = bindingArticulo.root.context
        selectArticuloInventory(articulo, context)
    }

    fun selectArticuloInventory(articulo: Articulo, context: Context){
        bindingArticulo.cvInventory.setOnClickListener{
            // Realiza la navegación hacia la acción homeInventoryFragment->agregarArticulo
            navController.navigate(R.id.action_homeInventoryFragment_to_homeDetallesFragment)
        }
    }
}