package persistence

import model.Items

interface PurchaseItemPostgreSqlDAO {
    suspend fun createItem (item : Items)
    suspend fun findByLocationPurchase(location : String) : List<Items>
}