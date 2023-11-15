package com.estivensh4.androidapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.estivensh4.androidapp.ui.theme.AwskmpTheme
import com.estivensh4.shared.SampleViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AwskmpTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val sampleViewModel = SampleViewModel()

    Column {
        CButton(text = "GeneratePresignedURL") {
            text = sampleViewModel.generatePresignedUrl(
                bucketName = "bucket",
                key = "key",
                expiration = Clock.System.now().plus(15, DateTimeUnit.HOUR)
            ) ?: ""

            Log.d("AwsS3", text)
        }
        CButton(text = "Create bucket") {
            sampleViewModel.createBucket("test-bucket-android-app")
        }
    }
}

@Composable
fun CButton(
    text: String,
    onClick: ()  -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(
            text = text,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AwskmpTheme {
        Greeting("Android")
    }
}