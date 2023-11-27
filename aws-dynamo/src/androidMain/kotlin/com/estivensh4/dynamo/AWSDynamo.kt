/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
import com.estivensh4.common.AWSBuilder
import com.estivensh4.dynamo.mappers.toAttributeValue
import com.estivensh4.dynamo.mappers.toAttributeValueUpdate
import com.estivensh4.dynamo.mappers.toRequest
import com.estivensh4.dynamo.mappers.toResult

actual class AWSDynamo actual constructor(
    private val accessKey: String,
    private val secretKey: String
) {

    private val client: AmazonDynamoDBClient
        get() {
            val credentials = BasicAWSCredentials(accessKey, secretKey)
            return AmazonDynamoDBClient(credentials)
        }

    actual suspend fun createTable(createTableRequest: CreateTableRequest): CreateTableResult {
        return try {
            val result = client.createTable(createTableRequest.toRequest())
            result.toResult()
        } catch (@Suppress("SwallowedException") exception: ResourceInUseException) {
            throw DynamoException(exception.message)
        }
    }

    actual suspend fun getAllTables(): ListTableResult {
        return client.listTables().toResult()
    }

    actual suspend fun deleteTable(tableName: String): DeleteTableResult {
        return try {
            client.deleteTable(tableName).toResult()
        } catch (@Suppress("SwallowedException") exception: ResourceInUseException) {
            throw DynamoException(exception.message)
        }
    }

    actual suspend fun putItem(
        tableName: String,
        items: Map<String, AttributeValue>
    ): PutItemResult {
        val result = client.putItem(tableName, items.mapValues { it.value.toAttributeValue() })
        return result.toResult()
    }

    actual suspend fun deleteItem(
        tableName: String,
        items: Map<String, AttributeValue>
    ): DeleteItemResult {
        val result = client.deleteItem(tableName, items.mapValues { it.value.toAttributeValue() })
        return result.toResult()
    }

    actual suspend fun getItem(
        tableName: String,
        items: Map<String, AttributeValue>
    ): GetItemResult {
        val result = client.getItem(tableName, items.mapValues { it.value.toAttributeValue() })
        return result.toResult()
    }

    actual suspend fun query(
        queryRequest: QueryRequest
    ): QueryResult {
        val result = client.query(queryRequest.toRequest())
        return result.toResult()
    }

    actual suspend fun updateItem(
        tableName: String,
        keys: Map<String, AttributeValue>,
        attributeValueUpdate: Map<String, AttributeValueUpdate>
    ): UpdateItemResult {
        val updateItemRequest = UpdateItemRequest()
            .apply {
                withTableName(tableName)
                withKey(keys.mapValues { it.value.toAttributeValue() })
                withAttributeUpdates(attributeValueUpdate.mapValues { it.value.toAttributeValueUpdate() })
            }
        val result = client.updateItem(updateItemRequest)
        return result.toResult()
    }

    actual suspend fun scan(scanRequest: ScanRequest): ScanResult {
        val result = client.scan(scanRequest.toRequest())
        return result.toResult()
    }
}

actual fun AWSBuilder.buildDynamo(): AWSDynamo {
    return AWSDynamo(accessKey, secretKey)
}
