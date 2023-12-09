package com.appmovil.movilapp.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context):FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }
    @Singleton
    @Provides
    fun provideAuth(@ApplicationContext context: Context):FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

}