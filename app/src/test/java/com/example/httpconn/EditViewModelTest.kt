package com.example.httpconn

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.httpconn.edit.EditViewModel
import com.example.httpconn.employee.EmployeeViewModel
import com.example.httpconn.main.MainViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class EditViewModelTest {

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var httpHelper: IHttpHelper
    private lateinit var viewModel: EditViewModel
    private val mapper by lazy { ObjectMapper().registerKotlinModule() }


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = EditViewModel(httpHelper)
    }

    @Test
    fun deleteEmployee() = runBlockingTest {
        val outPutResponse = Response("", 200)
        val employee = Employee("2", "John", "23440", "23")
        val response = mapper.writeValueAsString(employee)
        Mockito.`when`(httpHelper
                .send( RequestMethod.PUT,
                    "https://dummy.restapiexample.com/api/v1/update/${employee.id}", response)
        ).thenReturn(outPutResponse)
        viewModel.updateEmployee(employee)
        Mockito.verify(httpHelper)
            .send( RequestMethod.PUT,
                "https://dummy.restapiexample.com/api/v1/update/${employee.id}", response)

        val responseCode = viewModel.responseCode.getOrAwaitValue()
        Assert.assertEquals(responseCode, outPutResponse.code)
    }
}