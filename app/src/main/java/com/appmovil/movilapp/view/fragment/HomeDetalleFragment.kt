package com.appmovil.movilapp.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appmovil.movilapp.R
import com.appmovil.movilapp.databinding.FragmentVerDetallesBinding
import com.appmovil.movilapp.model.Articulo
import com.google.firebase.firestore.FirebaseFirestore

class HomeDetalleFragment() : Fragment() {
    private lateinit var binding: FragmentVerDetallesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()
    private lateinit var receivedArticulo: Articulo

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
        dataArticulo()
        setup()
    }

    private fun setup() {
        binding.btnEditarArticulo.setOnClickListener {
            editarProducto()
        }
        binding.btnEliminarArticulo.setOnClickListener {
            eliminarProducto()
        }
        binding.contentToolbar.backToolbar.setOnClickListener {
            volverMenu()
        }
        cargarDetalles()
    }

    private fun dataArticulo() {
        val receivedBundle = arguments
        receivedArticulo = receivedBundle?.getSerializable("articulo") as Articulo
    }

    private fun editarProducto() {
        val navController = findNavController()
        val bundle = Bundle()
        bundle.putSerializable("articulo", receivedArticulo)
        navController.navigate(R.id.action_homeDetallesFragment_to_homeEditarFragment, bundle)
    }

    private fun eliminarProducto() {
        db.collection("articulo").document(receivedArticulo.codigo.toString()).delete()
        val navController = findNavController()
        navController.navigate(R.id.action_homeDetalleFragment_to_homeInvetoryFragment)
    }

    private fun cargarDetalles() {
        binding.progressBar.visibility = View.VISIBLE

        binding.cardDetalles.nombreProducto.text = receivedArticulo.nombre
        binding.cardDetalles.valorUnidad.text = "$ " + receivedArticulo.precio.toString()
        binding.cardDetalles.valorCantidad.text = receivedArticulo.cantidad.toString()
        binding.cardDetalles.valorTotal.text = "$ " + (receivedArticulo.precio * receivedArticulo.cantidad).toString()

        binding.progressBar.visibility = View.GONE

    }

    private fun volverMenu() {
        val navController = findNavController()
        // Realiza la navegación hacia la acción homeInventoryFragment->agregarArticulo
        navController.navigate(R.id.action_homeDetalleFragment_to_homeInvetoryFragment)
    }


}