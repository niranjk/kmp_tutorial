package com.niranjan.khatri.kmptutorial.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: Car)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listCar: List<Car>)

    @Query("SELECT * FROM Car")
    fun getAllAsFlow(): Flow<List<Car>>

    @Query("SELECT COUNT(*) as count FROM Car")
    suspend fun count(): Int

    @Query("SELECT * FROM Car WHERE id in (:ids)")
    suspend fun loadAll(ids: List<Long>): List<Car>

    @Query("SELECT * FROM Car WHERE id in (:ids)")
    suspend fun loadMapped(ids: List<Int>): Map<@MapColumn(columnName = "id") Int, Car>
}