package com.example.employeeapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.database.Database
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity: AppCompatActivity() {
    private lateinit var employeeRecyclerView : RecyclerView
    private lateinit var employeeList : ArrayList<Employee>
    private lateinit var databaseHelper: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        employeeRecyclerView = findViewById(R.id.employeeRecyclerView)
        databaseHelper = Database(this)
        getUserData()

        // Go to EditEmployeeActivity when button is clicked
        val addEmployeeButton = findViewById<FloatingActionButton>(R.id.addEmployeeButton)
        addEmployeeButton.setOnClickListener {
            val intent = Intent(this, EditEmployeeActivity::class.java)
            startActivity(intent)
        }

        // Go to StatisticsActivity when button is clicked
        val statisticsButton = findViewById<FloatingActionButton>(R.id.statisticsButton)
        statisticsButton.setOnClickListener {
            if(employeeList.size > 0) {
                val intent = Intent(this, StatisticsActivity::class.java)
                startActivity(intent)
            } else {
                Utils.showAlert("No employees yet", "To see statistics, one or more employees must exist", this)
            }
        }
    }

    private fun getUserData() {
        employeeList = databaseHelper.getAllEmployees()
        employeeRecyclerView.layoutManager = LinearLayoutManager(this)
        employeeRecyclerView.adapter = EmployeeListAdapter(employeeList)
    }

}