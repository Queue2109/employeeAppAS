package com.example.employeeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity: AppCompatActivity() {
    var headerText = "Employee App"
    private lateinit var employeeRecyclerView : RecyclerView
    private lateinit var employeeList : ArrayList<Employee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeRecyclerView = findViewById(R.id.employeeRecyclerView)
        getUserData()
    }

    fun getUserData() {
        employeeList = ArrayList()
        employeeList.add(Employee("John", "Doe", 25, "M"))
        employeeList.add(Employee("Jane", "Doe", 22, "F"))
        employeeList.add(Employee("James", "Smith", 30, "M"))
        employeeList.add(Employee("Janet", "Smith", 27, "F"))

        employeeRecyclerView.layoutManager = LinearLayoutManager(this)
        employeeRecyclerView.adapter = EmployeeListAdapter(employeeList)
    }

}