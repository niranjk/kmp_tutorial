package com.niranjan.khatri.kmptutorial.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.niranjan.khatri.kmptutorial.datetime.DateTimeHelper
import com.niranjan.khatri.kmptutorial.datetime.DateTimeHelperImpl
import kotlinx.coroutines.delay


const val timeMillis = 1000 * 60L // 1 second
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeZoneScreen(
    currentTimezoneStrings: SnapshotStateList<String>
) {
    // Create the instance of DateTimeHelper class
    val timezoneHelper: DateTimeHelper = DateTimeHelperImpl()
    // remember the state of the list that will be defined later
    val listState = rememberLazyListState()
    // crete a column that will take the entire width
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //  Add Content
        // remember the current time, where time is current time in string format
        var time by remember { mutableStateOf(timezoneHelper.getCurrentTime()) }
        // This LaunchedEffect will run once and continue to get update every minute.
        // We pass Unit so that it is not canceled and re-launched when LaunchedEffect is recomposed
        LaunchedEffect(Unit) {
            while (true) {
                time = timezoneHelper.getCurrentTime()
                delay(timeMillis) // Every minute
            }
        }
    // Use the TimeZoneCard passing the timezone, time and date
        TimeZoneCard(
            city = timezoneHelper.getCurrentTimeZoneId(),
            time = time, date = timezoneHelper.getDate(timezoneHelper.getCurrentTimeZoneId())
        )
        Spacer(modifier = Modifier.size(16.dp))

        // Add Timezone items
        // This LazyColumn is like Android's RecyclerView or iOS's UITableView for building vertical list items
        LazyColumn(
            state = listState,
        ) {
            // LazyColumn has this items method to go through the list of items
            items(currentTimezoneStrings.size,
                // Use this key filed to set the unique key for each time zone items. This is useful during deletion.
                key = { timezone ->
                    timezone
                }) { index ->
                val timezoneString = currentTimezoneStrings[index]
                // use the AnimatedSwipeDismiss to handle swipe to delete
                AnimatedSwipeDismiss(
                    item = timezoneString,
                    // set the background to red when you swipe to delete and set the delete icon
                    background = { _ ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .background(Color.Red)
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp
                                )
                        ) {
                            val alpha = 1f
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                tint = Color.White.copy(alpha = alpha)
                            )
                        }
                    },
                    content = {
                        // set the content of list item i.e. it will be a TimeCard
                        TimeCard(
                            timezone = timezoneString,
                            date = timezoneHelper.getDate(timezoneString)
                        )
                    },
                    // this function will help to remove the time zone string from your list when you swipe
                    onDismiss = { zone ->
                        if (currentTimezoneStrings.contains(zone)) {
                            currentTimezoneStrings.remove(zone)
                        }
                    }
                )
            }
        }

    }
}
