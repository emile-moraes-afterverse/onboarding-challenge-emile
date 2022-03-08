package service.profile

import dto.ProfileDTO
import model.Profile
import model.Wallet
import persistence.ProfileApplicationDAO
import java.util.*

class ProfileService(private val profileDAO : ProfileApplicationDAO) {


    suspend fun create(request: ProfileDTO) {

        val id = UUID.randomUUID().toString()

        var profile = Profile(
            userId = id,
            nickname = request.nickname,
            region = request.region,
            wallet = Wallet.DEFAULT
        )


        this.profileDAO.create(profile)
    }

    suspend fun findById(userId: String): Profile? = profileDAO.findById(userId)
}