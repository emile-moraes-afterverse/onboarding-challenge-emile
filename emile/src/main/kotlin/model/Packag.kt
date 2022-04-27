package model

import java.time.Instant

data class Packag (
    val itemId: String,
    val quantityCoins: Int?,
    val quantityGems: Int?,
    val price: Double?,
    val location : String,
    val createdAt: Instant
    )