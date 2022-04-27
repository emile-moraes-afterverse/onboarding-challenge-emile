package postgres

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import model.Items
import persistence.PurchaseItemPostgreSqlDAO

class PurchaseItemDAO(private val postgres : SuspendingConnection) : PurchaseItemPostgreSqlDAO {
    override suspend fun createItem(item: Items) {
        postgres.query(
            INSERT_QUERY,
            item.item,
            item.coins,
            item.gems,
            item.createdAt
        )
    }

    override suspend fun findByLocationPurchase(location: String): List<Items> =
        postgres.query(
            FIND_QUERY,
            location
        ).rows.map { it.toPurchaseItem() }

    companion object {
        internal const val TABLE_NAME = "purchase_item"
        private const val ITEM_ID = "item_id"
        private const val ITEM = "item"
        private const val COINS = "coins"
        private const val GEMS = "gems"
        private const val PROFILE_ID = "profile_id"
        private const val LOCATION = "location"
        private const val CREATED_AT = "created_at"

        private val PROJECTION = """
      $ITEM_ID,
      $ITEM,
      $COINS,
      $GEMS,
      $PROFILE_ID,
      $LOCATION,
      $CREATED_AT
    """.trimIndent()


        private val INSERT_QUERY = """
      INSERT INTO $TABLE_NAME ($PROJECTION)
        VALUES (DEFAULT,?, ?, ?, ?, ?, ?)
    """.trimIndent()

        private val FIND_QUERY = """
      SELECT $PROJECTION FROM $TABLE_NAME WHERE $LOCATION = ?
    """.trimIndent()

        private fun RowData.toPurchaseItem() = Items(
            itemId = string(ITEM_ID),
            item = string(ITEM),
            coins = int(COINS),
            gems = int(GEMS),
            location = string(LOCATION),
            createdAt = instant(CREATED_AT)
        )
    }
}