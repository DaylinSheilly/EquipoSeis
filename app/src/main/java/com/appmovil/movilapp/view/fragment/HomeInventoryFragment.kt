package com.appmovil.movilapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmovil.movilapp.R
import com.appmovil.movilapp.databinding.FragmentHomeInventarioBinding
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.view.adapter.ArticulosAdapter
import com.appmovil.movilapp.view.fragment.HomeDetalleFragment
import com.appmovil.movilapp.viewmodel.ArticulosViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeInventoryFragment : Fragment() {
    private lateinit var binding: FragmentHomeInventarioBinding
    private val articulosViewModel: ArticulosViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeInventarioBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        dataLogin()
        setup()
        listarProducto()
    }

    private fun setup() {
        binding.contentToolbar.logOut.setOnClickListener {
            logOut()
        }
        binding.btnAdicionarArticulo.setOnClickListener{
            nuevoProducto()
        }
    }

    private fun nuevoProducto() {
        val navController = findNavController()
        // Realiza la navegación hacia la acción homeInventoryFragment->agregarArticulo
        navController.navigate(R.id.action_homeInventoryFragment_to_agregarArticulo)
    }


    private fun listarProducto() {
        // Mostrar el ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        articulosViewModel.getListArticulos()
        articulosViewModel.listInventory.observe(viewLifecycleOwner) { lista ->
            // Ocultar el ProgressBar
            binding.progressBar.visibility = View.GONE

            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val reversedList: MutableList<Articulo> = lista.toMutableList().asReversed()
            val adapter = ArticulosAdapter(reversedList) { articulo ->
                verDetalles(articulo)
            }
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun verDetalles(articulo: Articulo) {
        (requireActivity() as HomeActivity).apply {
            startActivity(Intent(this, HomeDetalleFragment::class.java))
            finish()
        }
    }

    private fun logOut() {
        sharedPreferences.edit().clear().apply()
        FirebaseAuth.getInstance().signOut()
        (requireActivity() as HomeActivity).apply {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun dataLogin() {
        val bundle = requireActivity().intent.extras
        val email = bundle?.getString("email")
        sharedPreferences.edit().putString("email",email).apply()
    }
}