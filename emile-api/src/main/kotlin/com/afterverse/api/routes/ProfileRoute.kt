package com.afterverse.api.routes

import com.afterverse.api.configuration.Configuration.profileService
import dto.ProfileDTO
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.profileRoute() {

    route("profile") {
        post("create") {
            try {
                val profileRequest = call.receive<ProfileDTO>()
                profileService.create(profileRequest)
                call.respond(HttpStatusCode.Created, profileRequest)
            } catch (e : java.lang.Exception) {
                println(e)
            }

        }

        get("{userid}") {
            try {
                val userId = call.parameters["userid"]
                val profile = userId?.let { it1 -> profileService.findById(it1) }
                if (profile != null) {
                    call.respond(HttpStatusCode.OK, profile)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Profile not found")
                }
            } catch (e : java.lang.Exception) {
            println(e)
            }
        }
    }
}