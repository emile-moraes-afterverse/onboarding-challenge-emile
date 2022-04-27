package com.afterverse.api.routes

import com.afterverse.api.configuration.Configuration
import dto.ItemDTO
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.purchaseinformation() {
    route("purchase") {
        post("create_item") {
            try {
                val purchaseItem = call.receive<ItemDTO>()
                val purchase = Configuration.itemPostgreSqlService.itemSQLService.createSQL(purchaseItem)
                call.respond(HttpStatusCode.Created, purchase)
            } catch (e : java.lang.Exception) {
                println(e)
            }
        }
    }
}