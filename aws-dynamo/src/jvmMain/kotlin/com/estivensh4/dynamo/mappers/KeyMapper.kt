/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import com.estivensh4.dynamo.KeySchemaElement
import com.estivensh4.dynamo.KeyType

fun KeyType.toKey(): com.amazonaws.services.dynamodbv2.model.KeyType {
    return when (this) {
        KeyType.HASH -> com.amazonaws.services.dynamodbv2.model.KeyType.HASH
        KeyType.RANGE -> com.amazonaws.services.dynamodbv2.model.KeyType.RANGE
    }
}

fun KeySchemaElement.toElement(): com.amazonaws.services.dynamodbv2.model.KeySchemaElement {
    return com.amazonaws.services.dynamodbv2.model.KeySchemaElement(
        attributeName,
        keyType.toKey()
    )
}
