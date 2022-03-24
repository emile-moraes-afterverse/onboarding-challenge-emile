package model

import java.time.Instant

data class Items (
    val itemId: String,
    val item: String,
    val coins: Int?,
    val gems: Int?,
    val createdAt: Instant
    )