package model

import model.enums.Region
import java.time.Instant
import java.util.*

data class Package(
    val idUser: UUID,
    val createdAt: Instant,
    val quantityCoins: Int?,
    val quantityGems: Int?,
    val price: Float,
    val region: Region
    )