package com.hy.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @auther:hanshengjian
 * @date:2021/12/8
 * 笔记类型
 */
@Entity(tableName = "dic_type")
data class DicType(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    @ColumnInfo(name = "type_name") var content:String = "",
    @ColumnInfo(name = "page") var page:Int = 0, //0：笔记，1：代办
    @ColumnInfo(name = "create_time") var createTime:Long = 0,
    @ColumnInfo(name = "modify_time") var modify_time:Long = 0,
    @ColumnInfo(name="author") var userId:String = "",
    @ColumnInfo(name="parent") var parentType:Int = 0,
    @ColumnInfo(name = "size") var size:Int = 0
)