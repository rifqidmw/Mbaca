package com.thelazyproject.mbaca.core.domain.model

data class Novel(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val image: String,
    val publishedDate: String,
    val pageCount: Int,
    val category: String,
    val rating: Double,
    val isFavorite: Boolean = false
)

