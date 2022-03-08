package com.afterverse.api.routes

import com.afterverse.api.configuration.Configuration
import dto.ProfileDTO
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.profileRoute() {
    install(ContentNegotiation) {
        jackson()
    }

    route("profiles"){
        post("/create") {

            var profileRequest = call.receive<ProfileDTO>()


            Configuration.profileService.createProfile.excute(profileRequest)

            call.respond(HttpStatusCode.Created, profileRequest)

        }

        get("{userid}"){
            val userId = call.parameters["userid"]

            var profile = Configuration.profileService.findById.executeFindById(userId.toString())
            if (profile != null){
                call.respond(HttpStatusCode.OK, profile)
            } else{
                call.respond(HttpStatusCode.NotFound, "Profile not found")
            }

        }
    }
}