package com.afterverse.dynamo

import com.typesafe.config.Config
import model.Items
import model.Packag
import persistence.StoreApplicationDAO
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import java.time.Instant

class DynamoStoreApplicationDAO (config: Config) : StoreApplicationDAO {
    private val tableName = config.getString("com.afterverse.api.dynamodb.onboarding-application.store-table-name")

    private val dynamoDbClient = DynamoDbClient.builder().region(Region.US_EAST_1)

    override suspend fun createItem(item: Items) {
        val addItem = mutableMapOf<String, AttributeValue>()
        addItem["itemId"] = AttributeValue.builder().s(item.itemId).build()
        addItem["item"] = AttributeValue.builder().s(item.item).build()
        addItem["coins"] = AttributeValue.builder().n(item.coins.toString()).build()
        addItem["gems"] = AttributeValue.builder().n(item.gems.toString()).build()
        addItem["location"] = AttributeValue.builder().s(item.location).build()
        addItem["createdAt"] = AttributeValue.builder().s(item.createdAt.toString()).build()
        val putItemRequest = PutItemRequest.builder().tableName(tableName).item(addItem).build()

        dynamoDbClient.build().putItem(putItemRequest)
    }

    override suspend fun createPackage(packag: Packag) {
        val addPackag = mutableMapOf<String, AttributeValue>()
        addPackag["itemId"] = AttributeValue.builder().s(packag.itemId).build()
        addPackag["quantityCoins"] = AttributeValue.builder().n(packag.quantityCoins.toString()).build()
        addPackag["quantityGems"] = AttributeValue.builder().n(packag.quantityGems.toString()).build()
        addPackag["price"] = AttributeValue.builder().n(packag.price.toString()).build()
        addPackag["location"] = AttributeValue.builder().s(packag.location).build()
        addPackag["createdAt"] = AttributeValue.builder().s(packag.createdAt.toString()).build()
        val putItemRequest = PutItemRequest.builder().tableName(tableName).item(addPackag).build()
        dynamoDbClient.build().putItem(putItemRequest)
    }

    override suspend fun findById(itemId: String): Items? {
        try {
            val key = mutableMapOf<String, AttributeValue>()
            key["itemId"] = AttributeValue.builder().s(itemId).build()
            val getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()
            return dynamoDbClient.build().getItem(getItemRequest).item()?.toItems()
        } catch (e: DynamoDbException){
            throw e.cause ?: e
        }
    }

    private fun Map<String, AttributeValue>.toItems() : Items {
        return Items (
            itemId = this["itemId"]?.s().toString(),
            //item = ItemType.valueOf(this["item"]?.s().toString()),
            item = this["item"]?.s().toString(),
            coins = this["coins"]?.n()?.toInt(),
            gems = this["gems"]?.n()?.toInt(),
            location = this["location"]?.s().toString(),
            createdAt = Instant.parse(this["createdAt"]?.s().toString())
        )
    }

    override suspend fun findByIdP(packgId: String): Packag {
        try {
            val key = mutableMapOf<String, AttributeValue>()
            key["itemId"] = AttributeValue.builder().s(packgId).build()
            val getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()
            return dynamoDbClient.build().getItem(getItemRequest).item().toPackag()
        } catch (e: DynamoDbException){
            throw e.cause ?: e
        }
    }

    private fun Map<String, AttributeValue>.toPackag() : Packag {
        return Packag (
            itemId = this["itemId"]?.s().toString(),
            quantityCoins = this["quantityCoins"]?.n()?.toInt(),
            quantityGems = this["quantityGems"]?.n()?.toInt(),
            price = this["price"]?.n()?.toDouble(),
            location = this["location"]?.s().toString(),
            createdAt = Instant.parse(this["createdAt"]?.s().toString())
        )
    }
}
