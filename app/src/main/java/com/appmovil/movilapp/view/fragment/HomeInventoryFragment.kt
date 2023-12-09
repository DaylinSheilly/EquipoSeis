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
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmovil.movilapp.databinding.FragmentHomeInventarioBinding
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.view.adapter.ArticulosAdapter
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
            guardarProducto()
        }
    }

    private fun guardarProducto() {
        // TODO:navbar que te lleva a HU4
        Toast.makeText(requireContext(), "Pasar a HU4", Toast.LENGTH_SHORT).show()
        }

    private fun listarProducto(){
        articulosViewModel.getListArticulos()
        articulosViewModel.listInventory.observe(viewLifecycleOwner) { lista ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val reversedList: MutableList<Articulo> = lista.toMutableList().asReversed()
            val adapter = ArticulosAdapter(reversedList)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()


//            Toast.makeText(requireContext(),"aymamamia",Toast.LENGTH_LONG).show()
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