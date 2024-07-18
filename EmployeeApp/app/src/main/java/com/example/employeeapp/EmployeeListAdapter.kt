import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.Employee
import com.example.employeeapp.EditEmployeeActivity
import com.example.employeeapp.R

class EmployeeListAdapter(private val employeeList: ArrayList<Employee>) : RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = employeeList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = employeeList[position]
        holder.bindItems(currentItem)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditEmployeeActivity::class.java)
            Log.d("Employee ID", currentItem.id.toString())
            intent.putExtra("EMPLOYEE_ID", currentItem.id)
            context.startActivity(intent)
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
