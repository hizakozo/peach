package com.example.peachapi.utils

import arrow.core.Either
import com.example.peachapi.config.CustomConfig
import com.example.peachapi.domain.UnAuthorizeException
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.stereotype.Component
import java.io.FileInputStream
import kotlin.math.log


@Component
class JwtUtil(private val config: CustomConfig) {
    fun verifyGoogleIdToken(idToken: String): FirebaseToken? =
        try {
            if (FirebaseApp.getApps().size == 0) initializeFirebaseApp()
            FirebaseAuth.getInstance().verifyIdToken(idToken)
        } catch (e: Exception) {
            null
        }

    fun initializeFirebaseApp(): Unit =
            FileInputStream("src/main/resources/serviceAccountKey.json")
                .let { serviceAccount ->
                    FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build()
                }
                .let { firebaseOptions ->
                    FirebaseApp.initializeApp(firebaseOptions)
                }
}