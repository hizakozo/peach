package com.example.peachapi.domain

import java.time.LocalDateTime
import java.time.ZoneId

data class PeachDateTime (
    val value: LocalDateTime
) {
    companion object {
        fun now(): PeachDateTime = PeachDateTime(LocalDateTime.now(ZoneId.of("UTC")))
        fun afterMinutes(minutes: Long) =
            PeachDateTime(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(minutes))
    }
}