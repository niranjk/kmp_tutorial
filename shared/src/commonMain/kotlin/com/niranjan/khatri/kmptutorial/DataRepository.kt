package com.niranjan.khatri.kmptutorial


import com.niranjan.khatri.kmptutorial.datastore.AutoListStore
import com.niranjan.khatri.kmptutorial.model.CarItemDetails
import com.niranjan.khatri.kmptutorial.room.Car
import com.niranjan.khatri.kmptutorial.room.CarDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class DataRepository(
    private var database: CarDatabase,
    private val autoDataStore: AutoListStore,
    private val scope: CoroutineScope,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val carDetails: Flow<List<CarItemDetails>>
        get() = autoDataStore.cart.mapLatest { it ->
            val ids = it.items.map { it.id }
            val cars : Map<Int, Car> = database.carDao().loadMapped(ids)
            it.items.mapNotNull {
                cars[it.id]?.let { car ->
                    CarItemDetails(car, it.count)
                }
            }
        }

    suspend fun addToCart(car: Car) {
        autoDataStore.add(car)
    }

    fun getData(): Flow<List<Car>> {
        scope.launch {
            if (database.carDao().count() < 1) {
                refreshData()
            }
        }
        return loadData()
    }

    fun loadData(): Flow<List<Car>> {
        return database.carDao().getAllAsFlow()
    }

    suspend fun refreshData() {
        val response = mockCars
        database.carDao().insert(response)
    }
}

data class Car(
    @androidx.room.PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val brand: String
)

val mockCars = listOf(
    Car(id = 1, name = "Civic", brand = "Honda"),
    Car(id = 2, name = "Accord", brand = "Honda"),
    Car(id = 3, name = "Camry", brand = "Toyota"),
    Car(id = 4, name = "Corolla", brand = "Toyota"),
    Car(id = 5, name = "Model 3", brand = "Tesla"),
    Car(id = 6, name = "Model S", brand = "Tesla"),
    Car(id = 7, name = "Mustang", brand = "Ford"),
    Car(id = 8, name = "F-150", brand = "Ford"),
    Car(id = 9, name = "Golf", brand = "Volkswagen"),
    Car(id = 10, name = "Jetta", brand = "Volkswagen")
)