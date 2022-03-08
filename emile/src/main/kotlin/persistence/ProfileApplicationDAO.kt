package persistence

import model.Profile
import java.util.UUID

interface ProfileApplicationDAO {
    suspend fun create(application: Profile)

    suspend fun findById(userId : String) : Profile?
}