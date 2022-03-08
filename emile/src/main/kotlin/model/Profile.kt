package model

import model.enums.Region

data class Profile(
    val userId: String,
    val nickname: String,
    val region: Region,
    val wallet: Wallet)
