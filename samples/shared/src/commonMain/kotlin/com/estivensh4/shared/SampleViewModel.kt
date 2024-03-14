package com.estivensh4.shared

import com.estivensh4.s3.AWSS3
import com.estivensh4.s3.Bucket
import com.estivensh4.s3.UploadFile
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@OptIn(DelicateCoroutinesApi::class)
open class SampleViewModel : KMMViewModel() {

    private var _bucketList = MutableStateFlow<List<Bucket>>(viewModelScope, emptyList())
    private var _bucket = MutableStateFlow(viewModelScope, Bucket("", Clock.System.now()))
    private var _generatePresignedUrl = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    val bucketList get() = _bucketList.asStateFlow()

    @NativeCoroutinesState
    val bucket get() = _bucket.asStateFlow()

    @NativeCoroutinesState
    val generatePresignedUrl get() = _generatePresignedUrl.asStateFlow()

    private val client = AWSS3.builder()
        .accessKey(BuildPublicConfig.accessKey)
        .secretKey(BuildPublicConfig.secretKey)
        .setEndpoint("s3.amazonaws.com")
        .build()

    fun generatePresignedUrl(
        bucketName: String,
        key: String,
    ) {
        GlobalScope.launch {
            _generatePresignedUrl.value = client.generatePresignedUrl(
                bucketName = bucketName,
                key = key,
                expirationInSeconds = 3600L
            ) ?: ""
        }
    }

    fun createBucket(bucketName: String) {
        GlobalScope.launch {
            client.createBucket(bucketName)
        }
    }

    fun listBuckets() {
        GlobalScope.launch {
            val list = client.listBuckets()
            _bucketList.value = list
        }
    }

    fun deleteBucket(bucketName: String) {
        GlobalScope.launch {
            client.deleteBucket(bucketName)
        }
    }

    fun putObject(bucketName: String, key: String, uploadFile: UploadFile) {
        GlobalScope.launch {
            client.putObject(bucketName, key, uploadFile)
        }
    }
}