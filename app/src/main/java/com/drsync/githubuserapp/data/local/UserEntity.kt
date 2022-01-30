package com.drsync.githubuserapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity (
    @field:PrimaryKey
    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @field:ColumnInfo(name="favorited")
    var isFavorited: Boolean
)