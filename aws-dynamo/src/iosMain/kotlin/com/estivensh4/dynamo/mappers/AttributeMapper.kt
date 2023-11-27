/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import cocoapods.AWSDynamoDB.AWSDynamoDBAttributeAction
import cocoapods.AWSDynamoDB.AWSDynamoDBAttributeDefinition
import cocoapods.AWSDynamoDB.AWSDynamoDBAttributeValue
import cocoapods.AWSDynamoDB.AWSDynamoDBAttributeValueUpdate
import cocoapods.AWSDynamoDB.AWSDynamoDBScalarAttributeType
import com.estivensh4.dynamo.AttributeDefinition
import com.estivensh4.dynamo.AttributeValue
import com.estivensh4.dynamo.AttributeValueUpdate
import com.estivensh4.dynamo.ScalarAttributeType
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun AttributeValue.toAttributeValue(): AWSDynamoDBAttributeValue {
    return AWSDynamoDBAttributeValue().apply {
        setS(s)
        setSS(sS)
        setN(n)
        setNS(ns)
        setM(m?.mapValues { it.value.toAttributeValue() })
        setL(l?.map { it.toAttributeValue() })
    }
}

@OptIn(ExperimentalForeignApi::class)
fun AttributeDefinition.toAttribute(): AWSDynamoDBAttributeDefinition {
    return AWSDynamoDBAttributeDefinition()
        .apply {
            setAttributeType(this@toAttribute.attributeType.toType())
            setAttributeName(this@toAttribute.attributeName)
        }
}

@OptIn(ExperimentalForeignApi::class)
fun AttributeValueUpdate.toAttributeValueUpdate(): AWSDynamoDBAttributeValueUpdate {
    val builder = AWSDynamoDBAttributeValueUpdate()
    action?.let { builder.setAction(AWSDynamoDBAttributeAction.valueOf(it)) }
    value?.let { builder.setValue(it.toAttributeValue()) }
    return builder
}

@OptIn(ExperimentalForeignApi::class)
fun ScalarAttributeType.toType(): AWSDynamoDBScalarAttributeType {
    return when (this) {
        ScalarAttributeType.S -> AWSDynamoDBScalarAttributeType.AWSDynamoDBScalarAttributeTypeS
        ScalarAttributeType.N -> AWSDynamoDBScalarAttributeType.AWSDynamoDBScalarAttributeTypeN
        ScalarAttributeType.B -> AWSDynamoDBScalarAttributeType.AWSDynamoDBScalarAttributeTypeB
    }
}
