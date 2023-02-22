package com.example.peachapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.server.WebFilter

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig(val config: CustomConfig){

    val API_PATH = "/v1/**"

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .withAllowedOrigins()
            .withDisableHttpBasicAuth()
            .withPublicEndPoint(HttpMethod.GET, "/ping")
            .withPublicEndPoint(HttpMethod.POST,"/auth")
            .withSessionAuthentication(API_PATH, SessionJwtFilter(config))
            .csrf().disable()

        return http.build()
    }

    private fun ServerHttpSecurity.withAllowedOrigins(): ServerHttpSecurity =
        this.cors()
            .configurationSource(corsConfigurationSource())
            .and()

    private fun ServerHttpSecurity.withDisableHttpBasicAuth(): ServerHttpSecurity =
        this.formLogin().disable().httpBasic().authenticationEntryPoint(HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val c = CorsConfiguration()
        c.addAllowedMethod(CorsConfiguration.ALL)
        c.addAllowedHeader(CorsConfiguration.ALL)
        c.allowCredentials = true
        c.allowedOrigins = config.allowedOrigins.split(",")
        c.applyPermitDefaultValues()
        c.addExposedHeader("location")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", c)
        return source
    }

    private fun ServerHttpSecurity.withPublicEndPoint(method: HttpMethod, path: String) =
        this.authorizeExchange()
            .pathMatchers(method, path)
            .permitAll().and()

    private fun ServerHttpSecurity.withSessionAuthentication(path: String, filter: WebFilter): ServerHttpSecurity =
        this.authorizeExchange()
            .pathMatchers(path)
            .authenticated()
            .and()
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)

}