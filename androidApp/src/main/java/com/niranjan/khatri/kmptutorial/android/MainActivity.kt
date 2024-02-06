package com.niranjan.khatri.kmptutorial.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.niranjan.khatri.kmptutorial.datetime.DateTimeHelperImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DateTimeView(helper = DateTimeHelperImpl())
                }
            }
        }
    }
}

@Composable
fun DateTimeView(helper: DateTimeHelperImpl) {
    val timeZone = helper.getCurrentTimeZoneId()
    Column {
        Text(text = "Time Zone: $timeZone")
        Text(text = "Current Date: ${helper.getDate(timeZone)}")
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        DateTimeView(DateTimeHelperImpl())
    }
}
