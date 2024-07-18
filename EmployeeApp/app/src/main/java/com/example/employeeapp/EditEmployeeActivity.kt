package com.example.employeeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.employeeapp.database.Database
import com.google.android.material.textfield.TextInputEditText

class EditEmployeeActivity: AppCompatActivity() {


    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var ageEditText: TextInputEditText
    private lateinit var header: TextView
    private lateinit var radioGroupGender: RadioGroup
    private var employeeId: Int? = null
    private lateinit var databaseHelper: Database
    private var editing : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_employee)
        databaseHelper = Database(this)
        firstNameEditText = findViewById(R.id.first_name_input)
        lastNameEditText = findViewById(R.id.last_name_input)
        ageEditText = findViewById(R.id.age_input)
        radioGroupGender = findViewById(R.id.radio_group_gender)
        header = findViewById(R.id.textViewHeader)

        // Determine if editing or adding new
        employeeId = intent?.getIntExtra("EMPLOYEE_ID", -1)
        if (employeeId != null && employeeId != -1) {
            // Load employee data for editing
            editing = true
            loadEmployeeData(employeeId!!)
            header.setText(R.string.edit_employee)
        } else {
            header.setText(R.string.add_employee)
        }

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            saveEmployee()
        }
    }

    private fun loadEmployeeData(id: Int) {
        val employee = databaseHelper.getEmployeeById(id)
        Log.e("Employee", employee.toString())
        if(employee != null) {
            firstNameEditText.setText(employee.firstName)
            lastNameEditText.setText(employee.lastName)
            ageEditText.setText(employee.age.toString())
        }
    }

    private fun saveEmployee() {
        // Code to either update the existing employee or add a new one
        val selectedGenderId = radioGroupGender.checkedRadioButtonId
        val gender = if (selectedGenderId == R.id.radio_button_male) "M" else "F"
        val employeeToSaveOrEdit = Employee(employeeId ?: 0, firstNameEditText.text.toString(), lastNameEditText.text.toString(), ageEditText.text.toString().toInt(), gender)
        Log.e("EmployeeToSave", employeeToSaveOrEdit.toString())
        if (editing) {
            Log.e("Editing", employeeId.toString())
            databaseHelper.updateEmployee(employeeToSaveOrEdit)
        } else {
            databaseHelper.addEmployee(employeeToSaveOrEdit)
        }
//        navigate to homepage
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}