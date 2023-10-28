package com.pictures.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureEntity(
    val id: Long,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val favorite: Boolean,
    val downloadUrl: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}