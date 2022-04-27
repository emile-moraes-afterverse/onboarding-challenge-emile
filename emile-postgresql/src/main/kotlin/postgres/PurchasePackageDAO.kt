package postgres

import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import model.Packag
import persistence.PurchasePackagePostgreSqlDAO

class PurchasePackageDAO (private val postgres : SuspendingConnection) : PurchasePackagePostgreSqlDAO {
    override suspend fun createPackage(packag: Packag) {
        postgres.query(
            FIND_QUERY,
            packag.quantityCoins,
            packag.quantityGems,
            packag.price,
            packag.location,
            packag.createdAt
        )
    }

    override suspend fun findByLocationPurchase(location: String): List<Packag> =
        postgres.query(
            FIND_QUERY,
            location
        ).rows.map { it.toPurchasePackage() }

    override suspend fun totalPackagePrice(): Double? =
        postgres.query(
            SUM
        ).rows.map { it.getDouble(0) }
            .first()

    override suspend fun totalPurchasePriceByLocation(location: String): Double? =
        postgres.query(
            SUM_PRICE_BY_LOCATION,
            location
        ).rows.map { it.getDouble(0) }
            .first()

    companion object {
        internal const val TABLE_NAME = "purchase_package"
        private const val PACKAGE_ID = "item_id"
        private const val COINS = "quantityCoins"
        private const val GEMS = "quantityGems"
        private const val PRICE = "price"
        private const val PROFILE_ID = "profile_id"
        private const val LOCATION = "location"
        private const val CREATED_AT = "created_at"

        private val PROJECTION = """
      $PACKAGE_ID,
      $COINS,
      $GEMS,
      $PRICE,
      $PROFILE_ID,
      $LOCATION,
      $CREATED_AT
    """.trimIndent()


        private val INSERT_QUERY = """
      INSERT INTO $TABLE_NAME ($PROJECTION)
        VALUES (DEFAULT,?, ?, ?, ?, ?)
    """.trimIndent()

        private val FIND_QUERY = """
      SELECT $PROJECTION FROM $TABLE_NAME WHERE $LOCATION = ?
    """.trimIndent()

        private val SUM = """
            SELECT SUM($PRICE) AS TOTAL FROM $TABLE_NAME
        """.trimIndent()

        private val SUM_PRICE_BY_LOCATION = """
            SELECT SUM($PRICE) AS TOTAL_LOCATION FROM $TABLE_NAME WHERE LOCATION = ?
        """.trimIndent()

        private fun RowData.toPurchasePackage() = Packag(
            itemId = string(PACKAGE_ID),
            quantityCoins = int(COINS),
            quantityGems = int(GEMS),
            price = double(PRICE),
            location = string(LOCATION),
            createdAt = instant(CREATED_AT)
        )
    }

}