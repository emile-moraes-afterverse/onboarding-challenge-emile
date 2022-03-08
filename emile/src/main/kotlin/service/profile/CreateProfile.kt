package service.profile

import dto.ProfileDTO
import model.Profile
import model.Wallet
import persistence.ProfileApplicationDAO
import java.util.*

class CreateProfile(private val profileDAO : ProfileApplicationDAO) {


    suspend fun excute(request: ProfileDTO) {

        val id = UUID.randomUUID().toString()

        var profile = Profile(
            userId = id,
            nickname = request.nickname,
            region = request.region,
            wallet = Wallet.Default
        )


        this.profileDAO.create(profile)
    }
}