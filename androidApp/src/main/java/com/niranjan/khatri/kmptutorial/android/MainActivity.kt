package com.niranjan.khatri.kmptutorial.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.niranjan.khatri.kmptutorial.android.ui.MainView
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog()) // Initialize Napier Logging library
        setContent {
            MyApplicationTheme {
                MainView {
                    TopAppBar(
                        title = {
                            when (it) {
                                0 -> Text(text = stringResource(id = R.string.time_zone))
                                else -> Text(text = stringResource(id = R.string.time_search))
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@PreviewScreenSizes
@Preview
@PreviewFontScale
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainView {}
    }
}
