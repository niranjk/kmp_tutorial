package com.niranjan.khatri.kmptutorial.di

import android.app.Application
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.niranjan.khatri.kmptutorial.datastore.AutoListStore
import com.niranjan.khatri.kmptutorial.room.CarDatabase
import com.niranjan.khatri.kmptutorial.room.dbFileName
import kotlinx.coroutines.Dispatchers

actual class DiFactory (private val app: Application){
    actual fun createRoomDatabase(): CarDatabase {
        val dbFile = app.getDatabasePath(dbFileName)
        return Room.databaseBuilder<CarDatabase>(
            context = app,
            name = dbFile.absolutePath,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    actual fun createAutoListDataStore(): AutoListStore {
        return AutoListStore {
            app.filesDir.resolve(
                "autostore.json",
            ).absolutePath
        }
    }
}