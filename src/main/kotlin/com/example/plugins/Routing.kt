package com.example.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.net.http.HttpResponse

fun Application.configureRouting() {
    install(ContentNegotiation){
        json()
    }
    install(Routing) {
        route(path = "/", method = HttpMethod.Get){
            handle {
                call.respondText { "response" }
            }
        }
        get("/user/{username}") {
            val username = call.parameters["username"]
            call.respondText { "Hello $username" }
        }

        get("/person") {
            try {
                val person = Person(name = "camacho", age = "30")
                call.respond(message = person, status = HttpStatusCode.OK)

            } catch (e: Exception) {
                call.respond(message = "${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
    }
//    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }
//    }
}

@Serializable
data class Person(
    val name: String,
    val age: String
)