/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

data class QueryRequest(
    val tableName: String?,
    val indexName: String?,
    val select: String?,
    val attributesToGet: List<String>?,
    val limit: Int?,
    val consistentRead: Boolean?,
    val keyConditionExpression: String?,
    val expressionAttributeNames: Map<String, String>?,
    val expressionAttributeValues: Map<String, AttributeValue>?,
) {
    data class Builder(
        var tableName: String? = null,
        var indexName: String? = null,
        var select: String? = null,
        var attributesToGet: List<String>? = null,
        var limit: Int? = null,
        var consistentRead: Boolean? = null,
        var keyConditionExpression: String? = null,
        var expressionAttributeNames: Map<String, String>? = null,
        var expressionAttributeValues: Map<String, AttributeValue>? = null
    ) {
        fun withTableName(tableName: String?) = apply { this.tableName = tableName }
        fun withIndexName(indexName: String?) = apply { this.indexName = indexName }
        fun withSelect(select: String?) = apply { this.select = select }
        fun withAttributesToGet(attributesToGet: List<String>?) =
            apply { this.attributesToGet = attributesToGet }

        fun withLimit(limit: Int?) = apply { this.limit = limit }
        fun withConsistenRead(consistentRead: Boolean?) =
            apply { this.consistentRead = consistentRead }

        fun withKeyConditionExpression(keyConditionExpression: String?) =
            apply { this.keyConditionExpression = keyConditionExpression }

        fun withExpressionAttributeNames(expressionAttributeNames: Map<String, String>?) =
            apply { this.expressionAttributeNames = expressionAttributeNames }

        fun withExpressionAttributeValues(expressionAttributeValues: Map<String, AttributeValue>?) =
            apply { this.expressionAttributeValues = expressionAttributeValues }

        fun build() = QueryRequest(
            tableName = tableName,
            indexName = indexName,
            select = select,
            attributesToGet = attributesToGet,
            limit = limit,
            consistentRead = consistentRead,
            expressionAttributeNames = expressionAttributeNames,
            expressionAttributeValues = expressionAttributeValues,
            keyConditionExpression = keyConditionExpression
        )
    }
}

data class QueryResult(
    val items: List<Map<String, AttributeValue>>?
)
