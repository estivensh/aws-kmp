import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.estivensh4.shared.SampleViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

@Composable
@Preview
fun App() {
    var generatePresignerUrlResult by remember { mutableStateOf("") }
    val sampleViewModel = SampleViewModel()
    val createBucketResult = sampleViewModel.bucket

    MaterialTheme {
        Column {
            Text(generatePresignerUrlResult)
            Text(createBucketResult.toString())


            CButton(text = "generatePresignedUrl") {
                generatePresignerUrlResult = sampleViewModel.generatePresignedUrl(
                    bucketName = "bucket",
                    key = "key",
                    expiration = Clock.System.now().plus(15, DateTimeUnit.HOUR)
                ) ?: ""
            }
            CButton(text = "Create bucket") {
                sampleViewModel.createBucket("test-bucket")
            }
        }
    }
}

@Composable
fun CButton(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}