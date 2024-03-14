package com.estivensh4.wearapp.presentation

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import com.estivensh4.s3.UploadFile
import com.estivensh4.shared.SampleViewModel
import com.estivensh4.wearapp.presentation.theme.ExampleTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gfgPolicy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(gfgPolicy)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    ExampleTheme {
        val sampleViewModel = SampleViewModel()
        val bucketName = "test-bucket-wear-app"
        val state = rememberScalingLazyListState()
        val context = LocalContext.current
        val key = "test (1).jpg"
        val photoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) {
            if (it != null) {
                sampleViewModel.putObject(
                    bucketName = bucketName,
                    key = key,
                    uploadFile = UploadFile(
                        uri = it,
                        contentResolver = context.contentResolver
                    )
                )
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            timeText = {
                TimeText()
            },
            positionIndicator = {
                PositionIndicator(scalingLazyListState = state)
            }
        ) {
            ScalingLazyColumn(
                state = state,
            ) {
                item {
                    Button(
                        onClick = {
                            sampleViewModel.generatePresignedUrl(bucketName, key)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Generate presigned url")
                    }
                }
                item {
                    Button(
                        onClick = { sampleViewModel.createBucket(bucketName) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Create bucket")
                    }
                }
                item {
                    Button(
                        onClick = { sampleViewModel.listBuckets() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "List buckets")
                    }
                }
                item {
                    Button(
                        onClick = { sampleViewModel.deleteBucket(bucketName) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Delete bucket")
                    }
                }

                item {
                    Button(
                        onClick = {
                            photoPicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Put object")
                    }
                }
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}