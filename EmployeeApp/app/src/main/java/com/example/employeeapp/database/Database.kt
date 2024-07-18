package com.example.employeeapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.employeeapp.Employee

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "employee.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_EMPLOYEES = "employees"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRST_NAME = "first_name"
        private const val COLUMN_LAST_NAME = "last_name"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_GENDER = "gender"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_EMPLOYEES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_GENDER + " TEXT" + ")")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEES")
        onCreate(db)
    }

    fun addEmployee(employee: Employee) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRST_NAME, employee.firstName)
            put(COLUMN_LAST_NAME, employee.lastName)
            put(COLUMN_AGE, employee.age)
            put(COLUMN_GENDER, employee.gender)
        }
        val result = db.insert(TABLE_EMPLOYEES, null, values)
        db.close()
        if (result == -1L) {
            Log.e("DBError", "Failed to insert data")
        } else {
            Log.d("DBSuccess", "Data inserted successfully")
        }
    }

    fun getAllEmployees(): ArrayList<Employee> {
        val employeeList = ArrayList<Employee>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EMPLOYEES", null)
        if (cursor.moveToFirst()) {
            do {
                val firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME)
                val lastNameIndex = cursor.getColumnIndex(COLUMN_LAST_NAME)
                val ageIndex = cursor.getColumnIndex(COLUMN_AGE)
                val genderIndex = cursor.getColumnIndex(COLUMN_GENDER)
                val idIndex = cursor.getColumnIndex(COLUMN_ID)

                // Ensure all indices are valid
                if (firstNameIndex != -1 && lastNameIndex != -1 && ageIndex != -1 && genderIndex != -1) {
                    val employee = Employee(
                        cursor.getInt(idIndex),
                        cursor.getString(firstNameIndex),
                        cursor.getString(lastNameIndex),
                        cursor.getInt(ageIndex),
                        cursor.getString(genderIndex)
                    )
                    employeeList.add(employee)
                } else {
                    // Handle the error or log as necessary
                    Log.e("EmployeeDatabase", "One or more columns are missing in the database.")
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return employeeList
    }

    fun updateEmployee(employee: Employee) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRST_NAME, employee.firstName)
            put(COLUMN_LAST_NAME, employee.lastName)
            put(COLUMN_AGE, employee.age)
            put(COLUMN_GENDER, employee.gender)
        }
        val success = db.update(
            TABLE_EMPLOYEES,
            values,
            "$COLUMN_ID = ?", arrayOf(employee.id.toString())
        )

        Log.d("EmployeeDatabaseEditEmployee", employee.toString())
        if(success == -1) {
            Log.e("DBError", "Failed to update data")
        } else {
            Log.d("DBSuccess", "Data updated successfully")
        }
        db.close()
    }

    fun getEmployeeById(id: Int): Employee? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EMPLOYEES WHERE $COLUMN_ID = $id", null)
        var employee: Employee? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME)
            val lastNameIndex = cursor.getColumnIndex(COLUMN_LAST_NAME)
            val ageIndex = cursor.getColumnIndex(COLUMN_AGE)
            val genderIndex = cursor.getColumnIndex(
                COLUMN_GENDER
            )
            if (firstNameIndex != -1 && lastNameIndex != -1 && ageIndex != -1 && genderIndex != -1) {
                employee = Employee(
                    cursor.getInt(idIndex),
                    cursor.getString(firstNameIndex),
                    cursor.getString(lastNameIndex),
                    cursor.getInt(ageIndex),
                    cursor.getString(genderIndex))

            } else {
                Log.e("EmployeeDatabase", "One or more columns are missing in the database.")
            }

        }
        cursor.close()
        db.close()
        return employee
    }

    // Function for testing purposes
    fun deleteAllEmployees() {
        val db = this.writableDatabase
        db.delete(TABLE_EMPLOYEES, null, null)
        db.close()
    }
}