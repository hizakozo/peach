package com.example.peachapi.config

import com.example.peachapi.domain.user.MailAddress
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.domain.user.UserName
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.springframework.web.util.pattern.PathPatternParser
import reactor.core.publisher.Mono

class SessionJwtFilter(private val config: CustomConfig, private val firebaseAuth: FirebaseAuth) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        when {
            exchange.request.matchesPattern("/v1/**") -> {
                exchange.request.headers["authorization"]?.first().toString()
                    .let { token ->
                        verifyIdToken(token)?.toUserAuthentication()
                    }
                    ?.let { userAuthentication ->
                        chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(userAuthentication))
                    }?: chain.filter(exchange)
            }
            else -> { chain.filter(exchange)}
        }

    private fun ServerHttpRequest.matchesPattern(pattern: String): Boolean =
        PathPatternParser()
            .parse(pattern)
            .matches(this.path.pathWithinApplication())
    private fun verifyIdToken(idToken: String): FirebaseToken? =
        try {
            firebaseAuth.verifyIdToken(idToken)
        } catch (e: Exception) {
            null
        }
}

data class AuthenticatedUser(
    val userId: UserId,
    val name: UserName,
    val mailAddress: MailAddress
)

class UserAuthentication(private val userDetails: AuthenticatedUser) : Authentication {
    override fun getName(): String = ""

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun getCredentials(): Any = ""

    override fun getDetails(): Any = this.userDetails

    override fun getPrincipal(): Any = ""

    override fun isAuthenticated(): Boolean = true

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw UnsupportedOperationException("")
    }
}

fun FirebaseToken.toUserAuthentication() =
    UserAuthentication(
        AuthenticatedUser(
            UserId(this.uid),
            UserName(this.name),
            MailAddress(this.email)
        )
    )