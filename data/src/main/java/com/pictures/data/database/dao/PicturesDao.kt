package com.pictures.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pictures.data.database.entity.PictureEntity

@Dao
interface PicturesDao {

    //SQLite does not have a boolean data type.
    // Room maps it to an INTEGER column, mapping true to 1 and false to 0
    @Query("SELECT * FROM pictureEntity WHERE favorite = :favorite")
    fun getFavoritePictures(favorite: Boolean = true): List<PictureEntity>

    @Query("SELECT * FROM pictureEntity WHERE id = :id")
    fun getPicture(id: Int): PictureEntity

    @Update
    fun update(pictureEntity: PictureEntity)

    @Query("SELECT * FROM pictureEntity")
    fun getAllPicture(): List<PictureEntity>

    @Insert
    fun insert(vararg pictureEntity: PictureEntity)

    @Query("DELETE FROM pictureEntity WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM pictureEntity")
    fun deleteAll()
}