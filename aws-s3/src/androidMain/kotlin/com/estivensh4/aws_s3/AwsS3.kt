package com.estivensh4.aws_s3

import com.amazonaws.AmazonClientException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.S3ClientOptions
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.estivensh4.aws_kmp.AwsException
import com.estivensh4.aws_s3.util.toAWSMethod
import kotlinx.datetime.Instant
import java.io.FileNotFoundException
import java.util.Calendar
import java.util.Date


actual class AwsS3 actual constructor(
    private val accessKey: String,
    private val secretKey: String,
    private val endpoint: String
) {

    private val client: AmazonS3Client
        get() {
            return AmazonS3Client(
                BasicAWSCredentials(
                    accessKey,
                    secretKey
                ),
                Region.getRegion(Regions.US_EAST_1)
            ).apply {
                this.endpoint = this@AwsS3.endpoint
                setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build())
            }
        }

    actual val endpointAWS get() = client.endpoint

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
     * @throws AwsException If there were any problems pre-signing the
     * request for the specified S3 object.
     * @see AwsS3.generatePresignedUrl
     */
    actual fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expiration: Instant
    ): String? {
        return try {
            val date = Calendar.getInstance()
            date.add(Calendar.MINUTE, 15)
            val result =
                client.generatePresignedUrl(bucketName, key, Date(expiration.toEpochMilliseconds()))
            result.toString()
        } catch (exception: AmazonS3Exception) {
            when (exception.statusCode) {
                404 -> throw FileNotFoundException("this key $key does not exist")
                else -> {
                    throw AwsException("Exception is ${exception.message}", exception)
                }
            }
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
    actual fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expiration: Instant,
        method: HttpMethod
    ): String? {
        return try {
            val date = Calendar.getInstance()
            date.add(Calendar.MINUTE, 15)
            val result =
                client.generatePresignedUrl(
                    bucketName,
                    key,
                    Date(expiration.toEpochMilliseconds()),
                    method.toAWSMethod()
                )
            result.toString()
        } catch (exception: AmazonS3Exception) {
            when (exception.statusCode) {
                404 -> throw FileNotFoundException("this key $key does not exist")
                else -> {
                    throw AwsException("Exception is ${exception.message}", exception)
                }
            }
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
     * @throws AwsException If there were any problems pre-signing the
     * request for the Amazon S3 resource.
     * @see AwsS3.generatePresignedUrl
     * @see AwsS3.generatePresignedUrl
     */
    actual fun generatePresignedUrl(
        generatePresignedUrlRequest: GeneratePresignedUrlRequest
    ): String? {
        return try {
            val result = client.generatePresignedUrl(
                com.amazonaws.services.s3.model.GeneratePresignedUrlRequest(
                    generatePresignedUrlRequest.bucketName,
                    generatePresignedUrlRequest.key
                )
            )
            result.toString()
        } catch (exception: AmazonS3Exception) {
            when (exception.statusCode) {
                404 -> throw FileNotFoundException("this key ${generatePresignedUrlRequest.key} does not exist")
                else -> {
                    throw AwsException("Exception is ${exception.message}", exception)
                }
            }
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
