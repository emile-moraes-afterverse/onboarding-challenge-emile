package model

import model.enums.ItemType
import java.time.Instant

data class Items (
    val id: String,
    val item: ItemType,
    val coins: Int?,
    val gems: Int?,
    val createdAt: Instant
    )