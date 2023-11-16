package com.estivensh4.shared

import com.estivensh4.aws_s3.AwsS3
import com.estivensh4.aws_s3.Bucket
import com.estivensh4.aws_s3.ImageFile
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock

@OptIn(DelicateCoroutinesApi::class)
open class SampleViewModel : KMMViewModel() {

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
    ): String? {
        return client.generatePresignedUrl(
            bucketName = bucketName,
            key = key,
            expirationInSeconds = 3600L
        )
    }

    fun createBucket(bucketName: String) = runBlocking {
        client.createBucket(bucketName)
    }

    fun listBuckets() {
        GlobalScope.launch {
            val list = client.listBuckets()
            print("$list")
        }
    }

    fun deleteBucket(bucketName: String) = runBlocking {
        client.deleteBucket(bucketName)
    }

    fun putObject(bucketName: String, key: String, imageFile: ImageFile) {
        GlobalScope.launch {
            client.putObject(bucketName, key, imageFile)
        }
    }
}