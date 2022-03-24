package model

data class Money (
    val coins: Int?,
    val gems: Int?
)
{
    fun minus(minusCoins: Int, minusGems: Int) = Money (
        coins = coins!! - minusCoins,
        gems =  gems!! - minusGems
    )

    fun sum ( addCoins: Int, addGems: Int) = Money (
        coins = coins!! + addCoins,
        gems = gems!! + addGems

    )
}


