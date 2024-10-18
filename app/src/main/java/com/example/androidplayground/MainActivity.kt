package com.example.androidplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.androidplayground.ui.theme.AndroidPlayGroundTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidPlayGroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingCheenu()
                }
            }
        }
    }
}

@Composable
fun GreetingCheenu() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Magenta)
            .size(700.dp, 700.dp),
//            .fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Hello Gunda Cheenu!",
            color = Color.Blue,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .background(color = Color.Cyan)
                .padding(8.dp)
                .background(color = Color.Gray)
                .padding(8.dp)
                .clip(CutCornerShape(4.dp))
        )
        Text(
            text = "Hello Cheenu",
            color = Color.Blue,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .background(color = Color.Cyan)
                .padding(8.dp)
                .background(color = Color.Gray)
                .padding(8.dp)
                .clip(CutCornerShape(4.dp))
        )
        Box(modifier = Modifier
            .height(96.dp),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = "Hello Cheenu",
                color = Color.Blue,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .background(color = Color.Cyan)
                    .padding(8.dp)
                    .background(color = Color.Gray)
                    .padding(8.dp)
                    .clip(CutCornerShape(4.dp))
            )
            Text(
                text = "Hello Cheenu",
                color = Color.Blue,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .background(color = Color.Cyan)
                    .padding(8.dp)
                    .background(color = Color.Gray)
                    .padding(8.dp)
                    .clip(CutCornerShape(4.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@PreviewScreenSizes
@Composable
fun GreetingPreview() {
    AndroidPlayGroundTheme {
        Surface {
            GreetingCheenu()
        }
    }
}