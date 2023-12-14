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
import androidx.navigation.fragment.findNavController
import com.appmovil.movilapp.R
import com.appmovil.movilapp.databinding.FragmentEditarDetallesBinding
import com.appmovil.movilapp.model.Articulo
import com.google.firebase.firestore.FirebaseFirestore

class HomeEditarFragment : Fragment() {
    private lateinit var binding: FragmentEditarDetallesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()
    private lateinit var receivedArticulo: Articulo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarDetallesBinding.inflate(inflater)
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
        binding.btnActualizarArticulo.setOnClickListener {
            actualizarProducto()
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

    private fun cargarDetalles() {
        binding.etCodigo.text = "Id: " + receivedArticulo.codigo.toString()
        binding.etNombreArticulo.setText(receivedArticulo.nombre)
        binding.etPrecio.setText(receivedArticulo.precio.toString())
        binding.etCantidad.setText(receivedArticulo.cantidad.toString())
    }

    private fun actualizarProducto() {
        val nombre = binding.etNombreArticulo.text.toString()
        val precio = binding.etPrecio.text.toString()
        val cantidad = binding.etCantidad.text.toString()

        if (nombre.isNotEmpty() && precio.isNotEmpty() && cantidad.isNotEmpty()) {
//            val articulo = Articulo(codigo.toInt(), nombre, precio.toInt(), cantidad.toInt())
//
//            db.collection("articulo").document(articulo.codigo.toString()).set(
//                hashMapOf(
//                    "codigo" to articulo.codigo,
//                    "nombre" to articulo.nombre,
//                    "precio" to articulo.precio,
//                    "cantidad" to articulo.cantidad
//                )
//            )

            Toast.makeText(context, "Articulo actualizado", Toast.LENGTH_SHORT).show()
            volverMenu()
        } else {
            Toast.makeText(context, "Llene los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun volverMenu() {
        val navController = findNavController()
        // Realiza la navegación hacia la acción homeInventoryFragment->agregarArticulo
        val bundle = Bundle()
        bundle.putSerializable("articulo", receivedArticulo)
        navController.navigate(R.id.action_homeEditarFragment_to_homeDetallesFragment, bundle)
    }

}