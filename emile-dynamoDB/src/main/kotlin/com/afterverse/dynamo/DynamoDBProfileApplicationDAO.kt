package com.afterverse.dynamo

import com.typesafe.config.Config
import model.Money
import model.Profile
import persistence.ProfileApplicationDAO
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*


class DynamoDBProfileApplicationDAO(config: Config): ProfileApplicationDAO {
    private val tableName = config.getString("com.afterverse.api.dynamodb.onboarding-application.profile-table-name")
    private val dynamoDbClient = DynamoDbClient.builder().region(Region.US_EAST_1)

    override suspend fun create(profile: Profile) {

        val money = mutableMapOf<String, AttributeValue>()
        money["coins"] = AttributeValue.builder().n(profile.money.coins.toString()).build()
        money["gems"] = AttributeValue.builder().n(profile.money.gems.toString()).build()

        val profileItem = mutableMapOf<String, AttributeValue>()
        profileItem["userId"] = AttributeValue.builder().s(profile.userId).build()
        profileItem["nickname"] = AttributeValue.builder().s(profile.nickname).build()
        //profileItem["regionType"] = AttributeValue.builder().s(profile.regionType.toString()).build()
        profileItem["money"] = AttributeValue.builder().m(money).build()
        val putItemRequest = PutItemRequest.builder().tableName(tableName).item(profileItem).build()

        dynamoDbClient.build().putItem(putItemRequest)
    }

    override suspend fun findById(userId: String): Profile {
        try{
            val key = mutableMapOf<String, AttributeValue>()
            key["userId"] = AttributeValue.builder().s(userId).build()
            val getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()
            return dynamoDbClient.build().getItem(getItemRequest).item().toProfile()
        } catch (e: DynamoDbException){
            throw e.cause ?: e
        }

    }

    private fun Map<String, AttributeValue>.toProfile(): Profile {
        return Profile (
            userId = this["userId"]?.s().toString(),
            nickname = this["nickname"]?.s().toString(),
            //regionType = RegionType.valueOf(this["regionType"]?.s().toString()),
            money = Money (
                coins = this["money"]?.m()?.get("coins")?.n()?.toInt(),
                gems = this["money"]?.m()?.get("gems")?.n()?.toInt()
            )
        )
    }

    override suspend fun updateMoneyPurchaseItemAndPackage(money: Money, userId: String) {
        val user = mapOf("userId" to AttributeValue.builder().s(userId).build())

        val updateMoney = mutableMapOf<String, AttributeValueUpdate>()
        val moneyMap = HashMap<String, AttributeValue>()

        moneyMap["gems"] = AttributeValue.builder()
            .n(money.gems.toString()).build()

        moneyMap["coins"] = AttributeValue.builder()
            .n(money.coins.toString()).build()

        updateMoney["money"] = AttributeValueUpdate.builder()
            .value(AttributeValue.builder().m(moneyMap).build())
            .action(AttributeAction.PUT)
            .build();

        val updateMoneyRequest = UpdateItemRequest.builder()
            .tableName(tableName)
            .key(user)
            .attributeUpdates(updateMoney)
            .build()
         dynamoDbClient.build().updateItem(updateMoneyRequest)
    }
}