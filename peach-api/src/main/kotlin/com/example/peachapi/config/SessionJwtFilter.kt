package com.example.peachapi.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.peachapi.domain.user.MailAddress
import com.example.peachapi.domain.user.UserId
import com.example.peachapi.domain.user.UserName
import com.example.peachapi.utils.JwtUtil
import com.google.firebase.auth.FirebaseToken
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.springframework.web.util.pattern.PathPatternParser
import reactor.core.publisher.Mono
import java.util.*

class SessionJwtFilter(private val config: CustomConfig, private val jwtUtil: JwtUtil) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        when {
            exchange.request.matchesPattern("/v1/**") -> {
                exchange.request.headers["X_TOKEN"]?.first().toString()
                    .let { token ->
                        jwtUtil.verifyGoogleIdToken(token)
                    }.toUserAuthentication()
                    .let { userAuthentication ->
                        chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(userAuthentication))
                    }
            }
            else -> { chain.filter(exchange)}
        }

    private fun ServerHttpRequest.matchesPattern(pattern: String): Boolean =
        PathPatternParser()
            .parse(pattern)
            .matches(this.path.pathWithinApplication())
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