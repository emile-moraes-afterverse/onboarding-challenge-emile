package model

import java.time.Instant
import java.util.*

data class Items(
    var id: UUID,
    var idUser: String,
    var style: String,
    var item: String,
    var payment : Wallet,
    val createdAt: Instant,
    )