/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

import com.estivensh4.common.AWSBuilder

expect class AWSDynamo constructor(
    accessKey: String,
    secretKey: String
) {
    suspend fun createTable(createTableRequest: CreateTableRequest): CreateTableResult
    suspend fun getAllTables(): ListTableResult
    suspend fun deleteTable(tableName: String): DeleteTableResult
    suspend fun putItem(tableName: String, items: Map<String, AttributeValue>): PutItemResult
    suspend fun updateItem(
        tableName: String,
        keys: Map<String, AttributeValue>,
        attributeValueUpdate: Map<String, AttributeValueUpdate>
    ): UpdateItemResult

    suspend fun deleteItem(tableName: String, items: Map<String, AttributeValue>): DeleteItemResult
    suspend fun getItem(tableName: String, items: Map<String, AttributeValue>): GetItemResult
    suspend fun query(queryRequest: QueryRequest): QueryResult
    suspend fun scan(scanRequest: ScanRequest): ScanResult
}

expect fun AWSBuilder.buildDynamo(): AWSDynamo
