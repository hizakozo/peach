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
            GET("/groups/{groupId}/categories", handler::getCategories)
        }
    }
    @Bean
    fun categoryRoutes(handler: CategoriesController) = coRouter {
        path("/v1").nest {
            POST("/categories", handler::create)
            GET("/categories/{categoryId}/items", handler::getItems)
            GET("/categories/{categoryId}/statuses", handler::getStatues)
        }
    }
    @Bean
    fun itemsRoutes(handler: ItemController) = coRouter {
        path("/v1").nest {
            POST("/items", handler::createItem)
            POST("/items/{itemId}/assignStatus", handler::assignStatus)
        }
    }
    @Bean
    fun statusRoutes(handler: StatusController) = coRouter {
        path("/v1").nest {
            POST("/statuses", handler::create)
            PUT("/statuses/{statusId}", handler::update)
        }
    }
}
