package com.afterverse.dynamo

import com.typesafe.config.Config
import kotlinx.coroutines.future.await
import model.Money
import model.Profile
import model.enums.Region
import persistence.ProfileApplicationDAO
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest



class DynamoDBProfileApplicationDAO(config: Config, private val dynamoDB: DynamoDbAsyncClient): ProfileApplicationDAO {
    private val tableName = config.getString("com.afterverse.api.dynamodb.onboarding-application.profile-table-name")

    override suspend fun create(profile: Profile) {

        val money = mutableMapOf<String, AttributeValue>()
        money["coins"] = AttributeValue.builder().n(profile.money.coins.toString()).build()
        money["gems"] = AttributeValue.builder().n(profile.money.gems.toString()).build()

        val profileItem = mutableMapOf<String, AttributeValue>()
        profileItem["id"] = AttributeValue.builder().s(profile.userId).build()
        profileItem["nickname"] = AttributeValue.builder().s(profile.nickname).build()
        profileItem["region"] = AttributeValue.builder().s(profile.region.value).build()
        profileItem["money"] = AttributeValue.builder().m(money).build()
        val putItemRequest = PutItemRequest.builder().tableName(tableName).item(profileItem).build()
        dynamoDB.putItem(putItemRequest)
    }

    override suspend fun findById(userId: String): Profile? {
        try{
            val key = mutableMapOf<String, AttributeValue>()
            key["userId"] = AttributeValue.builder().s(userId).build()
            val getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()
            return dynamoDB.getItem(getItemRequest).await().item()?.toProfile()
        } catch (e: DynamoDbException){
            throw e.cause ?: e
        }

    }


    private fun Map<String, AttributeValue>.toProfile(): Profile{
        return Profile(
            userId = this["id"]?.s().toString(),
            nickname = this["nickname"]?.s().toString(),
            region = Region.valueOf(this["region"]?.s().toString()),
            money = Money.DEFAULT
        )
    }
}