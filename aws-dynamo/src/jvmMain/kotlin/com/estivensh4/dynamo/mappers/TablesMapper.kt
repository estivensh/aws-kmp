/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.estivensh4.dynamo.CreateTableRequest
import com.estivensh4.dynamo.CreateTableResult
import com.estivensh4.dynamo.DeleteTableResult
import com.estivensh4.dynamo.ListTableResult
import com.estivensh4.dynamo.TableDescription

internal const val TIME = 10L

fun CreateTableRequest.toRequest(): com.amazonaws.services.dynamodbv2.model.CreateTableRequest {
    return com.amazonaws.services.dynamodbv2.model.CreateTableRequest(
        /* attributeDefinitions = */ attributeDefinitions.map { it.toAttribute() },
        /* tableName = */ tableName,
        /* keySchema = */ keySchemaList.map { it.toElement() },
        /* provisionedThroughput = */ ProvisionedThroughput(TIME, TIME)
    )
}

fun com.amazonaws.services.dynamodbv2.model.TableDescription.toDescription(): TableDescription {
    return TableDescription(
        this.tableName,
        this.itemCount
    )
}

fun com.amazonaws.services.dynamodbv2.model.CreateTableResult.toResult(): CreateTableResult {
    return CreateTableResult(
        description = tableDescription.toDescription()
    )
}

fun com.amazonaws.services.dynamodbv2.model.DeleteTableResult.toResult(): DeleteTableResult {
    return DeleteTableResult(
        tableDescription = tableDescription.toDescription()
    )
}

fun com.amazonaws.services.dynamodbv2.model.ListTablesResult.toResult(): ListTableResult {
    return ListTableResult(
        tableNames = tableNames,
        lastEvaluatedTableName = lastEvaluatedTableName
    )
}
