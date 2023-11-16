import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.estivensh4.aws_s3.ImageFile
import com.estivensh4.shared.SampleViewModel
import kotlinx.coroutines.launch
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
@Preview
fun App() {
    var generatePresignerUrlResult by remember { mutableStateOf("") }
    val sampleViewModel = SampleViewModel()
    val bucketName = "test-bucket-desktop-app"
    val key = "test.jpg"
    val scope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            var isFileChooserOpen by remember { mutableStateOf(false) }

            if (isFileChooserOpen) {
                FileDialog(
                    onCloseRequest = { list ->
                        isFileChooserOpen = false
                        list.forEach {
                            sampleViewModel.putObject(
                                bucketName = bucketName,
                                key = it.name,
                                imageFile = ImageFile(
                                    uri = it.toURI()
                                )
                            )
                        }
                    }
                )
            }
            CButton(text = "generatePresignedUrl") {
                generatePresignerUrlResult = sampleViewModel.generatePresignedUrl(
                    bucketName = bucketName,
                    key = key
                ) ?: ""
            }
            CButton(text = "Create bucket") {
                scope.launch {
                    sampleViewModel.createBucket(bucketName)
                }
            }
            CButton(text = "List buckets") {
                sampleViewModel.listBuckets()
            }
            CButton(text = "Delete bucket") {
                sampleViewModel.deleteBucket(bucketName)
            }
            CButton(text = "Put object") {
                isFileChooserOpen = true
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
    Window(onCloseRequest = ::exitApplication, title = "AWS DesktopApp") {
        App()
    }
}

@Composable
private fun FileDialog(
    parent: Frame? = null,
    onCloseRequest: (result: List<File>) -> Unit
) = AwtWindow(
    create = {
        object : FileDialog(parent, "Choose a file", LOAD) {
            override fun setMultipleMode(value: Boolean) {
                super.setMultipleMode(value)
                if (value) {
                    onCloseRequest(files.toList())
                }
            }

            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    onCloseRequest(files.toList())
                }
            }

            override fun isMultipleMode(): Boolean {
                return true
            }
        }
    },
    dispose = FileDialog::dispose
)