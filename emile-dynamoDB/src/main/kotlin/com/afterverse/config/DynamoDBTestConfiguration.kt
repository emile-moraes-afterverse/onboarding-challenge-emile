package com.afterverse.config

import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI
import java.time.Duration

object DynamoDBTestConfiguration {

    private val DYNAMO_IMAGE = "amazon/dynamodb-local"
    private val DYNAMO_PORT = 8000

    val dynamoDB = DynamoDbAsyncClient.builder()
        .credentialsProvider { AwsBasicCredentials.create("ACCESSKEYID", "test_secret_key") }
        .endpointOverride(URI.create("http://127.0.0.1:8000"))
        .build()


    init {
        val config = DefaultDockerClientConfig.createDefaultConfigBuilder().build()

        val httpClient = ZerodepDockerHttpClient.Builder()
            .dockerHost(config.dockerHost)
            .sslConfig(config.sslConfig)
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build()

        val docker = DockerClientImpl.getInstance(config, httpClient)

        val container =
            docker.createContainerCmd(DYNAMO_IMAGE)
                .withPortBindings(PortBinding.parse("$DYNAMO_PORT:8000"))
                .exec()
        docker.startContainerCmd(container.id).exec()
        Runtime.getRuntime().addShutdownHook(Thread {
            docker.removeContainerCmd(container.id).withForce(true).exec()
        })

        Thread.sleep(5000L)
    }



}
