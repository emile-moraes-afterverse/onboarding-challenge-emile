package com.afterverse.api.configuration

import com.afterverse.api.Server
import com.afterverse.api.configuration.AWSConfiguration.dynamoDbAsyncClient
import com.afterverse.api.configuration.TypesafeConfiguration.config
import com.afterverse.dynamo.DynamoDBProfileApplicationDAO
import service.profile.ProfileService

object Configuration {

    private val profileDAO = DynamoDBProfileApplicationDAO(config, dynamoDbAsyncClient)
    val profileService = ProfileService(profileDAO)

    val server = Server(8080)
}