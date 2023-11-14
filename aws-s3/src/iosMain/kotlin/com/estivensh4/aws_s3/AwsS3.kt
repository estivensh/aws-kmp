package com.estivensh4.aws_s3

import cocoapods.AWSCore.AWSEndpoint
import cocoapods.AWSCore.AWSRegionType
import cocoapods.AWSCore.AWSServiceConfiguration
import cocoapods.AWSCore.AWSStaticCredentialsProvider
import cocoapods.AWSS3.AWSS3GetPreSignedURLRequest
import cocoapods.AWSS3.AWSS3PreSignedURLBuilder
import com.estivensh4.aws_kmp.AwsException
import com.estivensh4.aws_s3.util.toAWSMethod
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
actual class AwsS3 actual constructor(
    private val accessKey: String,
    private val secretKey: String,
    private val endpoint: String
) {

    actual val endpointAWS get() = ""
    private var awsServiceConfiguration: AWSServiceConfiguration? = null

    init {
        initS3()
    }

    private fun initS3() {
        val credentialsProvider = AWSStaticCredentialsProvider(
            accessKey,
            secretKey
        )
        val configuration = AWSServiceConfiguration(
            region = AWSRegionType.AWSRegionAPEast1,
            credentialsProvider = credentialsProvider,
            endpoint = AWSEndpoint(endpoint)
        )
        awsServiceConfiguration = configuration
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
    @OptIn(ExperimentalForeignApi::class)
    actual fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expiration: Instant
    ): String? {

        return try {
            val preSignedURLRequest = AWSS3GetPreSignedURLRequest()
            preSignedURLRequest.apply {
                bucket = bucketName
                this.key = key
                expires = NSDate(expiration.toEpochMilliseconds().toDouble())
            }

            val request = AWSS3PreSignedURLBuilder.defaultS3PreSignedURLBuilder()
                .getPreSignedURL(preSignedURLRequest)
            val url = request.result() as NSURL
            url.absoluteString
        } catch (exception: Exception) {
            throw AwsException("Exception is ${exception.message}", exception)
        }
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
     * @param method The HTTP method verb to use for this URL
     * @return A pre-signed URL which expires at the specified time, and can be
     * used to allow anyone to download the specified object from S3,
     * without exposing the owner's AWS secret access key.
     * @throws AwsException If there were any problems pre-signing the
     * request for the specified S3 object.
     * @see AwsS3.generatePresignedUrl
     * @see AwsS3.generatePresignedUrl
     */
    @OptIn(ExperimentalForeignApi::class)
    actual fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expiration: Instant,
        method: HttpMethod
    ): String? {
        return try {
            val preSignedURLRequest = AWSS3GetPreSignedURLRequest()
            preSignedURLRequest.apply {
                bucket = bucketName
                this.key = key
                setHTTPMethod(method.toAWSMethod())
                expires = NSDate(expiration.toEpochMilliseconds().toDouble())
            }

            val request = AWSS3PreSignedURLBuilder.defaultS3PreSignedURLBuilder()
                .getPreSignedURL(preSignedURLRequest)
            val url = request.result() as NSURL
            url.absoluteString
        } catch (exception: Exception) {
            throw AwsException("Exception is ${exception.message}", exception)
        }
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
     * application owner's Amazon S3 bucket, but doesn't need to ship the AWS
     * security credentials with the application. A pre-signed URL to PUT an
     * object into the owner's bucket can be generated from a remote location
     * with the owner's AWS security credentials, then the pre-signed URL can be
     * passed to the end user's application to use.
     *
     *
     *
     * Note that presigned URLs cannot be used to upload an object with an
     * attached policy, as described in [this blog post](https://aws.amazon.com/articles/1434?_encoding=UTF8&queryArg=searchQuery&x=0&fromSearch=1&y=0&searchPath=all). That method is only suitable for POSTs from HTML
     * forms by browsers.
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
     * @param generatePresignedUrlRequest The request object containing all the
     * options for generating a pre-signed URL (bucket name, key,
     * expiration date, etc).
     * @return A pre-signed URL that can be used to access an Amazon S3 resource
     * without requiring the user of the URL to know the account's AWS
     * security credentials.
     * @throws AmazonS3Exception If there were any problems pre-signing the
     * request for the Amazon S3 resource.
     * @see AwsS3.generatePresignedUrl
     * @see AwsS3.generatePresignedUrl
     */
    @OptIn(ExperimentalForeignApi::class)
    actual fun generatePresignedUrl(
        generatePresignedUrlRequest: GeneratePresignedUrlRequest
    ): String? {
        return try {
            val preSignedURLRequest = AWSS3GetPreSignedURLRequest()
            preSignedURLRequest.apply {
                bucket = generatePresignedUrlRequest.bucketName
                key = generatePresignedUrlRequest.key
            }
            val request = AWSS3PreSignedURLBuilder.defaultS3PreSignedURLBuilder()
                .getPreSignedURL(preSignedURLRequest)
            val url = request.result() as NSURL
            url.absoluteString
        } catch (exception: Exception) {
            throw AwsException("Exception is ${exception.message}", exception)
        }
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
