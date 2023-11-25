/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */


package com.estivensh4.dynamo

import cocoapods.AWSCore.AWSEndpoint
import cocoapods.AWSCore.AWSRegionType
import cocoapods.AWSCore.AWSServiceConfiguration
import cocoapods.AWSCore.AWSServiceManager
import cocoapods.AWSCore.AWSServiceType
import cocoapods.AWSCore.AWSStaticCredentialsProvider
import cocoapods.AWSDynamoDB.AWSDynamoDB
import cocoapods.AWSDynamoDB.AWSDynamoDBCreateTableInput
import cocoapods.AWSDynamoDB.AWSDynamoDBDeleteItemInput
import cocoapods.AWSDynamoDB.AWSDynamoDBDeleteTableInput
import cocoapods.AWSDynamoDB.AWSDynamoDBErrorDomain
import cocoapods.AWSDynamoDB.AWSDynamoDBGetItemInput
import cocoapods.AWSDynamoDB.AWSDynamoDBGetItemOutput
import cocoapods.AWSDynamoDB.AWSDynamoDBListTablesInput
import cocoapods.AWSDynamoDB.AWSDynamoDBProvisionedThroughput
import cocoapods.AWSDynamoDB.AWSDynamoDBPutItemInput
import cocoapods.AWSDynamoDB.AWSDynamoDBPutItemOutput
import cocoapods.AWSDynamoDB.AWSDynamoDBQueryInput
import cocoapods.AWSDynamoDB.AWSDynamoDBQueryOutput
import cocoapods.AWSDynamoDB.AWSDynamoDBScanInput
import cocoapods.AWSDynamoDB.AWSDynamoDBScanOutput
import cocoapods.AWSDynamoDB.AWSDynamoDBSelect
import cocoapods.AWSDynamoDB.AWSDynamoDBUpdateItemInput
import cocoapods.AWSDynamoDB.AWSDynamoDBUpdateItemOutput
import com.estivensh4.common.AWSBuilder
import com.estivensh4.common.util.awaitResult
import com.estivensh4.dynamo.mappers.toAttribute
import com.estivensh4.dynamo.mappers.toAttributeValue
import com.estivensh4.dynamo.mappers.toAttributeValueUpdate
import com.estivensh4.dynamo.mappers.toResult
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.Foundation.NSNumber

