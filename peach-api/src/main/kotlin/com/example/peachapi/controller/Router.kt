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
            DELETE("/groups/{groupId}", handler::delete)
            PUT("/groups/{groupId}", handler::update)
            GET("/groups/{groupId}/categories", handler::getCategories)
            POST("/groups/{groupId}/invite", handler::invite)
        }
    }
    @Bean
    fun categoryRoutes(handler: CategoriesController) = coRouter {
        path("/v1").nest {
            POST("/categories", handler::create)
            DELETE("/categories/{categoryId}", handler::delete)
            PUT("/categories/{categoryId}", handler::update)
            GET("/categories/{categoryId}/items", handler::getItems)
            GET("/categories/{categoryId}/statuses", handler::getStatues)
        }
    }
    @Bean
    fun itemsRoutes(handler: ItemController) = coRouter {
        path("/v1").nest {
            POST("/items", handler::createItem)
            DELETE("/items/{itemId}", handler::delete)
            PUT("/items/{itemId}", handler::update)
            POST("/items/{itemId}/assignStatus", handler::assignStatus)
            POST("/items/{itemId}/unAssignStatus", handler::unAssignStatus)
        }
    }
    @Bean
    fun statusRoutes(handler: StatusController) = coRouter {
        path("/v1").nest {
            POST("/statuses", handler::create)
            PUT("/statuses/{statusId}", handler::update)
            DELETE("/statuses/{statusId}", handler::delete)
        }
    }
}
