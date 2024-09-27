package com.niranjan.khatri.kmptutorial.room

@androidx.room.Entity
data class Car(
    @androidx.room.PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val brand: String
)
