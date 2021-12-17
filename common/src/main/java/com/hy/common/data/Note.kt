package com.hy.common.data

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.BLOB
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author Lenovo
 */
@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    @ColumnInfo(name = "content") var content:String = "",
    @ColumnInfo(name = "type") var type:Int = 0,
    @ColumnInfo(name="title") var title:String = "",
    @ColumnInfo(name = "create_time") var createTime:Long = 0,
    @ColumnInfo(name = "modify_time") var modify_time:Long = 0,
    @ColumnInfo(name="author") var userId:String = "",
    @ColumnInfo(name="cover") var cover:Int = 0,
    @ColumnInfo(name = "delete") var delete:Int = 0
)