/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.estivensh4.wearapp.presentation

import android.R
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
import com.estivensh4.aws_s3.ImageFile
import com.estivensh4.shared.SampleViewModel
import com.estivensh4.wearapp.presentation.theme.ExampleTheme
import java.net.URL


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
        val createBucketResult = sampleViewModel.bucket
        val bucketName = "test-bucket-wear-app"
        val state = rememberScalingLazyListState()
        val context = LocalContext.current
        val uri = Uri.parse("android.resource://" + "com.estivensh4.wearapp" + "/" + com.estivensh4.wearapp.R.drawable.ic_android_black_24dp)

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
                    Text(text = createBucketResult.toString())
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
                            sampleViewModel.putObject(
                                bucketName,
                                "test.jpg",
                                ImageFile(
                                    uri = uri,
                                    contentResolver = context.contentResolver
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