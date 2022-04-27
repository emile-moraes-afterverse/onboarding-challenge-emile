package service.item

import dto.ItemDTO
import model.Items
import persistence.StoreApplicationDAO
import java.time.Instant
import java.util.*

class ItemService(private val itemDAO: StoreApplicationDAO) {

    suspend fun create(itemRequest : ItemDTO) {
        if(itemRequest.coins < 0 && itemRequest.gems < 0) {
            throw java.lang.Exception( "Coins and gems needs bigger than 0")
        }
        val id = UUID.randomUUID().toString()
        val item = Items(
            itemId = id,
            item = itemRequest.item,
            coins = itemRequest.coins,
            gems = itemRequest.gems,
            location = itemRequest.location,
            createdAt = Instant.now()
        )
        this.itemDAO.createItem(item)
        println(item)
    }

    suspend fun findById(itemId: String): Items? = itemDAO.findById(itemId)

}