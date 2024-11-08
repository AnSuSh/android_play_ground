package com.example.androidplayground

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.lifecycleScope
import com.example.androidplayground.data.SampleMessagingData
import com.example.androidplayground.screens.Conversation
import com.example.androidplayground.services.SampleService
import com.example.androidplayground.ui.theme.AndroidPlayGroundTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidPlayGroundTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(messages = SampleMessagingData.conversationSample)
                }
            }
        }

        lifecycleScope.launch {
            delay(2000)
            Log.d("Main", "Launching the service")

            intent = Intent(this@MainActivity, SampleService::class.java)
            intent.putExtra("name", "Hero!")
            startService(intent)
        }
    }

    override fun onStop() {
        super.onStop()

        stopService(intent)
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@PreviewScreenSizes
@Composable
fun GreetingPreview() {
    AndroidPlayGroundTheme {
        Surface {
            Conversation(
                messages = SampleMessagingData.conversationSample
            )
        }
    }
}