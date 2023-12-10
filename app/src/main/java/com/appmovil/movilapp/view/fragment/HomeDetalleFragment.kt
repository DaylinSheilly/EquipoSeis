package com.appmovil.movilapp.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmovil.movilapp.R
import com.appmovil.movilapp.databinding.FragmentVerDetallesBinding
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.view.adapter.ArticulosAdapter
import com.google.firebase.firestore.FirebaseFirestore

class HomeDetalleFragment() : Fragment() {
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
        binding.contentToolbar.backToolbar.setOnClickListener {
            volverMenu()
        }
        cargarDetalles()
    }
    private fun eliminarProducto() {
//        db.collection("articulo").document(articulo.codigo.toString()).delete()
    }

    private fun cargarDetalles() {
        Toast.makeText(context, "producto ${articulo.nombre}", Toast.LENGTH_SHORT).show()

        binding.progressBar.visibility = View.VISIBLE

        binding.cardDetalles.nombreProducto.text = Articulo.name;

        binding.progressBar.visibility = View.GONE

    }

    private fun volverMenu() {
        val navController = findNavController()
        // Realiza la navegación hacia la acción homeInventoryFragment->agregarArticulo
        navController.navigate(R.id.action_homeDetalleFragment_to_homeInvetoryFragment)
    }


}