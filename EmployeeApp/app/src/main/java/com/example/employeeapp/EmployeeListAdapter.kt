package com.example.employeeapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.database.Database
import com.google.android.material.snackbar.Snackbar
class EmployeeListAdapter(private val employeeList: ArrayList<Employee>) : RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder>() {
    private lateinit var databaseHolder: Database
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_view, parent, false)
        databaseHolder = Database(parent.context)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = employeeList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = employeeList[position]
        holder.bindItems(currentItem)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditEmployeeActivity::class.java)
            intent.putExtra("EMPLOYEE_ID", currentItem.id)
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val removedItem = employeeList[position]

            employeeList.removeAt(position)
            notifyItemRemoved(position)
            databaseHolder.deleteEmployeeById(removedItem.id)

            Snackbar.make(holder.itemView, "Employee deleted", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    employeeList.add(position, removedItem)
                    notifyItemInserted(position)
                    databaseHolder.addEmployee(removedItem)
                }
                show()
            }
            true
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val employeeDataTextView: TextView = itemView.findViewById(R.id.employeeData)

        fun bindItems(employee: Employee) {
            val employeeText = "${employee.firstName} ${employee.lastName} (${employee.gender}), ${employee.age}"
            employeeDataTextView.text = employeeText
        }
    }
}

