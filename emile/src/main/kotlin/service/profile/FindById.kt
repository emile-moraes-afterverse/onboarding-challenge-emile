package service.profile

import model.Profile
import persistence.ProfileApplicationDAO

class FindById(private val profileDAO : ProfileApplicationDAO) {

    suspend fun executeFindById(userId: String): Profile? = profileDAO.findById(userId)
}