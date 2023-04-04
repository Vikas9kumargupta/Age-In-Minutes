package com.example.agecalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById<Button>(R.id.btn1)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }
    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,{ _, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this,"Year was $selectedYear, Month is ${selectedMonth+1}, day of month was $selectedDayOfMonth",
                Toast.LENGTH_SHORT).show()
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            theDate?.let {
                val selectedDateInMinute = theDate.time / 60000
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000
                    val differenceInMinute = currentDateInMinutes - selectedDateInMinute
                    tvAgeInMinutes?.text = differenceInMinute.toString()
                }
            }
        },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
       dpd.show()

    }
}