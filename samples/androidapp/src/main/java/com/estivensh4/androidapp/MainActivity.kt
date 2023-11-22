package com.estivensh4.androidapp

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.estivensh4.androidapp.ui.theme.AwskmpTheme
import com.estivensh4.s3.ImageFile
import com.estivensh4.shared.SampleViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setContent {
            AwskmpTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    var text by remember { mutableStateOf("") }
    val sampleViewModel = SampleViewModel()
    val bucketName = "test-bucket-android-app"
    val key = "test (1).jpg"
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            sampleViewModel.putObject(
                bucketName,
                key,
                ImageFile(
                    uri = it,
                    contentResolver = context.contentResolver
                )
            )
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    Column {
        CButton(text = "GeneratePresignedURL") {
            sampleViewModel.generatePresignedUrl(
                bucketName = bucketName,
                key = key
            )
            Log.d("ResultGeneratePresignedUrl", text)
        }
        CButton(text = "Create bucket") {
            sampleViewModel.createBucket(bucketName)
        }
        CButton(text = "Delete bucket") {
            sampleViewModel.deleteBucket(bucketName)
        }
        CButton(text = "Put object") {
            photoPicker.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
    }
}

@Composable
fun CButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(
            text = text,
        )
    }
}