package com.thelazyproject.mbaca.ui.model

import com.thelazyproject.mbaca.core.domain.model.Novel

data class NovelUiModel(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val image: String,
    val publishedDate: String,
    val pageCount: Int,
    val category: String,
    val rating: Double,
    val isFavorite: Boolean = false,
    val formattedRating: String = "★ $rating",
    val shortDescription: String = if (description.length > 150)
        "${description.take(150)}..."
        else description,
    val displayAuthor: String = "by $author",
    val displayPages: String = "$pageCount pages"
)

fun Novel.toUiModel(): NovelUiModel {
    return NovelUiModel(
        id = this.id,
        title = this.title,
        author = this.author,
        description = this.description,
        image = this.image,
        publishedDate = this.publishedDate,
        pageCount = this.pageCount,
        category = this.category,
        rating = this.rating,
        isFavorite = this.isFavorite
    )
}

fun NovelUiModel.toDomain(): Novel {
    return Novel(
        id = this.id,
        title = this.title,
        author = this.author,
        description = this.description,
        image = this.image,
        publishedDate = this.publishedDate,
        pageCount = this.pageCount,
        category = this.category,
        rating = this.rating,
        isFavorite = this.isFavorite
    )
}

fun List<Novel>.toUiModels(): List<NovelUiModel> {
    return this.map { it.toUiModel() }
}

