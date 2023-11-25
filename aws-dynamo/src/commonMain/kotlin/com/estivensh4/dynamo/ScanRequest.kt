/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

data class ScanRequest(
    val tableName: String?,
    val indexName:String?,
    val select: String?,
    val attributesToGet: List<String>?,
    val limit: Int?,
    val consistentRead: Boolean?,
) {
    data class Builder(
        var tableName: String? = null,
        var indexName: String? = null,
        var select: String? = null,
        var attributesToGet: List<String>? = null,
        var limit: Int? = null,
        var consistentRead: Boolean? = null
    ) {
        fun withTableName(tableName: String?) = apply { this.tableName = tableName }
        fun withIndexName(indexName: String?) = apply { this.indexName = indexName }
        fun withSelect(select: String?) = apply { this.select = select }
        fun withAttributesToGet(attributesToGet: List<String>?) = apply { this.attributesToGet = attributesToGet }
        fun withLimit(limit: Int?) = apply { this.limit = limit }
        fun withConsistenRead(consistentRead: Boolean?) = apply { this.consistentRead = consistentRead }

        fun build() = ScanRequest(tableName, indexName, select, attributesToGet, limit, consistentRead)
    }
}

data class ScanResult(
    val items: List<Map<String, AttributeValue>>?
)

