package com.niranjan.khatri.kmptutorial.android.di

import android.app.Application
import com.niranjan.khatri.kmptutorial.di.AppDiContainer
import com.niranjan.khatri.kmptutorial.di.DiFactory

class CarApp : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppDiContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDiContainer(DiFactory(this))
    }
}