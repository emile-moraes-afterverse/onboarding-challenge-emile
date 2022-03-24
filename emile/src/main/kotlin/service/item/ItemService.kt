package service.item

import dto.ItemDTO
import model.Items
import persistence.StoreApplicationDAO
import java.time.Instant
import java.util.*

class ItemService(private val itemDAO: StoreApplicationDAO) {

    suspend fun create(itemRequest : ItemDTO) {
        val id = UUID.randomUUID().toString()
        val item = Items(
            id = id,
            item = itemRequest.item,
            coins = itemRequest.coins,
            gems = itemRequest.gems,
            createdAt = Instant.now()
        )
        this.itemDAO.createItem(item)
        println(item)
    }

    suspend fun findById(itemId: String): Items? = itemDAO.findById(itemId)

}