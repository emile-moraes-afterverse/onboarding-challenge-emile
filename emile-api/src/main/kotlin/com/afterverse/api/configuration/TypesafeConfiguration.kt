package com.afterverse.api.configuration

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

object TypesafeConfiguration {
    private const val CONFIG_ENV_VAR = "ONBOARDING_EMILE"
    val config: Config =
        System.getenv()[CONFIG_ENV_VAR]
            ?.also { System.setProperty("config.resource", it) }
            .let {
                ConfigFactory.load(this.javaClass.classLoader)
            }
}