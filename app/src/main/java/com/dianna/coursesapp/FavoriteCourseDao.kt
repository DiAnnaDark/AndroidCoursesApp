package com.dianna.coursesapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteCourseDao {

    @Query("SELECT * FROM favorite_courses")
    suspend fun getAllFavorites(): List<FavoriteCourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(course: FavoriteCourseEntity)

    @Delete
    suspend fun deleteFavorite(course: FavoriteCourseEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_courses WHERE id = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean
}