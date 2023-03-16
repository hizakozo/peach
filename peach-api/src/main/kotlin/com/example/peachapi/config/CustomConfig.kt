package com.example.peachapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "custom")
class CustomConfig {
    lateinit var jwtSecretKey: String
    lateinit var env: String
    lateinit var allowedOrigins: String
    lateinit var googleClientId: String
    lateinit var serviceAccountKeyPath: String
}