/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import com.estivensh4.dynamo.DeleteItemResult
import com.estivensh4.dynamo.GetItemResult
import com.estivensh4.dynamo.PutItemResult
import com.estivensh4.dynamo.UpdateItemResult

fun com.amazonaws.services.dynamodbv2.model.GetItemResult?.toResult(): GetItemResult {
    return GetItemResult(
        item = this?.item?.mapValues { it.value.toAttributeValue() } ?: emptyMap()
    )
}

fun com.amazonaws.services.dynamodbv2.model.PutItemResult?.toResult(): PutItemResult {
    return PutItemResult(
        attributes = this?.attributes?.mapValues { it.value.toAttributeValue() } ?: emptyMap()
    )
}

fun com.amazonaws.services.dynamodbv2.model.DeleteItemResult?.toResult(): DeleteItemResult {
    return DeleteItemResult(
        attributes = this?.attributes?.mapValues { it.value.toAttributeValue() } ?: emptyMap()
    )
}

fun com.amazonaws.services.dynamodbv2.model.UpdateItemResult?.toResult(): UpdateItemResult {
    return UpdateItemResult(
        attributes = this?.attributes?.mapValues { it.value.toAttributeValue() } ?: mapOf()
    )
}
