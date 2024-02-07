package com.niranjan.khatri.kmptutorial.android.ui

import androidx.compose.runtime.Composable

// alias that takes a list of string and returns nothing
typealias OnAddType = (List<String>) -> Unit
// alias used when dismissing a dialog
typealias OnDismissType = () -> Unit
// a composable function
typealias composeFun = @Composable () -> Unit
// a composable function that takes an integer
typealias topBarFun = @Composable (Int) -> Unit

// an empty composable function
@Composable
fun EmptyComposable(){

}