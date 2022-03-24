package com.afterverse.dynamo

import com.typesafe.config.Config
import kotlinx.coroutines.future.await
import model.Items
import model.Money
import model.Packag
import model.Profile
import model.enums.ItemType
import model.enums.Region
import persistence.StoreApplicationDAO
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import java.time.Instant

class DynamoStoreApplicationDAO (config: Config, private val dynamoDB :DynamoDbAsyncClient) : StoreApplicationDAO {
    private val tableName = config.getString("com.afterverse.api.dynamodb.onboarding-application.profile-table-name")

    override suspend fun createItem(item: Items) {
        val addItem = mutableMapOf<String, AttributeValue>()
        addItem["id"] = AttributeValue.builder().s(item.id).build()
        addItem["item"] = AttributeValue.builder().s(item.item.value).build()
        addItem["coins"] = AttributeValue.builder().s(item.coins.toString()).build()
        addItem["gems"] = AttributeValue.builder().s(item.gems.toString()).build()
        addItem["createdAt"] = AttributeValue.builder().s(item.createdAt.toString()).build()
        val putItemRequest = PutItemRequest.builder().tableName(tableName).item(addItem).build()
        dynamoDB.putItem(putItemRequest)
    }

    override suspend fun createPackage(packag: Packag) {
        val addPackag = mutableMapOf<String, AttributeValue>()
        addPackag["id"] = AttributeValue.builder().s(packag.id).build()
        addPackag["quantityCoins"] = AttributeValue.builder().n(packag.quantityCoins.toString()).build()
        addPackag["quantityGems"] = AttributeValue.builder().n(packag.quantityGems.toString()).build()
        addPackag["price"] = AttributeValue.builder().n(packag.price.toString()).build()
        addPackag["createdAt"] = AttributeValue.builder().s(packag.createdAt.toString()).build()
        val putItemRequest = PutItemRequest.builder().tableName(tableName).item(addPackag).build()
        dynamoDB.putItem(putItemRequest)
    }

    override suspend fun findById(itemId: String): Items? {
        try {
            val key = mutableMapOf<String, AttributeValue>()
            key["id"] = AttributeValue.builder().s(itemId).build()
            val getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()
            return dynamoDB.getItem(getItemRequest).await().item()?.toItems()
        } catch (e: DynamoDbException){
            throw e.cause ?: e
        }
    }

    private fun Map<String, AttributeValue>.toItems() : Items {
        return Items (
            id = this["id"]?.s().toString(),
            item = ItemType.valueOf(this["item"]?.s().toString()),
            coins = this["coins"]?.m()?.get("coins")?.n()?.toInt(),
            gems = this["gems"]?.m()?.get("gems")?.n()?.toInt(),
            createdAt = Instant.now()
        )
    }

    override suspend fun findByIdP(packgId: String): Packag? {
        try {
            val key = mutableMapOf<String, AttributeValue>()
            key["id"] = AttributeValue.builder().s(packgId).build()
            val getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()
            return dynamoDB.getItem(getItemRequest).await().item()?.toPackag()
        } catch (e: DynamoDbException){
            throw e.cause ?: e
        }
    }

    private fun Map<String, AttributeValue>.toPackag() : Packag {
        return Packag (
            id = this["id"]?.s().toString(),
            quantityCoins = this["qCoins"]?.m()?.get("quantityCoins")?.n()?.toInt(),
            quantityGems = this["qGems"]?.m()?.get("quantityGems")?.n()?.toInt(),
            price = this["price"]?.m()?.get("price")?.n()?.toDouble(),
            createdAt = Instant.now()
        )
    }

    override suspend fun addMoneyPurchasePackage(money: Money) {
        TODO("Not yet implemented")
    }

    override suspend fun minusMoneyPurchaseItem(money: Money) {
        TODO("Not yet implemented")
    }

}