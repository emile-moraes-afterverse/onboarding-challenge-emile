package com.afterverse.api.plugins
import com.afterverse.api.routes.myRoutes
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*

fun Application.configureRouting(){

  install(ContentNegotiation) {
    jackson()
  }

  routing {
    get("/") {
      call.respondText("Ok!")
    }
    myRoutes()
  }
}
