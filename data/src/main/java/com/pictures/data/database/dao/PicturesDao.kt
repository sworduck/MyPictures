package com.pictures.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pictures.data.database.entity.PictureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PicturesDao {

    @Query("SELECT * FROM pictureEntity")
    fun getAllPicture(): List<PictureEntity>

    @Query("SELECT * FROM pictureEntity")
    fun getAllPicturePaging(): PagingSource<Int,PictureEntity>

    @Query("SELECT * FROM pictureEntity WHERE favorite = 1")
    fun getFavoritePictures(): PagingSource<Int,PictureEntity>

    @Query("SELECT * FROM pictureEntity WHERE id = :id")
    fun getPictureById(id: Int): PictureEntity

    @Query("SELECT id FROM pictureEntity")
    fun getIdListFavoritePictures ():  Flow<List<Int>>

    @Update
    fun update(pictureEntity: PictureEntity)

    @Insert
    fun insert(vararg pictureEntity: PictureEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pictures: List<PictureEntity>)

    @Query("DELETE FROM pictureEntity WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM pictureEntity")
    fun deleteAll()
}