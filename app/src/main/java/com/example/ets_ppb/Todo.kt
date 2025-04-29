package com.example.ets_ppb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "deadline") val deadline: String?, // new column for deadline
    @ColumnInfo(name = "status") var status: Int // new column for status (1 or 0)
): java.io.Serializable
