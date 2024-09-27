package com.niranjan.khatri.kmptutorial.di

import com.niranjan.khatri.kmptutorial.datastore.AutoListStore
import com.niranjan.khatri.kmptutorial.room.CarDatabase
import kotlinx.serialization.json.Json

expect class DiFactory {
    fun createRoomDatabase(): CarDatabase
    fun createAutoListDataStore(): AutoListStore
}

val json = Json { ignoreUnknownKeys = true }