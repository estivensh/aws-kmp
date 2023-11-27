/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import com.estivensh4.dynamo.QueryRequest
import com.estivensh4.dynamo.QueryResult
import com.estivensh4.dynamo.ScanRequest
import com.estivensh4.dynamo.ScanResult

fun ScanRequest.toRequest(): com.amazonaws.services.dynamodbv2.model.ScanRequest {
    val builder = com.amazonaws.services.dynamodbv2.model.ScanRequest()
    tableName?.let { builder.withTableName(it) }
    indexName?.let { builder.withIndexName(it) }
    select?.let { builder.withIndexName(it) }
    attributesToGet?.let { builder.setAttributesToGet(it) }
    limit?.let { builder.withLimit(it) }
    consistentRead?.let { builder.withConsistentRead(it) }
    return builder
}

fun com.amazonaws.services.dynamodbv2.model.ScanResult?.toResult(): ScanResult {
    return ScanResult(
        items = this?.items?.map { map -> map.mapValues { it.value.toAttributeValue() } }
            ?: emptyList()
    )
}

fun com.amazonaws.services.dynamodbv2.model.QueryResult?.toResult(): QueryResult {
    return QueryResult(
        items = this?.items?.map { map -> map.mapValues { it.value.toAttributeValue() } }
            ?: emptyList()
    )
}

fun QueryRequest.toRequest(): com.amazonaws.services.dynamodbv2.model.QueryRequest {
    val builder = com.amazonaws.services.dynamodbv2.model.QueryRequest()
    tableName?.let { builder.withTableName(it) }
    indexName?.let { builder.withIndexName(it) }
    select?.let { builder.withIndexName(it) }
    attributesToGet?.let { builder.setAttributesToGet(it) }
    limit?.let { builder.withLimit(it) }
    consistentRead?.let { builder.withConsistentRead(it) }
    keyConditionExpression?.let { builder.withKeyConditionExpression(it) }
    expressionAttributeNames?.let { builder.withExpressionAttributeNames(it) }
    expressionAttributeValues?.let { value ->
        builder.withExpressionAttributeValues(value.mapValues { it.value.toAttributeValue() })
    }
    return builder
}
