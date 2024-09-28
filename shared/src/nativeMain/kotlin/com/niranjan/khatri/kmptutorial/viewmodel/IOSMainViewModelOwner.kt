package com.niranjan.khatri.kmptutorial.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.niranjan.khatri.kmptutorial.di.AppDiContainer

/**
 * This class acts as a container for ViewModels in an iOS application
 * built with Kotlin Multiplatform (KMP).
 * It's similar to how ViewModels are managed in Android, but adapted for iOS.
 * This class enables the use of ViewModels in iOS within a KMP project.
 * It manages the lifecycle of ViewModels, ensuring they are created and cleared appropriately.
 * It provides a way to access specific ViewModels through properties like mainViewModel.
 */
@Suppress("unused") // Android Studio is not aware of iOS usage.
class IOSMainViewModelOwner(appContainer: AppDiContainer) : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()

    // Here we create an instance of MainViewModel with the CreationExtras.
    val mainViewModel: MainViewModel = ViewModelProvider.create(
        owner = this as ViewModelStoreOwner,
        factory = MainViewModel.Factory,
        extras = MainViewModel.newCreationExtras(appContainer),
    )[MainViewModel::class]

    // To add more ViewModels if needed, we do it either by creating new properties or using reflection for a large number of ViewModels.

    // clears the ViewModelStore, releasing the ViewModels when the owner is no longer needed.
    fun clear() {
        viewModelStore.clear()
    }
}