package dev.simecek.routines.database.entity

data class User(var name: String, var avatar: String = DEFAULT_AVATAR) {

    companion object {
        const val DEFAULT_AVATAR: String = "avatar_1"
    }

}