package com.example.ets_ppb

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ets_ppb.R
import com.example.ets_ppb.Todo

class TodoAdapter(private val context: Context, val listener: TodoClickListener) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val todoList = ArrayList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList[position]

        // Binding title, note, and deadline
        holder.title.text = item.title
        holder.note.text = item.note
        holder.deadline.text = item.deadline // Display the deadline

        // Set CheckBox status based on the 'status' of the Todo
        holder.statusCheckBox.isChecked = item.status == 1

        // Listener for task click (when user taps on the whole card)
        holder.todoLayout.setOnClickListener {
            listener.onItemClicked(todoList[holder.adapterPosition])
        }

        // Listener for CheckBox state change
        holder.statusCheckBox.setOnCheckedChangeListener { _, isChecked ->
            item.status = if (isChecked) 1 else 0 // 1 for checked (completed), 0 for unchecked (not completed)
            listener.onStatusChanged(item) // Notify status change
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateList(newList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoLayout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val deadline = itemView.findViewById<TextView>(R.id.tv_deadline) // Deadline TextView
        val statusCheckBox = itemView.findViewById<CheckBox>(R.id.cb_status) // Status CheckBox
    }

    interface TodoClickListener {
        fun onItemClicked(todo: Todo)
        fun onStatusChanged(todo: Todo) // New listener for status change
    }
}
