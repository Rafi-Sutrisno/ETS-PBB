package com.example.ets_ppb
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ets_ppb.Todo
import com.example.ets_ppb.databinding.ActivityAddTodoBinding
import java.text.SimpleDateFormat
import java.util.*

class AddTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var todo: Todo
    private lateinit var oldTodo: Todo
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        try {
            oldTodo = intent.getSerializableExtra("current_todo") as Todo
            binding.etTitle.setText(oldTodo.title)
            binding.etNote.setText(oldTodo.note)
            binding.etDeadline.setText(oldTodo.deadline) // add deadline field
            binding.cbStatus.isChecked = oldTodo.status == 1 // add checkbox for status
            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (isUpdate) {
            binding.imgDelete.visibility = View.VISIBLE
        } else {
            binding.imgDelete.visibility = View.INVISIBLE
        }

        // Date Picker for Ending Date
        binding.etDeadline.setOnClickListener {
            showDatePickerDialog()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val todoDescription = binding.etNote.text.toString()
            val deadline = binding.etDeadline.text.toString() // get deadline from the input
            val status = if (binding.cbStatus.isChecked) 1 else 0 // get status from the checkbox

            if (title.isNotEmpty() && todoDescription.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate) {
                    todo = Todo(
                        oldTodo.id, title, todoDescription, formatter.format(Date()), deadline, status
                    )
                } else {
                    todo = Todo(null, title, todoDescription, formatter.format(Date()), deadline, status)
                }

                val intent = Intent()
                intent.putExtra("todo", todo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddTodoActivity, "please enter some data", Toast.LENGTH_LONG).show()
            }
        }

        binding.imgDelete.setOnClickListener {
            val intent = Intent()
            intent.putExtra("todo", oldTodo)
            intent.putExtra("delete_todo", true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showDatePickerDialog() {
        // Get current date to pre-fill the date picker (for new tasks)
        val calendar = Calendar.getInstance()

        // If we have an existing task with a deadline, set the calendar to that date
        val existingDeadline = binding.etDeadline.text.toString()
        if (existingDeadline.isNotEmpty()) {
            val parts = existingDeadline.split("/")
            if (parts.size == 3) {
                val day = parts[0].toInt()
                val month = parts[1].toInt() - 1 // months are 0-based in Calendar
                val year = parts[2].toInt()

                calendar.set(year, month, day)
            }
        }

        // Get the year, month, and day from the calendar
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // Format the selected date to a readable format
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etDeadline.setText(selectedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

}
