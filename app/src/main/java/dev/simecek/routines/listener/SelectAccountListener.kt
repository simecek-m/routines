package dev.simecek.routines.listener

import dev.simecek.routines.database.entity.User

interface SelectAccountListener {
    fun onSelect(user: User)
}