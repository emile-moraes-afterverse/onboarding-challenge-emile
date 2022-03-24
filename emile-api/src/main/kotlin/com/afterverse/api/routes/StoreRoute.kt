package com.afterverse.api.routes


import com.afterverse.api.configuration.Configuration.itemService
import com.afterverse.api.configuration.Configuration.packagService
import com.afterverse.api.configuration.Configuration.profileService
import dto.ItemDTO
import dto.PackageDTO
import dto.ProcessPurchaseItemDTO
import dto.ProcessPurchasePackageDTO
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.storeRoute() {
    route("store") {
        post("create_item") {
            try {
                val itemRequest = call.receive<ItemDTO>()
                itemService.create(itemRequest)
                call.respond(HttpStatusCode.Created, itemRequest)
            } catch (e : java.lang.Exception) {
                println(e)
            }

        }

        post("create_package") {
            val packagRequest = call.receive<PackageDTO>()
            packagService.create(packagRequest)
            call.respond(HttpStatusCode.Created, packagRequest)
        }

        post("purchase_item") {
            try {
                val purchaseItem = call.receive<ProcessPurchaseItemDTO>()
                val user = profileService.findById(purchaseItem.userId)
                val item = itemService.findById(purchaseItem.itemId)
                if (item != null) {
                    profileService.purchaseItem(item.coins!!, item.gems!!, user)
                }
            } catch (e : java.lang.Exception) {
                println(e)
            }

        }

        post("purchase_package"){
            try {
                val purchasePackage = call.receive<ProcessPurchasePackageDTO>()
                val user = profileService.findById(purchasePackage.userId)
                val packag = packagService.findById(purchasePackage.itemId)
                if (packag != null) {
                    profileService.purchasePackage(packag.quantityCoins!!, packag.quantityGems!!, user)
                }
            } catch (e : java.lang.Exception) {
                println(e)
            }
        }
    }
}

