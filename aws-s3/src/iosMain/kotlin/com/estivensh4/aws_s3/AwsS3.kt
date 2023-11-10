package com.estivensh4.aws_s3

import cocoapods.AWSS3.AWSS3GetPreSignedURLRequest
import cocoapods.AWSS3.AWSS3PreSignedURLBuilder
import kotlinx.coroutines.CompletableDeferred
import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.NSError
import platform.Foundation.NSURL

actual class AwsS3 actual constructor(
    private val accessKey: String,
    private val secretKey: String,
    private val endpoint: String
) {

    fun initialize() {

    }

    /**
     *
     *
     * Returns a pre-signed URL for accessing an Amazon S3 resource.
     *
     *
     *
     * Pre-signed URLs allow clients to form a URL for an Amazon S3 resource,
     * and then sign it with the current AWS security credentials. The
     * pre-signed URL can be shared to other users, allowing access to the
     * resource without providing an account's AWS security credentials.
     *
     *
     *
     * Pre-signed URLs are useful in many situations where AWS security
     * credentials aren't available from the client that needs to make the
     * actual request to Amazon S3.
     *
     *
     *
     * For example, an application may need remote users to upload files to the
     * application owner's Amazon S3 bucket, but doesn't need to ship the
     * AWS security credentials with the application. A pre-signed URL
     * to PUT an object into the owner's bucket can be generated from a remote
     * location with the owner's AWS security credentials, then the pre-signed
     * URL can be passed to the end user's application to use.
     *
     *
     *
     * If you are generating presigned url for [AWS KMS](http://aws.amazon.com/kms/)-encrypted objects, you need to
     * specify the correct region of the bucket on your client and configure AWS
     * Signature Version 4 for added security. For more information on how to do
     * this, see
     * http://docs.aws.amazon.com/AmazonS3/latest/dev/UsingAWSSDK.html#
     * specify-signature-version
     *
     *
     * @param bucketName The name of the bucket containing the desired object.
     * @param key The key in the specified bucket under which the desired object
     * is stored.
     * @param expiration The time at which the returned pre-signed URL will
     * expire.
     * @return A pre-signed URL which expires at the specified time, and can be
     * used to allow anyone to download the specified object from S3,
     * without exposing the owner's AWS secret access key.
     * @throws AmazonClientException If there were any problems pre-signing the
     * request for the specified S3 object.
     * @see AwsS3.generatePresignedUrl
     */
    actual fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expiration: Instant
    ): String? {

        val preSignedURLRequest = AWSS3GetPreSignedURLRequest()
        preSignedURLRequest.apply {
            bucket = bucketName
            this.key = key
            expires = NSDate(expiration.toEpochMilliseconds().toDouble())
        }

        val request = AWSS3PreSignedURLBuilder.defaultS3PreSignedURLBuilder()
            .getPreSignedURL(preSignedURLRequest)
        val url = request.result() as NSURL
        return url.absoluteString
    }

    actual class Builder {
        private var accessKey: String = ""
        private var secretKey: String = ""
        private var endpoint: String = ""

        actual fun accessKey(accessKey: String): Builder {
            this.accessKey = accessKey
            return this
        }

        actual fun secretKey(secretKey: String): Builder {
            this.secretKey = secretKey
            return this
        }

        actual fun setEndpoint(endpoint: String): Builder {
            this.endpoint = endpoint
            return this
        }

        actual fun build(): AwsS3 {
            return AwsS3(accessKey, secretKey, endpoint)
        }
    }
}

actual fun AwsS3Builder(): AwsS3.Builder = AwsS3.Builder()

suspend inline fun <reified T> awaitResult(function: (callback: (T?, NSError?) -> Unit) -> Unit): T {
    val job = CompletableDeferred<T?>()
    function { result, error ->
        if (error == null) {
            job.complete(result)
        } else {
            job.completeExceptionally(Exception(""))
        }
    }
    return job.await() as T
}
