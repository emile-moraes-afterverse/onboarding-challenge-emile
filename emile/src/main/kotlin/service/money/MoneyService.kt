package service.money

import model.*
import persistence.StoreApplicationDAO

class MoneyService(private val moneyDAO: StoreApplicationDAO) {

   suspend fun purchasePackage(packag: Packag, user: Profile) {
       moneyDAO.addMoneyPurchasePackage(user.money.plus(packag.quantityCoins!!, packag.quantityGems!!))
   }
    suspend fun purchaseItem(item : Items, user: Profile) {
            moneyDAO.minusMoneyPurchaseItem(user.money.minus(item.coins!!, item.gems!!))

    }
}