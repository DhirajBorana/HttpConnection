package com.example.httpconn

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class MainViewModelTest {

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var httpHelper: IHttpHelper
    private lateinit var viewModel: MainViewModel
    private val mapper by lazy { ObjectMapper().registerKotlinModule() }


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(httpHelper)
    }

    @Test
    fun getAll() = runBlockingTest {
        val response = Response(
            "{\"status\":\"success\",\"data\":[{\"id\":\"1\",\"employee_name\":\"Tiger Nixon\",\"employee_salary\":\"320800\",\"employee_age\":\"61\",\"profile_image\":\"\"},{\"id\":\"2\",\"employee_name\":\"Garrett Winters\",\"employee_salary\":\"170750\",\"employee_age\":\"63\",\"profile_image\":\"\"},{\"id\":\"3\",\"employee_name\":\"Ashton Cox\",\"employee_salary\":\"86000\",\"employee_age\":\"66\",\"profile_image\":\"\"},{\"id\":\"4\",\"employee_name\":\"Cedric Kelly\",\"employee_salary\":\"433060\",\"employee_age\":\"22\",\"profile_image\":\"\"},{\"id\":\"5\",\"employee_name\":\"Airi Satou\",\"employee_salary\":\"162700\",\"employee_age\":\"33\",\"profile_image\":\"\"},{\"id\":\"6\",\"employee_name\":\"Brielle Williamson\",\"employee_salary\":\"372000\",\"employee_age\":\"61\",\"profile_image\":\"\"},{\"id\":\"7\",\"employee_name\":\"Herrod Chandler\",\"employee_salary\":\"137500\",\"employee_age\":\"59\",\"profile_image\":\"\"},{\"id\":\"8\",\"employee_name\":\"Rhona Davidson\",\"employee_salary\":\"327900\",\"employee_age\":\"55\",\"profile_image\":\"\"},{\"id\":\"9\",\"employee_name\":\"Colleen Hurst\",\"employee_salary\":\"205500\",\"employee_age\":\"39\",\"profile_image\":\"\"},{\"id\":\"10\",\"employee_name\":\"Sonya Frost\",\"employee_salary\":\"103600\",\"employee_age\":\"23\",\"profile_image\":\"\"},{\"id\":\"11\",\"employee_name\":\"Jena Gaines\",\"employee_salary\":\"90560\",\"employee_age\":\"30\",\"profile_image\":\"\"},{\"id\":\"12\",\"employee_name\":\"Quinn Flynn\",\"employee_salary\":\"342000\",\"employee_age\":\"22\",\"profile_image\":\"\"},{\"id\":\"13\",\"employee_name\":\"Charde Marshall\",\"employee_salary\":\"470600\",\"employee_age\":\"36\",\"profile_image\":\"\"},{\"id\":\"14\",\"employee_name\":\"Haley Kennedy\",\"employee_salary\":\"313500\",\"employee_age\":\"43\",\"profile_image\":\"\"},{\"id\":\"15\",\"employee_name\":\"Tatyana Fitzpatrick\",\"employee_salary\":\"385750\",\"employee_age\":\"19\",\"profile_image\":\"\"},{\"id\":\"16\",\"employee_name\":\"Michael Silva\",\"employee_salary\":\"198500\",\"employee_age\":\"66\",\"profile_image\":\"\"},{\"id\":\"17\",\"employee_name\":\"Paul Byrd\",\"employee_salary\":\"725000\",\"employee_age\":\"64\",\"profile_image\":\"\"},{\"id\":\"18\",\"employee_name\":\"Gloria Little\",\"employee_salary\":\"237500\",\"employee_age\":\"59\",\"profile_image\":\"\"},{\"id\":\"19\",\"employee_name\":\"Bradley Greer\",\"employee_salary\":\"132000\",\"employee_age\":\"41\",\"profile_image\":\"\"},{\"id\":\"20\",\"employee_name\":\"Dai Rios\",\"employee_salary\":\"217500\",\"employee_age\":\"35\",\"profile_image\":\"\"},{\"id\":\"21\",\"employee_name\":\"Jenette Caldwell\",\"employee_salary\":\"345000\",\"employee_age\":\"30\",\"profile_image\":\"\"},{\"id\":\"22\",\"employee_name\":\"Yuri Berry\",\"employee_salary\":\"675000\",\"employee_age\":\"40\",\"profile_image\":\"\"},{\"id\":\"23\",\"employee_name\":\"Caesar Vance\",\"employee_salary\":\"106450\",\"employee_age\":\"21\",\"profile_image\":\"\"},{\"id\":\"24\",\"employee_name\":\"Doris Wilder\",\"employee_salary\":\"85600\",\"employee_age\":\"23\",\"profile_image\":\"\"}]}",
            200
        )
        Mockito.`when`(
            httpHelper.send(
                RequestMethod.GET,
                "https://dummy.restapiexample.com/api/v1/employees"
            )
        ).thenReturn(response)
        viewModel.getAll()
        Mockito.verify(httpHelper)
            .send(RequestMethod.GET, "https://dummy.restapiexample.com/api/v1/employees")
        val employees = viewModel.employees.getOrAwaitValue()
        Assert.assertEquals(employees, mapper.readValue(response.body, EmployeeResponse::class.java).data)
        val responseCode = viewModel.responseCode.getOrAwaitValue()
        Assert.assertEquals(responseCode, response.code)
    }
}