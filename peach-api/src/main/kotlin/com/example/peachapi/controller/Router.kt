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
    @Bean
    fun categoryRoutes(handler: CategoriesController) = coRouter {
        path("/v1").nest {
            POST("/group/{groupId}/categories", handler::create)
            GET("/group/{groupId}/categories", handler::getCategories)
        }
    }
    @Bean
    fun itemsRoutes(handler: ItemController) = coRouter {
        path("/v1").nest {
            GET("/group/{groupId}/category/{categoryId}/items", handler::getItems)
            POST("/group/{groupId}/category/{categoryId}/items", handler::createItem)
        }
    }
    @Bean
    fun statusRoutes(handler: ItemController) = coRouter {
        path("/v1").nest {
            GET("/group/{groupId}/category/{categoryId}/statuses", handler::getItems)
        }
    }
}
