package com.afterverse.api.configuration

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

object TypesafeConfiguration {
    const val APPLICATION = "com.afterverse.api.Server"
    private const val CONFIG_ENV_VAR = "ONBOARDING_EMILE"

    val configBaseResource = System.getenv(CONFIG_ENV_VAR)!!
    //private val rootConfig = ConfigFactory.load(configBaseResource)

    //val config = rootConfig.getConfig(APPLICATION)

    val config: Config =
        System.getenv()[CONFIG_ENV_VAR]
            ?.also { System.setProperty("config.resource", it) }
            .let {
                ConfigFactory.load(this.javaClass.classLoader)
            }
}