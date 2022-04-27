package persistence

import model.Packag

interface PurchasePackagePostgreSqlDAO {
    suspend fun createPackage (packag: Packag)
    suspend fun findByLocationPurchase(location : String) : List<Packag>
    suspend fun totalPackagePrice(): Double?
    suspend fun totalPurchasePriceByLocation(location: String): Double?
}