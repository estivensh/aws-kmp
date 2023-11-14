import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.estivensh4.shared.ExampleViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello") }
    val exampleViewModel = ExampleViewModel()

    MaterialTheme {
        Button(onClick = {
            text = exampleViewModel.generatePresignedUrl(
                bucketName = "bucket",
                key = "key",
                expiration = Clock.System.now().plus(15, DateTimeUnit.HOUR)
            ) ?: ""
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}