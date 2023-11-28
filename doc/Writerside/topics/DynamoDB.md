# DynamoDB

<show-structure for="chapter,procedure" depth="4"/>

The following code examples show you how to perform actions and implement common scenarios by using the AWS SDK for Kotlin with DynamoDB.

Actions are code excerpts from larger programs and must be run in context. While actions show you how to call individual service functions, you can see actions in context in their related scenarios and cross-service examples.

Scenarios are code examples that show you how to accomplish a specific task by calling multiple functions within the same service.

Each example includes a link to GitHub, where you can find instructions on how to set up and run the code in context.

## Configuration

<list type="decimal">
<li>Add dependency in module shared/build.gradle.kts
<code-block lang="kotlin">
val commonMain by getting {
    dependencies {
      implementation("io.github.estivensh4:aws-dynamo:$lastVersion")
   }
}
</code-block>
</li>
<li>Add pod in module shared/build.gradle.kts
<code-block lang="kotlin">
cocoapods {
   summary = "Some description for the Shared Module"
   homepage = "Link to the Shared Module homepage"
   version = "1.0"
   ios.deploymentTarget = "14.1"
   framework {
      baseName = "shared"
   }
   pod("AWSDynamoDB", "~> 2.33.4") // add this line
}
</code-block>
</li>
<li>Add pod in iosApp/Podfile
<code-block>
target 'iosApp' do
  use_frameworks!
  platform :ios, '14.1'
  pod 'shared', :path => '../shared'
  pod 'AWSDynamoDB', '~> 2.33.4' # add this line
end
</code-block>
</li>
</list>

## Examples
### Configure client
<code-block lang="kotlin">
val builder = AWSBuilder()
            .accessKey("YOUR_ACCESS_KEY")
            .secretKey("YOUR_SECRET_KEY")
client = builder.buildDynamo()
</code-block>

### Create table
```Kotlin
val request = CreateTableRequest(
    tableName = tableName,
    keySchemaList = listOf(
        KeySchemaElement(
            attributeName = "year",
            keyType = KeyType.HASH
        ),  
        KeySchemaElement(
            attributeName = "title",
            keyType = KeyType.RANGE
        )
    ),
    attributeDefinitions = listOf(
        AttributeDefinition(
            attributeName = "year",
            attributeType = ScalarAttributeType.N
        ),
        AttributeDefinition(
            attributeName = "title",
            attributeType = ScalarAttributeType.S
        )
    )
)

val result = client.createTable(request)
```

### Put item
<code-block lang="kotlin">
val result = client.putItem(
    tableName = tableName,
    items = mapOf(
        "year" to AttributeValue.Builder().withN("6").build(),
        "title" to AttributeValue.Builder().withS("Title").build(),
        "customerId" to AttributeValue.Builder().withS("1").build(),
        "emailAddress" to AttributeValue.Builder().withS("test@test.com").build(),
        "firstName" to AttributeValue.Builder().withS("Test").build(),
        "lastName" to AttributeValue.Builder().withS("Test").build()
    )
)
</code-block>

### Get all tables
<code-block lang="kotlin">
val result = client.getAllTables()
</code-block>

### Get item
<code-block lang="kotlin">
val result = client.getItem(
    tableName = tableName,
    items = mapOf(
        "year" to AttributeValue.Builder().withN(n).build(),
        "title" to AttributeValue.Builder().withS(title).build(),
    )
)
</code-block>

### Delete item
<code-block lang="kotlin">
val result = client.deleteItem(
    tableName = tableName,
    items = mapOf(
        "year" to AttributeValue.Builder().withN(n).build(),
        "title" to AttributeValue.Builder().withS(title).build(),
    )
)
</code-block>

### Query item
<code-block lang="kotlin">
val desiredYear = 10
val result = client.query(
    QueryRequest.Builder()
        .withTableName(tableName)
        .withKeyConditionExpression("#yr = :yearValue")
        .withExpressionAttributeNames(mapOf("#yr" to "year"))
        .withExpressionAttributeValues(
            mapOf(
                ":yearValue" to AttributeValue.Builder().withN(desiredYear.toString()).build()
            )
        )
        .build()
)
</code-block>

### Scan item
<code-block lang="kotlin">
val result = client.scan(
    ScanRequest.Builder()
        .withTableName(tableName)
        .build()
)
</code-block>

### Update item
<code-block lang="kotlin">
val year = 5
val title = "Title"
val result = client.updateItem(
    tableName = tableName,
    keys = mapOf(
        "year" to AttributeValue.Builder().withN(year.toString()).build(),
        "title" to AttributeValue.Builder().withS(title).build()
    ),
    attributeValueUpdate = mapOf(
        "customerId" to AttributeValueUpdate.Builder()
            .withValue(AttributeValue.Builder().withS("Mario").build()).build()
    )
)
</code-block>