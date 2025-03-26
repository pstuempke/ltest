package com.stuempke.data

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)