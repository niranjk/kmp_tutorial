package com.niranjan.khatri.kmptutorial.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.niranjan.khatri.kmptutorial.DataRepository
import com.niranjan.khatri.kmptutorial.di.AppDiContainer
import com.niranjan.khatri.kmptutorial.model.CarItemDetails
import com.niranjan.khatri.kmptutorial.room.Car
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DataRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        repository.getData().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState(),
            )

    val cartUiState: StateFlow<CartUiState> =
        repository.carDetails.map { CartUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CartUiState(),
            )

    fun addItemToCart(car: Car) {
        viewModelScope.launch {
            repository.addToCart(car)
        }
    }

    companion object {

        val APP_CONTAINER_KEY = CreationExtras.Key<AppDiContainer>()

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = this[APP_CONTAINER_KEY] as AppDiContainer
                val repository = appContainer.dataRepository
                MainViewModel(repository = repository)
            }
        }

        /**
         * Helper function to prepare CreationExtras.
         *
         * USAGE:
         *
         * val mainViewModel: MainViewModel = ViewModelProvider.create(
         *  owner = this as ViewModelStoreOwner,
         *  factory = MainViewModel.Factory,
         *  extras = MainViewModel.newCreationExtras(appContainer),
         * )[MainViewModel::class]
         */
        fun newCreationExtras(appContainer: AppDiContainer): CreationExtras =
            MutableCreationExtras().apply {
                set(APP_CONTAINER_KEY, appContainer)
            }
    }
}

/**
 * Ui State for the home screen
 */
data class HomeUiState(val carList: List<Car> = listOf())

/**
 * Ui State for the cart
 */
data class CartUiState(val cartDetails: List<CarItemDetails> = listOf())

private const val TIMEOUT_MILLIS = 5_000L