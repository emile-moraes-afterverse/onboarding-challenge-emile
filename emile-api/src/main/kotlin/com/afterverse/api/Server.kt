package com.afterverse.api

import com.afterverse.api.JsonExtensions.configureDefaultObjectMapper
import com.afterverse.api.plugins.configureRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.event.Level
import java.util.*

class Server(val port: Int) {

  private val engine = embeddedServer(Netty, port) {
      install(CORS) {
        anyHost()
        allowNonSimpleContentTypes = true
      }

      install(ContentNegotiation) {
        jackson { configureDefaultObjectMapper(this) }
      }

      install(CallLogging) {
        level = Level.INFO

        mdc("tid") {
          UUID.randomUUID().toString()
        }
      }

        configureRouting()
  }

  fun start() {
    engine.start(wait = true)
  }


}
