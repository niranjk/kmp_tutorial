package com.niranjan.khatri.kmptutorial.di

import com.niranjan.khatri.kmptutorial.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class AppDiContainer(
    private val factory: DiFactory,
) {
    val dataRepository: DataRepository by lazy {
        DataRepository(
            database = factory.createRoomDatabase(),
            autoDataStore = factory.createAutoListDataStore(),
            scope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
        )
    }
}