package dev.simecek.routines.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var name: String,
    @ColumnInfo(name = "avatar")var avatar: String = DEFAULT_AVATAR) {

    companion object {
        const val DEFAULT_AVATAR: String = "avatar_1"
        const val ANONYMOUS_USERNAME: String = ""
    }

}
