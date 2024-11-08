package com.example.androidplayground

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import coil.compose.AsyncImage
import com.example.androidplayground.data.SampleMessagingData
import com.example.androidplayground.screens.Conversation
import com.example.androidplayground.ui.theme.AndroidPlayGroundTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidPlayGroundTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        viewModel.uri?.let {
                            AsyncImage(model = it, contentDescription = null)
                        }
                        Button(onClick = {
                            Log.d("Main", "clicked")
                        }) {
                            Text(text = "Click me")
                        }
                    }
                }
            }
        }

//        lifecycleScope.launch {
//            delay(2000)
//            Log.d("Main", "Launching the Youtube")
//
////            // Explicit intent to outside app
////            Intent(Intent.ACTION_MAIN).also {
////                it.`package` = "com.google.android.youtube"
////                startActivity(it)
////            }
//
        // Implicit intent, system will decide which app to open
////            val intent = Intent(Intent.ACTION_SEND).apply {
////                type = "text/plain"
////                putExtra(Intent.EXTRA_EMAIL, arrayOf("amanjithayas@gmail.com"))
////                putExtra(Intent.EXTRA_SUBJECT, "Hero Try")
////                putExtra(Intent.EXTRA_TEXT, "Trying a small step..")
////            }
//
//            startActivity(intent)
//        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }

        viewModel.updateUri(uri)
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