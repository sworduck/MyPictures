package com.pictures.data

import com.pictures.data.database.entity.PictureEntity
import com.pictures.data.network.data.Picture
import com.pictures.domain.PictureData

fun PictureData.toPictureEntity(): PictureEntity {
    return PictureEntity(
        id = id,
        author = author,
        width = width,
        height = height,
        url = url,
        favorite = favorite,
        downloadUrl = downloadUrl
    )
}

fun PictureEntity.toPictureData(): PictureData {
    return PictureData(
        id = id,
        author = author,
        width = width,
        height = height,
        url = url,
        favorite = favorite,
        downloadUrl = downloadUrl
    )
}

fun Picture.toPictureData(): PictureData {
    return PictureData(
        id = id.toLong(),
        author = author,
        width = width,
        height = height,
        url = url,
        favorite = false,
        downloadUrl = downloadUrl
    )
}