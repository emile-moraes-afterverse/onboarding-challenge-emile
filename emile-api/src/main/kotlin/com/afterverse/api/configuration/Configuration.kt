package com.afterverse.api.configuration

import com.afterverse.api.Server
import com.afterverse.api.configuration.TypesafeConfiguration.config
import com.afterverse.dynamo.DynamoDBProfileApplicationDAO
import com.afterverse.dynamo.DynamoStoreApplicationDAO
import service.item.ItemService
import service.packag.PackageService
import service.profile.ProfileService

object Configuration {

    private val profileDAO = DynamoDBProfileApplicationDAO(config)
    val profileService = ProfileService(profileDAO)

    private val itemDAO = DynamoStoreApplicationDAO(config)
    val itemService = ItemService(itemDAO)

    private val packagDAO = DynamoStoreApplicationDAO(config)
    val packagService = PackageService(packagDAO)

    val server = Server(8000)
}