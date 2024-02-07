package com.niranjan.khatri.kmptutorial.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.niranjan.khatri.kmptutorial.android.R
import com.niranjan.khatri.kmptutorial.datetime.DateTimeHelper
import com.niranjan.khatri.kmptutorial.datetime.DateTimeHelperImpl
import kotlinx.coroutines.launch

@Composable
fun AddTimeZoneDialog(
    timezoneHelper: DateTimeHelper = DateTimeHelperImpl(),
    onAdd: (List<String>) -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    onDismissRequest = onDismiss
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        val timeZoneStrings by remember {
            mutableStateOf(
                timezoneHelper.getTimeZoneStrings().toList()
            )
        }
        val selectedStates = remember { SnapshotStateMap<Int, Boolean>() }
        val listState = rememberLazyListState()
        val searchValue = remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val focusRequester = remember { FocusRequester() }
        OutlinedTextField(
            singleLine = true,
            value = searchValue.value,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            onValueChange = {
                searchValue.value = it
                if (searchValue.value.isEmpty()) {
                    return@OutlinedTextField
                }
                val index = searchZones(searchValue.value, timezoneStrings = timeZoneStrings)
                if (index != -1) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index)
                    }
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    searchValue.value = ""
                }) {
                    Icon(
                        Icons.Filled.Clear,
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = "Cancel",
                    )
                }
            }
        )
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentPadding = PaddingValues(16.dp),
            state = listState,

            ) {
            itemsIndexed(timeZoneStrings) { i, timezone ->
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    color = if (isSelected(selectedStates,i))
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background

                ) {
                    Row(
                        modifier = Modifier
                            .toggleable(
                                value = isSelected(selectedStates, i),
                                onValueChange = {
                                    selectedStates[i] = it
                                })
                            .fillMaxWidth(),
                    ) {
                        Text(timezone)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.align(Alignment.End),
        ) {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {
                    onAdd(
                        getTimezones(
                            selectedStates = selectedStates,
                            timezoneStrings = timeZoneStrings
                        )
                    )
                }
            ) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }
}

fun searchZones(searchString: String, timezoneStrings : List<String>): Int {
    var timezone = timezoneStrings.firstOrNull { it.startsWith(searchString, ignoreCase = true)}
    if (timezone == null){
        timezone = timezoneStrings.firstOrNull { it.contains(searchString, ignoreCase = true) }
    }
    if (timezone != null){
        return timezoneStrings.indexOf(timezone)
    }
    return -1
}

fun isSelected(selectedStates: Map<Int, Boolean>, index: Int): Boolean{
    return (selectedStates.containsKey(index) && (selectedStates[index] == true))
}

fun getTimezones(selectedStates: Map<Int, Boolean>, timezoneStrings : List<String>): List<String> {
    val timezoneIndexes = selectedStates.map { if (it.value) it.key else -1 }
    val timezones = mutableListOf<String>()
    timezoneIndexes.forEach { index ->
        if (index != -1){
            timezones.add(timezoneStrings[index])
        }
    }
    return timezones
}