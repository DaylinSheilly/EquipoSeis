package com.appmovil.movilapp.repository

import com.appmovil.movilapp.model.Articulo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticuloRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    suspend fun getTotalArticulos(): Float {
        var total = 0.0f
        val querySnapshot = db.collection("articulo").get().await()
        for (document in querySnapshot.documents) {
            val precio = document.getDouble("precio")?.toFloat() ?: 0.0f
            val cantidad = document.getLong("cantidad")?.toFloat() ?: 0.0f

            val totalDocumento = precio * cantidad
            total += totalDocumento
        }
        return total
        }

    suspend fun getListArticulo():MutableList<Articulo> {
        val querySnapshot = db.collection("articulo").get().await()
        val listArticulos = mutableListOf<Articulo>()

        for (document in querySnapshot.documents) {
            // Construir un objeto Articulo con los datos del documento
            val articulo = Articulo(
                codigo = document.getLong("codigo")?.toInt() ?: 0,
                nombre = document.getString("nombre") ?: "",
                precio = document.getLong("precio")?.toInt() ?: 0,
                cantidad = document.getLong("cantidad")?.toInt() ?: 0
            )
            listArticulos.add(articulo)
        }
        return listArticulos
    }

    suspend fun guardarProducto(nuevoArticulo: Articulo) {
        withContext(Dispatchers.IO) {
            db.collection("articulo").document(nuevoArticulo.codigo.toString()).set(
                hashMapOf(
                    "codigo" to nuevoArticulo.codigo,
                    "nombre" to nuevoArticulo.nombre,
                    "precio" to nuevoArticulo.precio,
                    "cantidad" to nuevoArticulo.cantidad
                )
            ).await()
        }

    }
}

