package com.hy.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bedone")
data class Bedone(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "type") var type: Int = 0,
    @ColumnInfo(name = "finished") var finished: Int = 0,
    @ColumnInfo(name = "create_time") var createTime: Long = 0,
    @ColumnInfo(name = "modify_time") var modify_time: Long = 0,
    @ColumnInfo(name = "author") var userId: String = "",
    @ColumnInfo(name = "deleteFlag") var deleteFlag: Int = 0
)