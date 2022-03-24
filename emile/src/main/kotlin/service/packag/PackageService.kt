package service.packag

import dto.PackageDTO
import model.Packag
import persistence.StoreApplicationDAO
import java.time.Instant
import java.util.*

class PackageService(private val packageDAO : StoreApplicationDAO) {

    suspend fun create(packageRequest : PackageDTO){
        val id = UUID.randomUUID().toString()
        val packagCreate = Packag(
            itemId = id,
            quantityCoins = packageRequest.quantityCoins,
            quantityGems = packageRequest.quantityGems,
            price = packageRequest.price,
            createdAt = Instant.now()
        )
        this.packageDAO.createPackage(packagCreate)
        println(packagCreate)
    }

    suspend fun findById(packagId: String): Packag? = packageDAO.findByIdP(packagId)
}