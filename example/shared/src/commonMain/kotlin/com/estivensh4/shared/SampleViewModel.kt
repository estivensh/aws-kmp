package com.estivensh4.shared

import com.estivensh4.aws_s3.AwsS3
import com.estivensh4.aws_s3.Bucket
import com.estivensh4.aws_s3.ImageFile
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class SampleViewModel : KMMViewModel() {

    private val scope = viewModelScope.coroutineScope
    private var _bucket = Bucket("", Clock.System.now())
    private val client: AwsS3
        get() {
            return AwsS3.Builder()
                .accessKey(BuildPublicConfig.accessKey)
                .secretKey(BuildPublicConfig.secretKey)
                .setEndpoint("s3.amazonaws.com")
                .build()
        }

    @NativeCoroutinesState
    val bucket get() = _bucket

    fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expiration: Instant
    ): String? {
        return client.generatePresignedUrl(
            bucketName = bucketName,
            key = key,
            expiration = expiration
        )
    }

    fun createBucket(bucketName: String) {
        scope.launch {
            _bucket = client.createBucket(bucketName)
        }
    }

    fun listBuckets() {
        scope.launch {
            val list = client.listBuckets()
            print("Hola $list")
        }
    }

    fun deleteBucket(bucketName: String) {
        scope.launch {
            client.deleteBucket(bucketName)
        }
    }

    fun putObject(bucketName: String, key: String, imageFile: ImageFile) {
        scope.launch {
            client.putObject(bucketName, key, imageFile)
        }
    }
}