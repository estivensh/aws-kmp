# Generate Presigned Url

The Amazon S3 Java SDK provides a simple interface that can be used to store
and retrieve any amount of data, at any time, from anywhere on the web. It
gives any developer access to the same highly scalable, reliable, secure,
fast, inexpensive infrastructure that Amazon uses to run its own global
network of web sites. The service aims to maximize benefits of scale and to
pass those benefits on to developers.

## Configuration

<list type="decimal">
<li>Add dependency
<code-block lang="kotlin">
val commonMain by getting {
    dependencies {
      implementation("io.github.estivensh4:aws-s3:0.1.0")
   }
}
</code-block>
</li>
<li>Add pod
<code-block lang="kotlin">
cocoapods {
   summary = "Some description for the Shared Module"
   homepage = "Link to the Shared Module homepage"
   version = "1.0"
   ios.deploymentTarget = "14.1"
   framework {
      baseName = "shared"
   }
   pod("AWSS3") // add this line depending on the module to be used
}
</code-block>
</li>
</list>

## Examples

with <code>bucketName</code> and <code>key</code>
<code-block lang="kotlin">
fun generatePresignedUrl(): String? {
    val clientS3 = AwsS3.Builder()
        .accessKey("YOUR ACCESS KEY")
        .secretKey("YOUR SECRET KEY")
        .setEndpoint("s3.amazonaws.com")
        .build()
    return clientS3.generatePresignedUrl(
        bucketName = "bucketName",
        key = "key",
        expiration = Clock.System.now().plus(1, DateTimeUnit.HOUR)
    )
}
</code-block>
with <code>bucketName</code>,<code>key</code>,<code>expiration</code> and <code>method</code>
<code-block lang="kotlin">
fun generatePresignedUrl(): String? {
    val clientS3 = AwsS3.Builder()
        .accessKey("YOUR ACCESS KEY")
        .secretKey("YOUR SECRET KEY")
        .setEndpoint("s3.amazonaws.com")
        .build()
    return clientS3.generatePresignedUrl(
        bucketName = "bucketName",
        key = "key",
        expiration = Clock.System.now().plus(1, DateTimeUnit.HOUR),
        method = HttpMethod.GET
    )
}
</code-block>
with <code>data class</code> <code>generatePresignedUrl</code>
<code-block lang="kotlin">
fun generatePresignedUrl(): String? {
    val clientS3 = AwsS3.Builder()
        .accessKey("YOUR ACCESS KEY")
        .secretKey("YOUR SECRET KEY")
        .setEndpoint("s3.amazonaws.com")
        .build()
    return clientS3.generatePresignedUrl(
        GeneratePresignedUrlRequest(
            bucketName = "bucketName",
            key = "key"
        )
    )
}
</code-block>