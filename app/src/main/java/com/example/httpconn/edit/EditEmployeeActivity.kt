package com.example.httpconn.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.httpconn.Employee
import com.example.httpconn.R
import com.example.httpconn.databinding.ActivityEditEmployeeBinding

class EditEmployeeActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(EditViewModel::class.java) }
    lateinit var binding: ActivityEditEmployeeBinding
    private var employee: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_employee)
        employee = intent.getSerializableExtra("EMPLOYEE") as Employee
        updateUi()

        with(binding) {
            saveBtn.setOnClickListener {
                employee?.let {
                    viewModel.updateEmployee(
                        Employee(
                            it.id, name.text.toString(), salary.text.toString(), age.text.toString()
                        )
                    )
                }
            }
        }

        viewModel.responseCode.observe(this) {
            when (it) {
                200 -> {
                    Toast.makeText(this, "Successfully edited", Toast.LENGTH_SHORT).show()
                    finish()
                }
                404 -> Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
                429 -> Toast.makeText(this, "Too many request", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi() {
        binding.apply {
            name.setText(employee?.name)
            age.setText(employee?.age)
            salary.setText(employee?.salary)
        }
    }
}