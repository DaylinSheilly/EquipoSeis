package com.appmovil.movilapp.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmovil.movilapp.databinding.FragmentHomeBinding
import com.appmovil.movilapp.databinding.FragmentVerDetallesBinding
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.view.HomeActivity
import com.appmovil.movilapp.view.LoginActivity
import com.appmovil.movilapp.view.adapter.ArticulosAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeDetalleFragment : Fragment() {
    private lateinit var binding: FragmentVerDetallesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerDetallesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        setup()
    }

    private fun setup() {
        binding.btnEliminarArticulo.setOnClickListener {
            eliminarProducto()
        }
        cargarDetalles()
    }
    private fun eliminarProducto() {
//        db.collection("articulo").document(articulo.codigo.toString()).delete()
    }

    private fun cargarDetalles() {
        val detalles = binding.detalles
    }


}