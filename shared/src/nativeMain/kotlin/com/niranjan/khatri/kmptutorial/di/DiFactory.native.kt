package com.niranjan.khatri.kmptutorial.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.niranjan.khatri.kmptutorial.datastore.AutoListStore
import com.niranjan.khatri.kmptutorial.room.CarDatabase
import com.niranjan.khatri.kmptutorial.room.dbFileName
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DiFactory {
    actual fun createRoomDatabase(): CarDatabase {
        val dbFile = "${fileDirectory()}/$dbFileName"
        return Room.databaseBuilder<CarDatabase>(
            name = dbFile,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    actual fun createAutoListDataStore(): AutoListStore {
        return AutoListStore {
            "${fileDirectory()}/autostore.json"
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path!!
    }
}