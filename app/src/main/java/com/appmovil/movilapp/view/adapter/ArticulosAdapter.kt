package com.appmovil.movilapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.movilapp.databinding.RvHomeInventarioBinding
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.view.viewHolder.ArticulosViewHolder

class ArticulosAdapter(private val listArticulos: MutableList<Articulo>, private val onItemClick: (Articulo) -> Unit) : RecyclerView.Adapter<ArticulosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticulosViewHolder {
        val binding = RvHomeInventarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticulosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticulosViewHolder, position: Int) {
        val articulo = listArticulos[position]
        holder.setArticuloInventory(articulo)

        holder.itemView.setOnClickListener {
            onItemClick(articulo)
        }
    }

    override fun getItemCount(): Int {
        return listArticulos.size
    }
}
