package com.niranjan.khatri.kmptutorial.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * This compose function takes timezone and date
 */
@Composable
fun TimeCard(timezone: String, date: String) {
    // Use Box to take full width and give White background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(120.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        // Card with rounded corners and grey border
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            // Add Content
            // box to set the background to white
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White
                    )
                    .padding(16.dp)
            ) {
                // row to fill the width
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // columns on the left side
                    Column(
                        horizontalAlignment = Alignment.Start

                    ) {
                        // show the time zone location
                        Text(
                            timezone, style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            "Location", style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    // create column on the right side
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        // show date
                        Text(
                            "Date", style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        // 10
                        Text(
                            date, style = TextStyle(
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
