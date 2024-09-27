package com.niranjan.khatri.kmptutorial.room


import androidx.room.ConstructedBy
import androidx.room.RoomDatabaseConstructor

@androidx.room.Database(entities = [Car::class], version = 1)
@ConstructedBy(CarDatabaseConstructor::class)
abstract class CarDatabase : androidx.room.RoomDatabase() {
    abstract fun carDao(): CarDao
}

/**
 * An expect/actual class for the database constructor so that Room can generate
 * the implementation.
 * // Note* Do not manually implement the actuals it is all autogenerated by Room DB
 */
// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CarDatabaseConstructor : RoomDatabaseConstructor<CarDatabase> {
    override fun initialize(): CarDatabase
}
internal const val dbFileName = "Car.db"