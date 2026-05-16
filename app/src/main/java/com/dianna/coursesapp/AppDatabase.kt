package com.dianna.coursesapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCourseEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteCourseDao(): FavoriteCourseDao
}