package service.information

import dto.ItemDTO
import model.Items
import persistence.PurchaseItemPostgreSqlDAO
import java.time.Instant
import java.util.*

class PurchaseItemService(private val purchaseItemDAO : PurchaseItemPostgreSqlDAO) {
     suspend fun createSQL(item: ItemDTO) {
         val id = UUID.randomUUID().toString()
        val purchaseItem = Items (
            itemId = id,
            item = item.item,
            coins = item.coins,
            gems = item.gems,
            location = item.location,
            createdAt = Instant.now()
                )
         this.purchaseItemDAO.createItem(purchaseItem)
    }

     suspend fun findByLocation(location: String): List<Items> = purchaseItemDAO.findByLocationPurchase(location)

    suspend fun totalPurchasePriceByLocation(location: String): Double? { TODO("Not yet implemented") }
}