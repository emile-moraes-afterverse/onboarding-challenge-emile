package model

data class Profile(
    val userId: String,
    val nickname: String,
   // val regionType: RegionType,
    var money: Money
    )