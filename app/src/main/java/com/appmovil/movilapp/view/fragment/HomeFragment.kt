package com.appmovil.movilapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appmovil.movilapp.R
import com.appmovil.movilapp.databinding.FragmentHomeBinding
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.view.widget.TotalInventoryWidget
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()

    }

    private fun setup() {
        binding.btnGuardarArticulo.setOnClickListener {
            guardarProducto()
        }
        binding.contentToolbar.backToolbar.setOnClickListener {
            volverMenu()
        }
    }
    private fun guardarProducto() {
        val codigo = binding.etCodigo.text.toString()
        val nombre = binding.etNombreArticulo.text.toString()
        val precio = binding.etPrecio.text.toString()
        val cantidad = binding.etCantidad.text.toString()

        if (codigo.isNotEmpty() && nombre.isNotEmpty() && precio.isNotEmpty()) {
            val articulo = Articulo(codigo.toInt(), nombre, precio.toInt(), cantidad.toInt())

            db.collection("articulo").document(articulo.codigo.toString()).set(
                hashMapOf(
                    "codigo" to articulo.codigo,
                    "nombre" to articulo.nombre,
                    "precio" to articulo.precio,
                    "cantidad" to articulo.cantidad
                )
            )

            val updateIntent = Intent(context, TotalInventoryWidget::class.java)
            updateIntent.action = "UPDATE_WIDGET"
            context?.sendBroadcast(updateIntent)

            Toast.makeText(context, "Articulo guardado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(context, "Llene los campos", Toast.LENGTH_SHORT).show()
        }
    }


    private fun volverMenu() {
        val navController = findNavController()
        navController.navigate(R.id.action_homeFragment_to_homeInvetoryFragment)
    }

    private fun limpiarCampos() {
        binding.etCodigo.setText("")
        binding.etNombreArticulo.setText("")
        binding.etPrecio.setText("")
        binding.etCantidad.setText("")
    }
}