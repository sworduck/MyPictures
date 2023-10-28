package com.pictures.domain

data class PictureData(
    val author: String,
    val downloadUrl: String,
    val height: Int,
    val id: Long,
    val url: String,
    val width: Int,
    var favorite: Boolean
)