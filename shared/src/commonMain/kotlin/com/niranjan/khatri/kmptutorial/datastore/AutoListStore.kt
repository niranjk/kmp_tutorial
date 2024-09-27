package com.niranjan.khatri.kmptutorial.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.niranjan.khatri.kmptutorial.room.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.Json.Default.encodeToString
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.use

@Serializable
data class AutoStore(
    val items: List<AutoItem>,
)

@Serializable
data class AutoItem(
    val id: Int,
    val count: Int,
)
internal object CartJsonSerializer : OkioSerializer<AutoStore> {
    override val defaultValue: AutoStore = AutoStore(emptyList())
    override suspend fun readFrom(source: BufferedSource): AutoStore {
        return decodeFromString<AutoStore>(source.readUtf8())
    }
    override suspend fun writeTo(t: AutoStore, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(encodeToString(AutoStore.serializer(), t))
        }
    }
}

class AutoListStore(
    private val produceFilePath: () -> String,
) {
    private val db = DataStoreFactory.create(
        storage = OkioStorage<AutoStore>(
            fileSystem = FileSystem.SYSTEM,
            serializer = CartJsonSerializer,
            producePath = { produceFilePath().toPath() },
        ),
    )
    val cart: Flow<AutoStore>
        get() = db.data
    suspend fun add(car: Car) = update(car, 1)
    suspend fun remove(car: Car) = update(car, -1)
    suspend fun update(car: Car, diff: Int) {
        db.updateData { prevCart ->
            val newItems = mutableListOf<AutoItem>()
            var found = false
            prevCart.items.forEach {
                if (it.id == car.id) {
                    found = true
                    newItems.add(
                        it.copy(
                            count = it.count + diff,
                        ),
                    )
                } else {
                    newItems.add(it)
                }
            }
            if (!found) {
                newItems.add(
                    AutoItem(id = car.id, count = diff),
                )
            }
            newItems.removeAll {
                it.count <= 0
            }
            AutoStore(
                items = newItems,
            )
        }
    }
}