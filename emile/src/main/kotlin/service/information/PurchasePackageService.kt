package service.information

import dto.PackageDTO
import model.Packag
import persistence.PurchasePackagePostgreSqlDAO

class PurchasePackageService(private val purchasePackageDAO : PurchasePackagePostgreSqlDAO) {

    suspend fun create(packag: PackageDTO){}

    suspend fun findByLocation(location: String): List<Packag>?{ TODO("Not yet implemented")}

    suspend fun totalPackagePrice(): Double?{ TODO("Not yet implemented")}

    suspend fun totalPurchasePriceByLocation(location: String): Double?{ TODO("Not yet implemented")}
}