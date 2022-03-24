package persistence

import model.Items
import model.Money
import model.Packag

interface StoreApplicationDAO {
    suspend fun createItem (item : Items)
    suspend fun findById(itemId : String) : Items?

    suspend fun createPackage(packag: Packag)
    suspend fun findByIdP(packgId : String) : Packag?

    suspend fun addMoneyPurchasePackage(money: Money)
    suspend fun minusMoneyPurchaseItem(money: Money)
}