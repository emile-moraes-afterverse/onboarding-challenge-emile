package com.afterverse.api

import com.afterverse.api.plugins.configureRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class Server(val port: Int) {

  private val engine = embeddedServer(Netty, port) {
    routing {
      install(CORS) {
        anyHost()
        allowNonSimpleContentTypes = true
      }

      install(ContentNegotiation) {
        jackson()
      }

      configureRouting();
    }
  }

  fun start() {
    engine.start(wait = true)
  }


}
