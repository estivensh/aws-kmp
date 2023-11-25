/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException
import com.estivensh4.common.AWSBuilder
import com.estivensh4.dynamo.mappers.toAttributeValue
import com.estivensh4.dynamo.mappers.toAttributeValueUpdate
import com.estivensh4.dynamo.mappers.toRequest
import com.estivensh4.dynamo.mappers.toResult

actual class AWSDynamo actual constructor(
    private var accessKey: String,
    private var secretKey: String
) {

    private val client: AmazonDynamoDB
        get() {
            val credentials =
                AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))
            return AmazonDynamoDBClient.builder()
                .withCredentials(credentials)
                .withRegion(Regions.DEFAULT_REGION)
                .build()
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
        return client.deleteTable(tableName).toResult()
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
        val result = client.updateItem(
            tableName,
            keys.mapValues { it.value.toAttributeValue() },
            attributeValueUpdate.mapValues { it.value.toAttributeValueUpdate() }
        )
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
