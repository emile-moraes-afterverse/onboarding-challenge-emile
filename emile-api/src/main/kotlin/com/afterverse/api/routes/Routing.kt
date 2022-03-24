package com.afterverse.api.routes

import io.ktor.routing.*
import service.money.MoneyService

fun Routing.myRoutes() {
    profileRoute()
    storeRoute()
}