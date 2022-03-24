package com.afterverse.api.configuration

import com.afterverse.api.Server
import com.afterverse.api.configuration.AWSConfiguration.dynamoDbAsyncClient
import com.afterverse.api.configuration.TypesafeConfiguration.config
import com.afterverse.dynamo.DynamoDBProfileApplicationDAO
import com.afterverse.dynamo.DynamoStoreApplicationDAO
import service.item.ItemService
import service.money.MoneyService
import service.packag.PackageService
import service.profile.ProfileService

object Configuration {

    private val profileDAO = DynamoDBProfileApplicationDAO(config, dynamoDbAsyncClient)
    val profileService = ProfileService(profileDAO)

    private val itemDAO = DynamoStoreApplicationDAO(config, dynamoDbAsyncClient)
    val itemService = ItemService(itemDAO)

    private val packagDAO = DynamoStoreApplicationDAO(config, dynamoDbAsyncClient)
    val packagService = PackageService(packagDAO)

    private val moneyDAO = DynamoStoreApplicationDAO(config, dynamoDbAsyncClient)
    val moneyService = MoneyService(moneyDAO)

    val server = Server(8080)
}