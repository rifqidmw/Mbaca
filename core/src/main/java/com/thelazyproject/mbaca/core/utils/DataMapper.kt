package com.thelazyproject.mbaca.core.utils

import com.thelazyproject.mbaca.core.data.source.local.entity.NovelEntity
import com.thelazyproject.mbaca.core.data.source.remote.response.NovelResponse
import com.thelazyproject.mbaca.core.domain.model.Novel

object DataMapper {

    fun mapResponsesToEntities(responses: List<NovelResponse>): List<NovelEntity> {
        return responses.map { response ->
            val authorName = response.authors?.firstOrNull()?.name ?: "Unknown Author"
            val imageUrl = response.formats?.get("image/jpeg") ?: ""
            val category = response.subjects?.firstOrNull() ?:
                          response.bookshelves?.firstOrNull() ?: "Fiction"
            val downloadCount = response.downloadCount ?: 0
            val rating = if (downloadCount > 0) {
                minOf(5.0, (downloadCount / 1000.0).coerceAtLeast(1.0))
            } else {
                0.0
            }

            NovelEntity(
                id = response.id.toString(),
                title = response.title ?: "Unknown Title",
                author = authorName,
                description = buildDescription(response),
                image = imageUrl,
                publishedDate = getPublishedYear(response),
                pageCount = 0,
                category = category,
                rating = rating,
                isFavorite = false
            )
        }
    }

    private fun buildDescription(response: NovelResponse): String {
        val parts = mutableListOf<String>()

        response.authors?.take(3)?.forEach { author ->
            val years = if (author.birthYear != null || author.deathYear != null) {
                val birth = author.birthYear?.toString() ?: "?"
                val death = author.deathYear?.toString() ?: "?"
                " ($birth-$death)"
            } else ""
            parts.add("${author.name}$years")
        }

        if (parts.isNotEmpty()) {
            parts.add("")
        }

        response.subjects?.take(5)?.let { subjects ->
            parts.add("Subjects: ${subjects.joinToString(", ")}")
        }

        response.languages?.let { languages ->
            parts.add("Languages: ${languages.joinToString(", ")}")
        }

        response.downloadCount?.let { count ->
            parts.add("Downloads: $count")
        }

        return parts.joinToString("\n").ifEmpty { "No description available" }
    }

    private fun getPublishedYear(response: NovelResponse): String {
        val author = response.authors?.firstOrNull()
        return when {
            author?.birthYear != null -> "${author.birthYear}"
            author?.deathYear != null -> "Before ${author.deathYear}"
            else -> "Unknown"
        }
    }

    fun mapEntitiesToDomain(entities: List<NovelEntity>): List<Novel> {
        return entities.map { entity ->
            Novel(
                id = entity.id,
                title = entity.title,
                author = entity.author,
                description = entity.description,
                image = entity.image,
                publishedDate = entity.publishedDate,
                pageCount = entity.pageCount,
                category = entity.category,
                rating = entity.rating,
                isFavorite = entity.isFavorite
            )
        }
    }

    fun mapResponsesToDomain(responses: List<NovelResponse>): List<Novel> {
        return responses.map { response ->
            val authorName = response.authors?.firstOrNull()?.name ?: "Unknown Author"
            val imageUrl = response.formats?.get("image/jpeg") ?: ""
            val category = response.subjects?.firstOrNull() ?:
                          response.bookshelves?.firstOrNull() ?: "Fiction"
            val downloadCount = response.downloadCount ?: 0
            val rating = if (downloadCount > 0) {
                minOf(5.0, (downloadCount / 1000.0).coerceAtLeast(1.0))
            } else {
                0.0
            }

            Novel(
                id = response.id.toString(),
                title = response.title ?: "Unknown Title",
                author = authorName,
                description = buildDescription(response),
                image = imageUrl,
                publishedDate = getPublishedYear(response),
                pageCount = 0,
                category = category,
                rating = rating,
                isFavorite = false
            )
        }
    }

    fun mapEntityToDomain(entity: NovelEntity): Novel {
        return Novel(
            id = entity.id,
            title = entity.title,
            author = entity.author,
            description = entity.description,
            image = entity.image,
            publishedDate = entity.publishedDate,
            pageCount = entity.pageCount,
            category = entity.category,
            rating = entity.rating,
            isFavorite = entity.isFavorite
        )
    }
}

