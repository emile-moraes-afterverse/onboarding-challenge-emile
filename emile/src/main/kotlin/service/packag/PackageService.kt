package service.packag

import dto.PackageDTO
import model.Packag
import persistence.StoreApplicationDAO
import java.time.Instant
import java.util.*

class PackageService(private val packageDAO : StoreApplicationDAO) {

    suspend fun create(packageRequest : PackageDTO){
        if(packageRequest.quantityCoins!! < 0 && packageRequest.quantityGems!! < 0) {
            throw java.lang.Exception( "Coins and gems needs bigger than 0")
        }
        val id = UUID.randomUUID().toString()
        val packagCreate = Packag(
            itemId = id,
            quantityCoins = packageRequest.quantityCoins,
            quantityGems = packageRequest.quantityGems,
            price = packageRequest.price,
            location = packageRequest.location,
            createdAt = Instant.now()
        )
        this.packageDAO.createPackage(packagCreate)
        println(packagCreate)
    }

    suspend fun findById(packagId: String): Packag? = packageDAO.findByIdP(packagId)
}