package com.afterverse.api.configuration

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

object AWSConfiguration {

    private val region = Region.US_EAST_1

    private val dynamoDbClient = DynamoDbClient.builder()

//    private val nettyHttpClient = NettyNioAsyncHttpClient
//        .builder()
//        .build()
//
//    private val aws2CredentialsProvider =
//        DefaultCredentialsProvider.builder()
//            .asyncCredentialUpdateEnabled(true)
//            .build()
//
//    val dynamoDbAsyncClient : DynamoDbAsyncClient =
//        DynamoDbAsyncClient.builder()
//            .region(region)
//            .httpClient(nettyHttpClient)
//            .credentialsProvider(aws2CredentialsProvider)
//            .build()

}