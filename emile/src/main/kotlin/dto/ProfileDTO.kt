package dto

import model.enums.Region

data class ProfileDTO(val nickname: String,
                      val region: Region
)