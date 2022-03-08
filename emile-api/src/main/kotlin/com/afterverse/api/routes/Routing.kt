package com.afterverse.api.routes

import io.ktor.routing.*

fun Routing.myRoutes() {

    route("routes"){
        profileRoute()
    }
}