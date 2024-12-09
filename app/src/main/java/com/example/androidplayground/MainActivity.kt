package com.example.androidplayground

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.compose.AsyncImage
import com.example.androidplayground.components.AirplaneModeReceiver
import com.example.androidplayground.components.PhotoCompressionWorker
import com.example.androidplayground.data.SampleMessagingData
import com.example.androidplayground.screens.Conversation
import com.example.androidplayground.ui.theme.AndroidPlayGroundTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val receiver = AirplaneModeReceiver()

    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        workManager = WorkManager.getInstance(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        registerReceiver(
            receiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )

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
                        val workRes = viewModel.workId?.let {
                            workManager.getWorkInfoByIdLiveData(it).observeAsState().value
                        }
//                        Button(onClick = {
//                            Log.d("Main", "clicked")
//                            Intent(applicationContext, RunningService::class.java).also {
//                                it.action = RunningService.Actions.START.toString()
//                                startService(it)
//                            }
//                        }) {
//                            Text(text = "Click me to Start")
//                        }
//                        Button(onClick = {
//                            Log.d("Main", "clicked")
//                            Intent(applicationContext, RunningService::class.java).also {
//                                it.action = RunningService.Actions.STOP.toString()
//                                startService(it)
//                            }
//                        }) {
//                            Text(text = "Click me to Stop")
//                        }
                        LaunchedEffect(key1 = workRes?.outputData) {
                            if (workRes?.outputData != null) {
                                val filePath =
                                    workRes.outputData.getString(PhotoCompressionWorker.KEY_RESULT_PATH)
                                filePath?.let {
                                    val bitmap = BitmapFactory.decodeFile(it)
                                    viewModel.updateCompressedBitmap(bitmap)
                                }
                            }
                        }
                        viewModel.compressedBitmap?.let {
                            Text(text = "Compressed bitmap")
                            Image(bitmap = it.asImageBitmap(), contentDescription = null)
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }

        val request = OneTimeWorkRequestBuilder<PhotoCompressionWorker>()
            .setInputData(
                workDataOf(
                    PhotoCompressionWorker.KEY_CONTENT_URI to uri.toString(),
                    PhotoCompressionWorker.KEY_COMPRESSION_THRESHOLD to 1024 * 20L
                )
            )
            .setConstraints(
                Constraints(
                    requiresStorageNotLow = true
                )
            ).build()
        viewModel.updateWorkId(request.id)
        workManager.enqueue(request)

        viewModel.updateUri(uri)
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
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