@OptIn(ExperimentalForeignApi::class)
actual class AWSDynamo actual constructor(
    private val accessKey: String,
    private val secretKey: String
) {

    private val client: AWSDynamoDB get() = AWSDynamoDB.defaultDynamoDB()

    actual suspend fun createTable(createTableRequest: CreateTableRequest): CreateTableResult {
        val request = AWSDynamoDBCreateTableInput()
            .apply {
                tableName = createTableRequest.tableName
                keySchema = createTableRequest.keySchemaList.map { it.toAttribute() }
                attributeDefinitions =
                    createTableRequest.attributeDefinitions.map { it.toAttribute() }
                this.provisionedThroughput = AWSDynamoDBProvisionedThroughput().apply {
                    setWriteCapacityUnits(NSNumber(number))
                    setReadCapacityUnits(NSNumber(number))
                }
            }
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.createTable(request, it) }
        )
        return result.toResult()
    }

    actual suspend fun getAllTables(): ListTableResult {
        val input = AWSDynamoDBListTablesInput()
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.listTables(input, it) }
        )
        return result.toResult()
    }

    actual suspend fun deleteTable(tableName: String): DeleteTableResult {
        val deleteTableInput = AWSDynamoDBDeleteTableInput()
        deleteTableInput.tableName = tableName
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.deleteTable(deleteTableInput, it) }
        )
        return result.toResult()
    }

    actual suspend fun putItem(
        tableName: String,
        items: Map<String, AttributeValue>
    ): PutItemResult {
        val input = AWSDynamoDBPutItemInput()
            .apply {
                this.tableName = tableName
                this.item = items.mapValues { it.value.toAttributeValue() }
            }
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.putItem(input, it) }
        )
        return result.toResult()
    }

    actual suspend fun deleteItem(
        tableName: String,
        items: Map<String, AttributeValue>
    ): DeleteItemResult {
        val input = AWSDynamoDBDeleteItemInput()
            .apply {
                this.tableName = tableName
                this.key = items.mapValues { it.value.toAttributeValue() }
            }
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.deleteItem(input, it) }
        )
        return result.toResult()
    }

    actual suspend fun getItem(
        tableName: String,
        items: Map<String, AttributeValue>
    ): GetItemResult {
        val input = AWSDynamoDBGetItemInput()
            .apply {
                this.tableName = tableName
                this.key = items.mapValues { it.value.toAttributeValue() }
            }
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.getItem(input, it) }
        )
        return result.toResult()
    }

    actual suspend fun query(
        queryRequest: QueryRequest
    ): QueryResult {
        val input = AWSDynamoDBQueryInput()
            .apply {
                setTableName(queryRequest.tableName)
                setAttributesToGet(queryRequest.attributesToGet)
                queryRequest.limit?.let { setLimit(NSNumber(it)) }
                setIndexName(queryRequest.indexName)
                queryRequest.select?.let { setSelect(AWSDynamoDBSelect.valueOf(it)) }
                setKeyConditionExpression(queryRequest.keyConditionExpression)
                setExpressionAttributeNames(queryRequest.expressionAttributeNames?.toMap())
                setExpressionAttributeValues(
                    queryRequest.expressionAttributeValues?.mapValues { it.value.toAttributeValue() }
                )
            }
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.query(input, it) }
        )
        return result.toResult()
    }

    actual suspend fun updateItem(
        tableName: String,
        keys: Map<String, AttributeValue>,
        attributeValueUpdate: Map<String, AttributeValueUpdate>
    ): UpdateItemResult {
        val input = AWSDynamoDBUpdateItemInput()
            .apply {
                setTableName(tableName)
                setKey(keys.mapValues { it.value.toAttributeValue() })
                setAttributeUpdates(attributeValueUpdate.mapValues { it.value.toAttributeValueUpdate() })
            }
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.updateItem(input, it) }
        )
        return result.toResult()
    }

    actual suspend fun scan(
        scanRequest: ScanRequest
    ): ScanResult {
        val input = AWSDynamoDBScanInput()
            .apply {
                setTableName(scanRequest.tableName)
                setAttributesToGet(scanRequest.attributesToGet)
                scanRequest.limit?.let { setLimit(NSNumber(it)) }
                setIndexName(scanRequest.indexName)
                scanRequest.select?.let { setSelect(AWSDynamoDBSelect.valueOf(it)) }
            }
        val result = awaitResult(
            exception = { it.toException() },
            function = { client.scan(input, it) }
        )
        return result.toResult()
    }

    companion object {
        const val number = 10
    }
}

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBUpdateItemOutput?.toResult(): UpdateItemResult {
    return UpdateItemResult(
        attributes = this?.attributes as Map<String, AttributeValue>? ?: mapOf()
    )
}

@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBScanOutput?.toResult(): ScanResult {
    val item = this?.items as List<Map<String, AttributeValue>>
    return ScanResult(
        items = item
    )
}

@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBQueryOutput?.toResult(): QueryResult {
    val item = this?.items as List<Map<String, AttributeValue>>
    return QueryResult(
        items = item
    )
}

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBGetItemOutput?.toResult(): GetItemResult {
    return GetItemResult(
        item = this?.item as Map<String, AttributeValue>? ?: mapOf(),
    )
}


@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBPutItemOutput?.toResult(): PutItemResult {
    return PutItemResult(
        attributes = this?.attributes as Map<String, AttributeValue>? ?: mapOf(),
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun NSError.toException() = when (domain) {
    AWSDynamoDBErrorDomain -> DynamoException(userInfo["message"].toString())
    else -> General(toString())
}

@OptIn(ExperimentalForeignApi::class)
actual fun AWSBuilder.buildDynamo(): AWSDynamo {
    val credentials = AWSStaticCredentialsProvider(accessKey, secretKey)
    val configuration = AWSServiceConfiguration(
        region = AWSRegionType.AWSRegionUSEast1,
        credentialsProvider = credentials,
        localTestingEnabled = false,
        endpoint = AWSEndpoint(
            useUnsafeURL = true,
            region = AWSRegionType.AWSRegionUSEast1,
            service = AWSServiceType.AWSServiceDynamoDB
        )
    )

    AWSServiceManager.defaultServiceManager()?.setDefaultServiceConfiguration(configuration)
    return AWSDynamo(accessKey, secretKey)
}
