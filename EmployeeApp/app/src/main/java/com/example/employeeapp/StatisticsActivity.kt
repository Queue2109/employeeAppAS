package com.example.employeeapp

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.employeeapp.database.Database
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class StatisticsActivity: AppCompatActivity() {
    private lateinit var allEmployees: List<Employee>
    private lateinit var databaseHelper: Database
    private lateinit var pieChart: PieChart
    private lateinit var avgAgeTextView: TextView
    private lateinit var backButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        databaseHelper = Database(this)
        allEmployees = databaseHelper.getAllEmployees()
        preparePieChart()
        avgAgeTextView = findViewById(R.id.avg_age_text_view)
        val age = calculateAvgAge()
        avgAgeTextView.text = age
        backButton = findViewById<FloatingActionButton>(R.id.back_button)

        backButton.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
    }

    private fun calculateAvgAge(): String {
        val averageAge = allEmployees.map { it.age }.average()
        return String.format(Locale.GERMAN, "%.1f", averageAge)
    }

    private fun preparePieChart() {
        pieChart = findViewById(R.id.pieChart)

        val numberOfMen = allEmployees.count { it.gender == "M" }
        val numberOfWomen = allEmployees.count { it.gender == "F" }

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(numberOfMen.toFloat(), "Men"))
        entries.add(PieEntry(numberOfWomen.toFloat(), "Women"))

        val dataSet = PieDataSet(entries, "")
        val colorYellow = ContextCompat.getColor(this, R.color.yellow)
        val colorSweetRed = ContextCompat.getColor(this, R.color.sweet_red)
        dataSet.colors = listOf(colorYellow, colorSweetRed)
        dataSet.valueTextSize = 16f
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueFormatter = PercentFormatter(pieChart)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.centerText = "Gender \n Distribution"
        pieChart.setCenterTextSize(16f)
        pieChart.setCenterTextColor(ContextCompat.getColor(this, R.color.dark_blue))
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = false

        val legend = pieChart.legend
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.isWordWrapEnabled = true
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.textSize = 16f
        legend.textColor = ContextCompat.getColor(this, R.color.text_primary)
        pieChart.invalidate() // refresh
    }
}