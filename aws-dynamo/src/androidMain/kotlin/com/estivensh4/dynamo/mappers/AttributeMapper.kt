/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import com.estivensh4.dynamo.AttributeDefinition
import com.estivensh4.dynamo.AttributeValue
import com.estivensh4.dynamo.AttributeValueUpdate
import com.estivensh4.dynamo.ScalarAttributeType

fun ScalarAttributeType.toType(): com.amazonaws.services.dynamodbv2.model.ScalarAttributeType {
    return when (this) {
        ScalarAttributeType.S -> com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.S
        ScalarAttributeType.N -> com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.N
        ScalarAttributeType.B -> com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.B
    }
}

fun AttributeDefinition.toAttribute(): com.amazonaws.services.dynamodbv2.model.AttributeDefinition {
    return com.amazonaws.services.dynamodbv2.model.AttributeDefinition(
        attributeName,
        attributeType.toType()
    )
}

fun com.amazonaws.services.dynamodbv2.model.AttributeValue.toAttributeValue(): AttributeValue {
    return AttributeValue.Builder()
        .withS(s)
        .withSS(ss)
        .withN(n)
        .withNS(ns)
        .withM(m?.mapValues { it.value.toAttributeValue() } ?: mapOf())
        .withL(l?.map { it.toAttributeValue() } ?: listOf())
        .build()
}


fun AttributeValue.toAttributeValue(): com.amazonaws.services.dynamodbv2.model.AttributeValue {
    val builder = com.amazonaws.services.dynamodbv2.model.AttributeValue()
    s?.let { builder.withS(it) }
    sS?.let { builder.withSS(it) }
    n?.let { builder.withN(it) }
    ns?.let { builder.withNS(it) }
    m?.let { value -> builder.withM(value.mapValues { it.value.toAttributeValue() }) }
    l?.let { value -> builder.withL(value.map { it.toAttributeValue() }) }
    return builder
}

fun AttributeValueUpdate.toAttributeValueUpdate(): com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate {
    val builder = com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate()
    action?.let { builder.withAction(it) }
    value?.let { builder.withValue(it.toAttributeValue()) }
    return builder
}
