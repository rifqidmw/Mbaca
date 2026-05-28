package com.thelazyproject.mbaca.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NovelResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("authors")
    val authors: List<Author>?,

    @SerializedName("subjects")
    val subjects: List<String>?,

    @SerializedName("bookshelves")
    val bookshelves: List<String>?,

    @SerializedName("languages")
    val languages: List<String>?,

    @SerializedName("download_count")
    val downloadCount: Int?,

    @SerializedName("formats")
    val formats: Map<String, String>?
)

data class Author(
    @SerializedName("name")
    val name: String?,

    @SerializedName("birth_year")
    val birthYear: Int?,

    @SerializedName("death_year")
    val deathYear: Int?
)

data class BooksResponse(
    @SerializedName("count")
    val count: Int?,

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val results: List<NovelResponse>?
)

