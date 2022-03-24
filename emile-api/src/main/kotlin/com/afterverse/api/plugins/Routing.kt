package com.afterverse.api.plugins
import com.afterverse.api.routes.myRoutes
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*

fun Application.configureRouting(){
  routing {
    get("/") {
      call.respondText("Ok!")
    }
    myRoutes()
  }
}
