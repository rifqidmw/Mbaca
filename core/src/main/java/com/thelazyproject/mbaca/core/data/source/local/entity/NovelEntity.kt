package com.thelazyproject.mbaca.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novels")
data class NovelEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "publishedDate")
    val publishedDate: String,

    @ColumnInfo(name = "pageCount")
    val pageCount: Int,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)

