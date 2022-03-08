package model

data class Wallet(
    var coins: Int = 0,
    var gems: Int = 0
){
    companion object{
        val Default = Wallet(coins = 100, gems = 100)
    }
}

operator fun Wallet.plus(other: Wallet) = Wallet(
    coins + other.coins,
    gems + other.gems
)