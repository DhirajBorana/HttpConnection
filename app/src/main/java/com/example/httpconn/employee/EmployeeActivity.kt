package com.example.httpconn.employee

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.httpconn.edit.EditEmployeeActivity
import com.example.httpconn.Employee
import com.example.httpconn.R
import com.example.httpconn.databinding.ActivityEmployeeBinding


class EmployeeActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(EmployeeViewModel::class.java) }
    lateinit var binding: ActivityEmployeeBinding
    private var employee: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee)
        employee = intent.getSerializableExtra("EMPLOYEE") as Employee
        binding.employee = employee

        viewModel.responseCode.observe(this) {
            when (it) {
                200 -> {
                    Toast.makeText(this, "Successfully deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                404 -> Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
                429 -> Toast.makeText(this, "Too many request", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.employee, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.edit -> {
                val intent = Intent(this, EditEmployeeActivity::class.java)
                intent.putExtra("EMPLOYEE", employee)
                startActivity(intent)
                true
            }
            R.id.delete -> {
                employee?.id?.let { viewModel.deleteEmployee(it) }
                true
            }
            else ->  false
        }
    }
}