/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import cocoapods.AWSDynamoDB.AWSDynamoDBKeySchemaElement
import cocoapods.AWSDynamoDB.AWSDynamoDBKeyType
import com.estivensh4.dynamo.KeySchemaElement
import com.estivensh4.dynamo.KeyType
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun KeySchemaElement.toAttribute(): AWSDynamoDBKeySchemaElement {
    return AWSDynamoDBKeySchemaElement()
        .apply {
            setKeyType(this@toAttribute.keyType.toType())
            setAttributeName(this@toAttribute.attributeName)
        }

}

@OptIn(ExperimentalForeignApi::class)
fun KeyType.toType(): AWSDynamoDBKeyType {
    return when (this) {
        KeyType.HASH -> AWSDynamoDBKeyType.AWSDynamoDBKeyTypeHash
        KeyType.RANGE -> AWSDynamoDBKeyType.AWSDynamoDBKeyTypeRange
        else -> AWSDynamoDBKeyType.AWSDynamoDBKeyTypeUnknown
    }
}
