package persistence

import model.Money
import model.Profile

interface ProfileApplicationDAO {
    suspend fun create(profile : Profile)
    suspend fun findById(userId : String) : Profile
    suspend fun updateMoneyPurchaseItemAndPackage(money: Money, userId: String)
}
