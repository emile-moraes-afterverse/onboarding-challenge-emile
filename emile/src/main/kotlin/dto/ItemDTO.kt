package dto

import model.enums.ItemType

class ItemDTO(val item: ItemType,
              val coins : Int,
              val gems : Int
)