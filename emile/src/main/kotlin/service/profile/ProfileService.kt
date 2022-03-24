package service.profile

import dto.ProfileDTO
import model.Money
import model.Profile
import persistence.ProfileApplicationDAO
import java.util.*

class ProfileService(private val profileDAO : ProfileApplicationDAO) {

    suspend fun create(request: ProfileDTO) {
        val id = UUID.randomUUID().toString()
        val profile = Profile (
            userId = id,
            nickname = request.nickname,
           // regionType = RegionType.valueOf(request.regionType),
            money = Money(
                coins = request.moneyDTO.coins,
                gems = request.moneyDTO.gems
            )
        )
        this.profileDAO.create(profile)
    }

    suspend fun findById(userId: String): Profile = profileDAO.findById(userId)

    suspend fun purchaseItem(itemCoins : Int, itemGems : Int, user: Profile) {
        profileDAO.updateMoneyPurchaseItemAndPackage(user.money.minus(itemCoins, itemGems), user.userId)
    }

    suspend fun purchasePackage(packageCoins : Int, packageGems : Int, user: Profile){
        profileDAO.updateMoneyPurchaseItemAndPackage(user.money.sum(packageCoins, packageGems), user.userId)
    }
}