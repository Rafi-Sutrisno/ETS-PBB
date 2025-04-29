package com.example.ets_ppb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ets_ppb.TodoDatabase
import com.example.ets_ppb.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoRepository: TodoRepository
    val allTodo: LiveData<List<Todo>>
    val completedCount: LiveData<Int>
    val notCompletedCount: LiveData<Int>

    init {
        val todoDao = TodoDatabase.getDatabase(application).getTodoDao()
        todoRepository = TodoRepository(todoDao)
        allTodo = todoRepository.allTodos
       completedCount = todoRepository.completedCount
        notCompletedCount = todoRepository.notCompletedCount
    }

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.insert(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.update(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.delete(todo)
        }
    }
}
