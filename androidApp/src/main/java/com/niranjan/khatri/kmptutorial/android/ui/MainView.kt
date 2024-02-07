package com.niranjan.khatri.kmptutorial.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.niranjan.khatri.kmptutorial.android.MyApplicationTheme

/**
 * Keep Track of your Two Screens
 */
sealed class Screen(val title: String){
    object TimeZoneScreen : Screen("TimeZone")
    object TimeSearchScreen : Screen("TimeSearch")
}

/***
 * Class to handle Bottom Navigation Item
 */
data class BottomItem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

/**
 * Create bottom navigation items
 */
val bottomNavigationItems = listOf(
    BottomItem(
        Screen.TimeZoneScreen.title,
        Icons.Filled.LocationOn,
        "TimeZone"
    ),
    BottomItem(
        Screen.TimeSearchScreen.title,
        Icons.Filled.Search,
        "TimeSearch"
    )
)

/***
 * Create composable MainView which takes a function that provide a topBar
 * and by default is assigned to [EmptyComposable]
 */
@Composable
fun MainView(topBar: topBarFun = { EmptyComposable()}){
    /**
    Here we hold the state for showing or dismissing the dialog
    if state object ->[true] show dialog
    else if [false] -> hide dialog
     */
    val showAddDialog = remember {
      mutableStateOf(false)
    }
    /**
     Here we hold the state containing the current TimeZone Strings
     */
    val currentTimezoneStrings = remember {
        SnapshotStateList<String>()
    }
    /**
     Here we hold the state containing the currently selected index in Bottom Bar
     */
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    MyApplicationTheme {
        // Add Scaffold
        Scaffold(
            topBar = {
                // Compose Function for Toolbar
                topBar(selectedIndex.intValue)
            },
            floatingActionButton = {
                if (selectedIndex.intValue == 0){
                    FloatingActionButton(
                        modifier = Modifier.padding(16.dp),
                        shape = FloatingActionButtonDefaults.largeShape,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        onClick = {
                            showAddDialog.value = true
                        }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Timezone Location")
                    }
                }
            },
            bottomBar = {
                // Bottom Navigation Bar
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    bottomNavigationItems.forEachIndexed { index, bottomItem ->
                        NavigationBarItem(
                            selected = selectedIndex.intValue == index,
                            onClick = { selectedIndex.intValue == index },
                            icon = {
                                Icon(imageVector = bottomItem.icon, contentDescription = bottomItem.iconContentDescription)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.DarkGray,
                                selectedTextColor = Color.DarkGray,
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White,
                                indicatorColor = MaterialTheme.colorScheme.primary
                            ),
                            label = {
                                Text(
                                    text = bottomItem.route,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)){
                // Dialogs
                // show dialog if the showAddDialog is true
                if (showAddDialog.value) {
                    AddTimeZoneDialog(
                        // onAdd will receive new list of time zones
                        onAdd = { newTimezones ->
                            showAddDialog.value = false
                            for (zone in newTimezones) {
                                // if current list doesn't contain the time zone, add to your list
                                if (!currentTimezoneStrings.contains(zone)) {
                                    currentTimezoneStrings.add(zone)
                                }
                            }
                        },
                        onDismiss = {
                            // onDimiss don't show the dialog
                            showAddDialog.value = false
                        },
                    )
                }

                // Screens
                when (selectedIndex.intValue	) {
                    0 -> TimeZoneScreen(currentTimezoneStrings)
                    // 1 -> SearchScreen(currentTimezoneStrings)
                }
            }
        }
    }
}