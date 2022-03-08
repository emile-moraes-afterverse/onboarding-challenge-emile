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

            val profileRequest = call.receive<ProfileDTO>()
            profileService.create(profileRequest)
            call.respond(HttpStatusCode.Created, profileRequest)
        }

        get("{userid}") {
            val userId = call.parameters["userid"]
            val profile = profileService.findById(userId.toString())
            if (profile != null) {
                call.respond(HttpStatusCode.OK, profile)
            } else {
                call.respond(HttpStatusCode.NotFound, "Profile not found")
            }

        }
    }
}