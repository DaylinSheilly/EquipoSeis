package com.appmovil.movilapp.repository

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
    }
