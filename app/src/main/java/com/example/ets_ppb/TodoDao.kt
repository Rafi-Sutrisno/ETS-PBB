package com.example.ets_ppb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import androidx.room.OnConflictStrategy


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * from todo_table order by id ASC")
    fun getAllTodos(): LiveData<List<Todo>>

    @Query("SELECT COUNT(*) FROM todo_table WHERE status = 1")
    fun getCompletedCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM todo_table WHERE status = 0")
    fun getNotCompletedCount(): LiveData<Int>

    @Query("UPDATE todo_table set title = :title, note = :note, deadline = :deadline, status = :status where id = :id")
    suspend fun update(id: Int?, title: String?, note: String?, deadline: String?, status: Int)
}

