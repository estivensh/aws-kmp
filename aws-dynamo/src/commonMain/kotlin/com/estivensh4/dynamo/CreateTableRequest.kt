/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

data class CreateTableRequest(
    val tableName: String,
    val attributeDefinitions: List<AttributeDefinition>,
    val keySchemaList: List<KeySchemaElement>
)

data class TableDescription(
    val tableName: String?,
    val itemCount: Long?
)

data class AttributeDefinition(
    val attributeName: String,
    val attributeType: ScalarAttributeType
)
