package com.afterverse.api.routes

import com.afterverse.api.configuration.Configuration.itemService
import com.afterverse.api.configuration.Configuration.moneyService
import com.afterverse.api.configuration.Configuration.packagService
import com.afterverse.api.configuration.Configuration.profileService
import dto.ItemDTO
import dto.PackageDTO
import dto.ProcessPurchaseItemDTO
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import dto.ProcessPurchasePackageDTO


fun Route.storeRoute() {
    route("store") {
        post("create_item") {
            val itemRequest = call.receive<ItemDTO>()
            itemService.create(itemRequest)
            call.respond(HttpStatusCode.Created, itemRequest)
        }

        post("create_package") {
            val packagRequest = call.receive<PackageDTO>()
            packagService.create(packagRequest)
            call.respond(HttpStatusCode.Created, packagRequest)
        }

        post("purchase_item") {
            val purchaseItem = call.receive<ProcessPurchaseItemDTO>()
            val user = profileService.findById(purchaseItem.userId)
            val item = itemService.findById(purchaseItem.itemId)
            moneyService.purchaseItem(item!!, user!!)
        }

        post("purchase_package"){
            val purchasePackage = call.receive<ProcessPurchasePackageDTO>()
            val user = profileService.findById(purchasePackage.userId)
            val packag = packagService.findById(purchasePackage.packageId)
            moneyService.purchasePackage(packag!!, user!!)
        }
    }
}

