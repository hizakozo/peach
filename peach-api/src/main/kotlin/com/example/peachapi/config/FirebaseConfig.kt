package com.example.peachapi.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirebaseConfig(private val customConfig: CustomConfig) {
    @Bean
    fun getFirebaseAuth(): FirebaseAuth {
        initializeFirebaseApp()
        return FirebaseAuth.getInstance()
    }

    fun initializeFirebaseApp(): Unit =
        FileInputStream(customConfig.serviceAccountKeyPath)
            .let { serviceAccount ->
                FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()
            }
            .let { firebaseOptions ->
                FirebaseApp.initializeApp(firebaseOptions)
            }
}