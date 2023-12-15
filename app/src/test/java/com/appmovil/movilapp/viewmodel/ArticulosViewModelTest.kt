package com.appmovil.movilapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmovil.movilapp.model.Articulo
import com.appmovil.movilapp.repository.ArticuloRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ArticulosViewModelTest {
    @get:Rule

    val rule = InstantTaskExecutorRule()
    private lateinit var articulosViewModel: ArticulosViewModel
    private lateinit var articuloRepository: ArticuloRepository

    @Before

    fun setUp(){
        articuloRepository = mock(ArticuloRepository::class.java)
        articulosViewModel = ArticulosViewModel(articuloRepository)
    }

    @Test
    fun`test metodo guardarProducto`() = runBlocking{

        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val articulo = Articulo(1, "peine", 7000, 3)

        `when`(articuloRepository.guardarProducto(articulo))
            .thenAnswer{ invocation ->
                val articuloArgument = invocation.getArgument<Articulo>(0)
                articuloArgument
            }
        //when
        articulosViewModel.guardarProducto(1.toString(),"peine", 7000.toString(), 3.toString())
        //then
        Mockito.verify(articuloRepository).guardarProducto(articulo)
        Dispatchers.resetMain()

    }

    @Test
    fun`test metodo getListArticulos`() = runBlocking {
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val mockArticulos = mutableListOf(
            Articulo(1, "peine", 7000, 3)
        )
        `when`(articuloRepository.getListArticulo()).thenReturn(mockArticulos)
        //when
        articulosViewModel.getListArticulos()
        //then
        assertEquals(articulosViewModel.listInventory.value, mockArticulos)
        Dispatchers.resetMain()
    }
}