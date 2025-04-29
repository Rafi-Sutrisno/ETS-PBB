package com.example.ets_ppb

import androidx.lifecycle.LiveData
import com.example.ets_ppb.Todo

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<Todo>> = todoDao.getAllTodos()
    val completedCount: LiveData<Int> = todoDao.getCompletedCount()
    val notCompletedCount: LiveData<Int> = todoDao.getNotCompletedCount()


    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: Todo) {
        todoDao.update(todo.id, todo.title, todo.note, todo.deadline, todo.status)
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }
}
