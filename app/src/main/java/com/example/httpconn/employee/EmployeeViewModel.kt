package com.example.httpconn.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httpconn.Employee
import com.example.httpconn.HttpHelper
import com.example.httpconn.RequestMethod
import com.google.gson.Gson
import kotlinx.coroutines.launch

class EmployeeViewModel : ViewModel() {
    
    private val _responseCode = MutableLiveData<Int>()
    val responseCode: LiveData<Int>
        get() = _responseCode


    fun deleteEmployee(id: String) {
        viewModelScope.launch {
            _responseCode.value =
                HttpHelper.send(RequestMethod.DELETE, "https://dummy.restapiexample.com/api/v1/delete/$id").code
        }
    }
}