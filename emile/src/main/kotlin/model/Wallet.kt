package model

data class Wallet(
    val coins: Int = 0,
    val gems: Int = 0
) {
    companion object {
        val DEFAULT = Wallet(coins = 100, gems = 100)
    }
}

operator fun Wallet.plus(other: Wallet) = Wallet(
    coins + other.coins,
    gems + other.gems
)