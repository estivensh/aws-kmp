/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import cocoapods.AWSDynamoDB.AWSDynamoDBDeleteItemOutput
import com.estivensh4.dynamo.AttributeValue
import com.estivensh4.dynamo.DeleteItemResult
import kotlinx.cinterop.ExperimentalForeignApi

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBDeleteItemOutput?.toResult(): DeleteItemResult {
    return DeleteItemResult(
        attributes = this?.attributes as Map<String, AttributeValue>? ?: mapOf(),
    )
}
