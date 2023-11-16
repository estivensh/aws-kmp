/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.aws_s3

import com.estivensh4.aws_kmp.AwsException

expect class AWSS3 private constructor(
    accessKey: String,
    secretKey: String,
    endpoint: String
) {

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
     * @param expirationInSeconds The time at which the returned pre-signed URL will
     * expire.
     * @return A pre-signed URL which expires at the specified time, and can be
     * used to allow anyone to download the specified object from S3,
     * without exposing the owner's AWS secret access key.
     * @throws AwsException If there were any problems pre-signing the
     * request for the specified S3 object.
     * @see AWSS3.generatePresignedUrl
     */
    suspend fun generatePresignedUrl(bucketName: String, key: String, expirationInSeconds: Long): String?

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
     * @param expirationInSeconds The time at which the returned pre-signed URL will
     * expire.
     * @param method The HTTP method verb to use for this URL
     * @return A pre-signed URL which expires at the specified time, and can be
     * used to allow anyone to download the specified object from S3,
     * without exposing the owner's AWS secret access key.
     * @throws AwsException If there were any problems pre-signing the
     * request for the specified S3 object.
     * @see AWSS3.generatePresignedUrl
     * @see AWSS3.generatePresignedUrl
     */
    suspend fun generatePresignedUrl(
        bucketName: String,
        key: String,
        expirationInSeconds: Long,
        method: HttpMethod
    ): String?

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
     * @see AWSS3.generatePresignedUrl
     * @see AWSS3.generatePresignedUrl
     */
    suspend fun generatePresignedUrl(generatePresignedUrlRequest: GeneratePresignedUrlRequest): String?

    /**
     *
     *
     * Creates a new Amazon S3 bucket with the specified name in the default
     * (US) region.
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
    suspend fun createBucket(bucketName: String): Bucket

    /**
     *
     *
     * Returns a list of all Amazon S3 buckets that the authenticated sender of
     * the request owns.
     *
     *
     *
     * Users must authenticate with a valid AWS Access Key ID that is registered
     * with Amazon S3. Anonymous requests cannot list buckets, and users cannot
     * list buckets that they did not create.
     *
     *
     * @return A list of all of the Amazon S3 buckets owned by the authenticated
     * sender of the request.
     * @throws AWSS3 If any errors are encountered in the client
     * while making the request or handling the response.
     * @see AWSS3.listBuckets
     */
    suspend fun listBuckets(): List<Bucket>

    /**
     *
     *
     * Deletes the specified bucket. All objects (and all object versions, if
     * versioning was ever enabled) in the bucket must be deleted before the
     * bucket itself can be deleted.
     *
     *
     *
     * Only the owner of a bucket can delete it, regardless of the bucket's
     * access control policy.
     *
     *
     * @param bucketName The name of the bucket to delete.
     * @throws AwsException If any errors are encountered in the client
     * while making the request or handling the response.
     * @see AWSS3.deleteBucket
     */
    suspend fun deleteBucket(bucketName: String)
    /**
     * Deletes multiple objects in a single bucket from S3.
     *
     *
     * In some cases, some objects will be successfully deleted, while some
     * attempts will cause an error. If any object in the request cannot be
     * deleted, this method throws a [AwsException] with
     * details of the error.
     *
     * @param bucketName The name of an existing bucket, to which you have permission.
     * @param keys The request object containing all options for
     * deleting multiple objects.
     * @throws AwsException If any errors occurred in Amazon S3 while
     * processing the request.
     */
    suspend fun deleteObjects(bucketName: String, vararg keys: String): DeleteObjectResult
    /**
     *
     *
     * Uploads the specified file to Amazon S3 under the specified bucket and
     * key name.
     *
     *
     *
     * Amazon S3 never stores partial objects; if during this call an exception
     * wasn't thrown, the entire object was stored.
     *
     *
     *
     * If you are uploading or accessing [AWS KMS](http://aws.amazon.com/kms/)-encrypted objects, you need to
     * specify the correct region of the bucket on your client and configure AWS
     * Signature Version 4 for added security. For more information on how to do
     * this, see
     * http://docs.aws.amazon.com/AmazonS3/latest/dev/UsingAWSSDK.html#
     * specify-signature-version
     *
     *
     *
     * Using the file extension, Amazon S3 attempts to determine the correct
     * content type and content disposition to use for the object.
     *
     *
     *
     * If versioning is enabled for the specified bucket, this operation will
     * this operation will never overwrite an existing object with the same key,
     * but will keep the existing object as an older version until that version
     * is explicitly deleted.
     *
     *
     *
     * If versioning is not enabled, this operation will overwrite an existing
     * object with the same key; Amazon S3 will store the last write request.
     * Amazon S3 does not provide object locking. If Amazon S3 receives multiple
     * write requests for the same object nearly simultaneously, all of the
     * objects might be stored. However, a single object will be stored with the
     * final write request.
     *
     *
     *
     * When specifying a location constraint when creating a bucket, all objects
     * added to the bucket are stored in the bucket's region. For example, if
     * specifying a Europe (EU) region constraint for a bucket, all of that
     * bucket's objects are stored in EU region.
     *
     *
     *
     * The specified bucket must already exist and the caller must have
     * permission to the bucket to upload an object.
     *
     *
     * @param bucketName The name of an existing bucket, to which you have permission.
     * @param key The key under which to store the specified file.
     * @param imageFile The file containing the data to be uploaded to Amazon S3.
     * @return A [PutObjectResult] object containing the information
     * returned by Amazon S3 for the newly created object.
     * @throws AwsException If any errors are encountered in the client
     * while making the request or handling the response.
     * @see AWSS3.putObject
     */
    suspend fun putObject(
        bucketName: String,
        key: String,
        imageFile: ImageFile
    ): PutObjectResult

    /**
     *
     *
     * Returns a list of summary information about the objects in the specified
     * buckets. List results are *always* returned in lexicographic
     * (alphabetical) order.
     *
     *
     *
     * Because buckets can contain a virtually unlimited number of keys, the
     * complete results of a list query can be extremely large. To manage large
     * result sets, Amazon S3 uses pagination to split them into multiple
     * responses.
     *
     *
     * The total number of keys in a bucket doesn't substantially affect list
     * performance.
     *
     *
     * @param bucketName The name of the Amazon S3 bucket to list.
     * @return A listing of the objects in the specified bucket, along with any
     * other associated information, such as common prefixes (if a
     * delimiter was specified), the original request parameters, etc.
     * @throws AwsException If any errors are encountered in the client
     * while making the request or handling the response.
     * @see AWSS3.listObjects
     */
    suspend fun listObjects(bucketName: String): ListObjectsResult

    class Builder() {
        fun accessKey(accessKey: String): Builder
        fun secretKey(secretKey: String): Builder
        fun setEndpoint(endpoint: String): Builder
        fun build(): AWSS3
    }
}