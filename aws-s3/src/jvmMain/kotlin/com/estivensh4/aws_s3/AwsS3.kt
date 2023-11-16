package com.estivensh4.aws_s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.DeleteObjectsRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.estivensh4.aws_s3.util.toAWSMethod
import kotlinx.datetime.Instant
import java.io.FileNotFoundException
import java.util.Calendar

actual class AwsS3 actual constructor(
    private val accessKey: String,
    private val secretKey: String,
    private val endpoint: String
) {
    private val client: AmazonS3
        get() {
            val credentials = AWSStaticCredentialsProvider(
                BasicAWSCredentials(accessKey, secretKey)
            )
            return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(credentials)
                .enablePathStyleAccess()
                .disableChunkedEncoding()
                .build()
        }

    actual val endpointAWS: String
        get() = endpoint

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
     * @throws Exception If there were any problems pre-signing the
     * request for the specified S3 object.
     * @see AwsS3.generatePresignedUrl
     */
    actual fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expirationInSeconds: Long
    ): String? {
        return try {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, expirationInSeconds.toInt())
            val result = client.generatePresignedUrl(bucketName, key, calendar.time)
            result.toString()
        } catch (exception: AmazonS3Exception) {
            when (exception.statusCode) {
                404 -> throw FileNotFoundException("this key $key does not exist")
                else -> {
                    throw Exception("Exception is ${exception.message}", exception)
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
     * @throws Exception If there were any problems pre-signing the
     * request for the specified S3 object.
     * @see AwsS3.generatePresignedUrl
     * @see AwsS3.generatePresignedUrl
     */
    actual fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expirationInSeconds: Long,
        method: HttpMethod
    ): String? {
        return try {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, expirationInSeconds.toInt())
            val result =
                client.generatePresignedUrl(
                    bucketName,
                    key,
                    calendar.time,
                    method.toAWSMethod()
                )
            result.toString()
        } catch (exception: AmazonS3Exception) {
            when (exception.statusCode) {
                404 -> throw FileNotFoundException("this key $key does not exist")
                else -> {
                    throw Exception("Exception is ${exception.message}", exception)
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
     * @throws AmazonS3Exception If there were any problems pre-signing the
     * request for the Amazon S3 resource.
     * @see AwsS3.generatePresignedUrl
     * @see AwsS3.generatePresignedUrl
     */
    actual fun generatePresignedUrl(generatePresignedUrlRequest: GeneratePresignedUrlRequest): String? {
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
                    throw Exception("Exception is ${exception.message}", exception)
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

    /**
     *
     *
     * Creates a new Amazon S3 bucket with the specified name in the default
     * (US) region, [Region.US_Standard].
     *
     *
     *
     * Every object stored in Amazon S3 is contained within a bucket. Buckets
     * partition the namespace of objects stored in Amazon S3 at the top level.
     * Within a bucket, any name can be used for objects. However, bucket names
     * must be unique across all of Amazon S3.
     *
     *
     *
     * Bucket ownership is similar to the ownership of Internet domain names.
     * Within Amazon S3, only a single user owns each bucket. Once a uniquely
     * named bucket is created in Amazon S3, organize and name the objects
     * within the bucket in any way. Ownership of the bucket is retained as long
     * as the owner has an Amazon S3 account.
     *
     *
     *
     * To conform with DNS requirements, the following constraints apply:
     *
     *  * Bucket names should not contain underscores
     *  * Bucket names should be between 3 and 63 characters long
     *  * Bucket names should not end with a dash
     *  * Bucket names cannot contain adjacent periods
     *  * Bucket names cannot contain dashes next to periods (e.g.,
     * "my-.bucket.com" and "my.-bucket" are invalid)
     *  * Bucket names cannot contain uppercase characters
     *
     *
     *
     *
     * There are no limits to the number of objects that can be stored in a
     * bucket. Performance does not vary based on the number of buckets used.
     * Store all objects within a single bucket or organize them across several
     * buckets.
     *
     *
     *
     * Buckets cannot be nested; buckets cannot be created within other buckets.
     *
     *
     *
     * Do not make bucket create or delete calls in the high availability code
     * path of an application. Create or delete buckets in a separate
     * initialization or setup routine that runs less often.
     *
     *
     *
     * To create a bucket, authenticate with an account that has a valid AWS
     * Access Key ID and is registered with Amazon S3. Anonymous requests are
     * never allowed to create buckets.
     *
     *
     * @param bucketName The name of the bucket to create. All buckets in Amazon
     * S3 share a single namespace; ensure the bucket is given a
     * unique name.
     * @return The newly created bucket.
     * @throws AwsException If any errors are encountered in the client
     * while making the request or handling the response.
     */
    actual suspend fun createBucket(bucketName: String): Bucket {
        val result = client.createBucket(bucketName)
        return result.toBucket()
    }

    actual suspend fun listBuckets(): List<Bucket> {
        return client.listBuckets().map { it.toBucket() }
    }

    actual suspend fun deleteBucket(bucketName: String) {
        client.deleteBucket(bucketName)
    }

    actual suspend fun deleteObjects(
        bucketName: String,
        vararg keys: String
    ): DeleteObjectResult {
        val deleteObjectsRequest = DeleteObjectsRequest(bucketName)
        deleteObjectsRequest.withKeys(*keys)

        val result = client.deleteObjects(deleteObjectsRequest)

        return DeleteObjectResult(
            isRequesterCharged = result.isRequesterCharged,
            deleted = result.deletedObjects.map {
                DeleteObject(
                    key = it.key,
                    versionId = it.versionId,
                    deleteMarker = it.isDeleteMarker,
                    deleteMarkerVersionId = it.deleteMarkerVersionId
                )
            }
        )
    }

    actual suspend fun putObject(
        bucketName: String,
        key: String,
        imageFile: ImageFile
    ): PutObjectResult {
        val result = client.putObject(
            bucketName,
            key,
            imageFile.toByteArray().inputStream(),
            ObjectMetadata()
        )
        return PutObjectResult(
            versionId = result.versionId,
            eTag = result.eTag,
            expirationTime = result.expirationTime?.let { Instant.fromEpochMilliseconds(it.time) },
            contentMd5 = result.contentMd5,
        )
    }
}

fun com.amazonaws.services.s3.model.Bucket.toBucket(): Bucket {
    return Bucket(
        name = name,
        creationDate = creationDate?.let { Instant.fromEpochMilliseconds(it.time) }
    )
}