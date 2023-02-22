package com.example.peachapi.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class Router {

    @Bean
    fun healthCheckRoutes(handler: HealthCheckController) = coRouter {
        GET("/ping", handler::healthCheck)
    }
    @Bean
    fun userRoutes(handler: Auth) = coRouter {
        path("/").nest {
            POST("auth", handler::auth)
        }
    }
    @Bean
    fun meRoutes(handler: MeController) = coRouter {
        path("/v1").nest {
            GET("/me", handler::getMe)
        }
    }
    @Bean
    fun groupRoutes(handler: GroupsController) = coRouter {
        path("/v1").nest {
            POST("/groups", handler::create)
            GET("/groups", handler::getGroups)
        }
    }
}