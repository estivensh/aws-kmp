/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo.mappers

import cocoapods.AWSDynamoDB.AWSDynamoDBCreateTableOutput
import cocoapods.AWSDynamoDB.AWSDynamoDBDeleteTableOutput
import cocoapods.AWSDynamoDB.AWSDynamoDBListTablesOutput
import cocoapods.AWSDynamoDB.AWSDynamoDBTableDescription
import com.estivensh4.dynamo.CreateTableResult
import com.estivensh4.dynamo.DeleteTableResult
import com.estivensh4.dynamo.ListTableResult
import com.estivensh4.dynamo.TableDescription
import kotlinx.cinterop.ExperimentalForeignApi

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBListTablesOutput.toResult(): ListTableResult {
    return ListTableResult(
        tableNames = tableNames as List<String>?,
        lastEvaluatedTableName = lastEvaluatedTableName
    )
}

@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBCreateTableOutput.toResult(): CreateTableResult {
    return CreateTableResult(
        description = tableDescription.toDescription()
    )
}

@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBTableDescription?.toDescription(): TableDescription {
    return TableDescription(
        tableName = this?.tableName,
        itemCount = this?.itemCount?.longValue
    )
}

@OptIn(ExperimentalForeignApi::class)
fun AWSDynamoDBDeleteTableOutput.toResult(): DeleteTableResult {
    return DeleteTableResult(
        tableDescription = tableDescription.toDescription()
    )
}
