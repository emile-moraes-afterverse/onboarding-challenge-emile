package com.afterverse.config

import com.afterverse.config.DynamoDBTestConfiguration.dynamoDB
import kotlinx.coroutines.future.await
import software.amazon.awssdk.services.dynamodb.model.*

suspend fun recreateTable(
    tableName: String,
    hashKey: String,
    hashKeyType: ScalarAttributeType,
    globalIndices: List<GlobalIndex>? = null,
    localIndices: List<LocalIndex>? = null
) {
    val listTablesResponse = dynamoDB.listTables().await()

    if (listTablesResponse.tableNames().contains(tableName)) {
        dynamoDB.deleteTable(DeleteTableRequest.builder().tableName(tableName).build()).await()
    }

    dynamoDB.createTable(
        CreateTableRequest.builder()
            .tableName(tableName)
            .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(10).writeCapacityUnits(10).build())
            .attributeDefinitions(
                listOf(
                    AttributeDefinition.builder()
                        .attributeName(hashKey)
                        .attributeType(hashKeyType)
                        .build()
                ).plus(
                    globalIndices?.flatMap { index ->
                        index.globalSecondaryIndex.keySchema().map {
                            AttributeDefinition.builder()
                                .attributeName(it.attributeName())
                                .attributeType(index.scalarAttributeType)
                                .build()
                        }
                    } ?: emptyList()
                ).plus(
                    localIndices?.flatMap { index ->
                        index.localSecondaryIndex.keySchema().map {
                            AttributeDefinition.builder()
                                .attributeName(it.attributeName())
                                .attributeType(index.scalarAttributeType)
                                .build()
                        }
                    } ?: emptyList()
                )
            )
            .keySchema(
                listOf(
                    KeySchemaElement.builder()
                        .attributeName(hashKey)
                        .keyType(KeyType.HASH)
                        .build()
                )
            )
            .localSecondaryIndexes(localIndices?.map { it.localSecondaryIndex })
            .globalSecondaryIndexes(globalIndices?.map { it.globalSecondaryIndex })
            .build())
        .await()
}

data class GlobalIndex(
    val globalSecondaryIndex: GlobalSecondaryIndex,
    val scalarAttributeType: ScalarAttributeType
)

data class LocalIndex(
    val localSecondaryIndex: LocalSecondaryIndex,
    val scalarAttributeType: ScalarAttributeType
)