package com.afterverse.api.configuration

import com.afterverse.api.Server
import com.afterverse.api.configuration.AWSConfiguration.dynamoDbAsyncClient
import com.afterverse.api.configuration.TypesafeConfiguration.config
import com.afterverse.dynamo.DynamoDBProfileApplicationDAO
import service.profile.CreateProfile
import service.profile.FindById

object Configuration {
    val server = Server(8080)

    val profileDAO = DynamoDBProfileApplicationDAO(config, dynamoDbAsyncClient)

    object profileService{
        val createProfile = CreateProfile(profileDAO)
        val findById = FindById(profileDAO)

    }
}