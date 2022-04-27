package service.profile

import dto.ProfileDTO
import model.Money
import model.Profile
import persistence.ProfileApplicationDAO
import java.util.*

class ProfileService(private val profileDAO : ProfileApplicationDAO) {

    suspend fun create(request: ProfileDTO) {
        try {
            if(request.moneyDTO.coins < 0 && request.moneyDTO.coins < 0) {
                throw java.lang.Exception( "Coins and gems needs bigger than 0")
            }
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
        } catch (e : java.lang.Exception) {
            println(e.message)
        }

    }

    suspend fun findById(userId: String): Profile = profileDAO.findById(userId)

    suspend fun purchaseItem(itemCoins : Int, itemGems : Int, user: Profile) {
        try {
            if (user.money.coins!! < itemCoins || user.money.gems!! < itemGems){
                throw java.lang.Exception("Coin and gem values cannot be less than the purchase")
            }
            profileDAO.updateMoneyPurchaseItemAndPackage(user.money.minus(itemCoins, itemGems), user.userId)
        } catch (e : java.lang.Exception) {
            println(e.message)
        }

    }

    suspend fun purchasePackage(packageCoins : Int, packageGems : Int, user: Profile){
            profileDAO.updateMoneyPurchaseItemAndPackage(user.money.sum(packageCoins, packageGems), user.userId)

    }
}