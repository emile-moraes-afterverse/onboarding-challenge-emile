package service.profile

import dto.ProfileDTO
import model.Money
import model.Profile
import persistence.ProfileApplicationDAO
import java.util.*

class ProfileService(private val profileDAO : ProfileApplicationDAO) {

    suspend fun create(request: ProfileDTO) {
        val id = UUID.randomUUID().toString()
        val profile = Profile (
            userId = id,
            nickname = request.nickname,
            region = request.region,
            money = Money.DEFAULT

        )
        println(profile)
        this.profileDAO.create(profile)
    }

    suspend fun findById(userId: String): Profile? = profileDAO.findById(userId)
}