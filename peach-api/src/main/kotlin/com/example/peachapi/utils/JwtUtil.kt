package com.example.peachapi.utils

import com.example.peachapi.config.CustomConfig
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.stereotype.Component
import java.io.FileInputStream


@Component
class JwtUtil(private val config: CustomConfig) {
    fun verifyGoogleIdToken(idToken: String): FirebaseToken {
        val serviceAccount = FileInputStream("src/main/resources/serviceAccountKey.json")
        val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        FirebaseApp.getApps()
            .let { 
                if (it.size == 0) FirebaseApp.initializeApp(options)
            }
        return FirebaseAuth.getInstance().verifyIdToken(idToken)
    }
}