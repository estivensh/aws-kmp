/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

import com.estivensh4.common.AWSBuilder
import com.varabyte.truthish.assertThat
import com.varabyte.truthish.assertThrows
import io.kotest.common.platform
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains

class AWSDynamoTest {

    private lateinit var client: AWSDynamo
    private val accessKey = "accessKey"
    private val secretKey = "secretKey"

    @BeforeTest
    fun setUp() {
        val builder = AWSBuilder()
            .accessKey(accessKey)
            .secretKey(secretKey)
        client = builder.buildDynamo()
    }

    @Test
    fun `create table return success`() = runTest {

        val tableName = "test-table-${platform.name}-2"

        val tableExists = client.getAllTables().tableNames?.contains(tableName) ?: false

        if (tableExists) {
            val result = client.deleteTable(tableName)

            assertThat(
                client.getAllTables().tableNames ?: emptyList()
            ).contains(result.tableDescription.tableName!!)

        } else {

            val request = CreateTableRequest(
                tableName = tableName,
                keySchemaList = listOf(
                    KeySchemaElement(
                        attributeName = "year",
                        keyType = KeyType.HASH
                    ),
                    KeySchemaElement(
                        attributeName = "title",
                        keyType = KeyType.RANGE
                    )
                ),
                attributeDefinitions = listOf(
                    AttributeDefinition(
                        attributeName = "year",
                        attributeType = ScalarAttributeType.N
                    ),
                    AttributeDefinition(
                        attributeName = "title",
                        attributeType = ScalarAttributeType.S
                    )
                )
            )

            val result = client.createTable(request)

            assertContains(
                client.getAllTables().tableNames ?: emptyList(),
                result.description.tableName ?: tableName
            )
        }
    }

    @Test
    fun `create table return failure`() = runTest {

        val tableName = "test-table-${platform.name}"

        val request = CreateTableRequest(
            tableName = tableName,
            keySchemaList = listOf(
                KeySchemaElement(
                    attributeName = "year",
                    keyType = KeyType.HASH
                ),
                KeySchemaElement(
                    attributeName = "title",
                    keyType = KeyType.RANGE
                )
            ),
            attributeDefinitions = listOf(
                AttributeDefinition(
                    attributeName = "year",
                    attributeType = ScalarAttributeType.N
                ),
                AttributeDefinition(
                    attributeName = "title",
                    attributeType = ScalarAttributeType.S
                )
            )
        )

        assertThrows<DynamoException> {
            client.createTable(request)
        }
    }

    @Test
    fun `put item`() = runTest {

        val tableName = "test-table-${platform.name}"


        val tableExists = client.getAllTables().tableNames?.contains(tableName) ?: false

        if (!tableExists) {
            val request = CreateTableRequest(
                tableName = tableName,
                keySchemaList = listOf(
                    KeySchemaElement(
                        attributeName = "year",
                        keyType = KeyType.HASH
                    ),
                    KeySchemaElement(
                        attributeName = "title",
                        keyType = KeyType.RANGE
                    )
                ),
                attributeDefinitions = listOf(
                    AttributeDefinition(
                        attributeName = "year",
                        attributeType = ScalarAttributeType.N
                    ),
                    AttributeDefinition(
                        attributeName = "title",
                        attributeType = ScalarAttributeType.S
                    )
                )
            )
            client.createTable(request)
        }

        val result = client.putItem(
            tableName = tableName,
            items = mapOf(
                "year" to AttributeValue.Builder().withN("6").build(),
                "title" to AttributeValue.Builder().withS("Title").build(),
                "customerId" to AttributeValue.Builder().withS("customer.customerId").build(),
                "emailAddress" to AttributeValue.Builder().withS("customer.emailAddress").build(),
                "firstName" to AttributeValue.Builder().withS("customer.firstName").build(),
                "lastName" to AttributeValue.Builder().withS("customer.lastName").build()
            )
        )

        assertThat(result.attributes).isEmpty()
    }

    @Test
    fun `get an item from table`() = runTest {

        val tableName = "test-table-${platform.name}"
        val n = "5"
        val title = "Title"

        val result = client.getItem(
            tableName = tableName,
            items = mapOf(
                "year" to AttributeValue.Builder().withN(n).build(),
                "title" to AttributeValue.Builder().withS(title).build(),
            )
        )

        if (result.item.isEmpty()) {
            val resultPutItem = client.putItem(
                tableName = tableName,
                items = mapOf(
                    "year" to AttributeValue.Builder().withN(n).build(),
                    "title" to AttributeValue.Builder().withS(title).build(),
                    "customerId" to AttributeValue.Builder().withS("customer.customerId").build(),
                    "emailAddress" to AttributeValue.Builder().withS("customer.emailAddress")
                        .build(),
                    "firstName" to AttributeValue.Builder().withS("customer.firstName").build(),
                    "lastName" to AttributeValue.Builder().withS("customer.lastName").build()
                )
            )
            assertThat(resultPutItem.attributes).isEmpty()
        } else {
            val resultDelete = client.deleteItem(
                tableName = tableName,
                items = mapOf(
                    "year" to AttributeValue.Builder().withN(n).build(),
                    "title" to AttributeValue.Builder().withS(title).build(),
                )
            )
            assertThat(resultDelete.attributes).isEmpty()
        }


    }

    @Test
    fun `query an item from table`() = runTest {

        val tableName = "test-table-${platform.name}"
        val desiredYear = 10
        val result = client.query(
            QueryRequest.Builder()
                .withTableName(tableName)
                .withKeyConditionExpression("#yr = :yearValue")
                .withExpressionAttributeNames(mapOf("#yr" to "year"))
                .withExpressionAttributeValues(
                    mapOf(
                        ":yearValue" to AttributeValue.Builder().withN(desiredYear.toString())
                            .build()
                    )
                )
                .build()
        )

        assertThat(result.items).isNotNull()
    }

    @Test
    fun `scan an item from table`() = runTest {

        val tableName = "test-table-${platform.name}"
        val result = client.scan(
            ScanRequest.Builder()
                .withTableName(tableName)
                .build()
        )

        assertThat(result.items).isNotNull()
    }

    @Test
    fun `update an item from table`() = runTest {

        val tableName = "test-table-${platform.name}"

        val year = 5
        val title = "Title"

        val result = client.updateItem(
            tableName = tableName,
            keys = mapOf(
                "year" to AttributeValue.Builder().withN(year.toString()).build(),
                "title" to AttributeValue.Builder().withS(title).build()
            ),
            attributeValueUpdate = mapOf(
                "customerId" to AttributeValueUpdate.Builder()
                    .withValue(AttributeValue.Builder().withS("Mario").build()).build()
            )
        )

        assertThat(result.attributes).isEmpty()
    }
}
