package com.pictures.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pictures.data.database.entity.PictureEntity

@Dao
interface PicturesDao {

    @Query("SELECT * FROM pictureEntity")
    fun getFavoritePictures(): PagingSource<Int,PictureEntity>

    @Query("SELECT * FROM pictureEntity WHERE id = :id")
    fun getPictureById(id: Int): PictureEntity

    @Query("SELECT id FROM pictureEntity")
    fun getIdListFavoritePictures (): List<Int>

    @Update
    fun update(pictureEntity: PictureEntity)

    @Insert
    fun insert(vararg pictureEntity: PictureEntity)

    @Query("DELETE FROM pictureEntity WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM pictureEntity")
    fun deleteAll()
}