package com.pictures.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pictures.data.database.dao.PicturesDao
import com.pictures.data.database.entity.PictureEntity

@Database(entities = [PictureEntity::class], version = 2)
abstract class MyPictureDb: RoomDatabase() {

    companion object{
        const val DATABASE_NAME = "MyPictureDb"
    }

    abstract fun picturesDao(): PicturesDao
}