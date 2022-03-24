package model

import java.time.Instant

data class Packag (
    val id: String,
    val quantityCoins: Int?,
    val quantityGems: Int?,
    val price: Double?,
    val createdAt: Instant
    )