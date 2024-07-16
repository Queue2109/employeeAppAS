package com.example.employeeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeListAdapter(private val employeeList:ArrayList<Employee>) : RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Employee  = employeeList[position]
        holder.bindItems(currentItem)
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(employee: Employee) {
            val employeeData = employee.first_name + " " + employee.last_name + " (" + employee.gender + ")" + ", " + employee.age
            itemView.findViewById<TextView>(R.id.employeeData).text = employeeData

        }
    }
}