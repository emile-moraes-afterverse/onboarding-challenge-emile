package model

import java.util.UUID

data class Money(
    val coins: Int?,
    val gems: Int?
) {
    companion object {
        val DEFAULT = Money( coins = 100, gems = 100)
    }
}

fun Money.plus( addCoins: Int, addGems: Int) = Money(
    coins?.plus(addCoins),
    gems?.plus(addGems)
)

fun Money.minus(minusCoins: Int, minusGems: Int) = Money(
    coins?.minus(minusCoins),
    gems?.minus(minusGems)
)
