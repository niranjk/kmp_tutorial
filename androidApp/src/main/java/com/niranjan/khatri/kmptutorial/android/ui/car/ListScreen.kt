package com.niranjan.khatri.kmptutorial.android.ui.car

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niranjan.khatri.kmptutorial.android.R
import com.niranjan.khatri.kmptutorial.android.di.CarApp
import com.niranjan.khatri.kmptutorial.model.CarItemDetails
import com.niranjan.khatri.kmptutorial.room.Car
import com.niranjan.khatri.kmptutorial.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(context: Context) {
    /**
     * To enable KMP compatibility for the ViewModel, this code avoids directly referencing the Android Application 
     * during ViewModel instantiation. Instead, it passes a KMP-compatible dependency container 
     * (AppDiContainer) through the extras to the ViewModel factory. 
     * This allows the factory to create the ViewModel instance without Android dependencies.
     */
    val app = context.applicationContext as CarApp
    val extras = remember(app) {
        val container = app.container
        MainViewModel.newCreationExtras(container)
    }
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModel.Factory,
        extras = extras,
    )

    val uiState by viewModel.homeUiState.collectAsState()
    val storeState by viewModel.storeUiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.cars),)
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing.only(
            /**
             * Here the Box composable will be padded according to the safe drawing area 
             * defined by the code snippet. This ensures that the content within the Box
             * is displayed within the safe area and doesn't overlap with system bars or cutouts.
             */
            WindowInsetsSides.Top + WindowInsetsSides.Horizontal
        ),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            var expanded by remember { mutableStateOf(false) }
            Row(modifier = Modifier.padding(16.dp)) {
                val total = storeState.storeDetails.sumOf { item -> item.count }
                Text(
                    text = "Store has $total items",
                    modifier = Modifier.weight(1f).padding(12.dp),
                )
                Button(onClick = { expanded = !expanded }) {
                    Text(text = if (expanded) "collapse" else "expand")
                }
            }
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(1000)),
            ) {
                StoreDetailsView(storeState.storeDetails)
            }

            LazyColumn {
                items(items = uiState.carList, key = { it.id }) { item ->
                    CarItem(
                        item = item,
                        onAddToStore = viewModel::addItemToStore,
                    )
                }
                item {
                    Spacer(
                        Modifier.windowInsetsBottomHeight(
                            WindowInsets.systemBars
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CarItem(
    item: Car,
    onAddToStore: (car: Car) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .heightIn(min = 96.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = item.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                )
                Text(
                    text = item.brand,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = { onAddToStore(item) }) {
                    Text(stringResource(R.string.add))
                }
            }
        }
    }
}

@Composable
fun StoreDetailsView(store: List<CarItemDetails>, modifier: Modifier = Modifier) {
    Column(
        modifier.padding(horizontal = 32.dp),
    ) {
        store.forEach { item ->
            Text(text = "${item.car.name}: ${item.count}")
        }
    }
}

@Preview
@Composable
fun ItemPreview() {
    CarItem(
        Car(name = "500", brand = "Fiat", id = 1111),
        onAddToStore = {},
    )
}