package dev.simecek.routines.utils.drawable

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.utils.constant.Icon

class DrawableUtils {

    companion object {
        const val ICON_ID_NOT_FOUND = 0

        @JvmStatic
        fun getAvatarDrawable(context: Context, avatar: String): Drawable? {
            val avatarId = context.resources.getIdentifier(avatar, "drawable", context.packageName)
            return if(avatarId == ICON_ID_NOT_FOUND) {
                null
            } else {
                ContextCompat.getDrawable(context, avatarId)
            }
        }

        @JvmStatic
        fun getDrawableIcon(context: Context, icon: Icon): Drawable? {
            val iconName: String = icon.toString().lowercase()
            val identifier = "${Routine.ICON_PREFIX}$iconName"
            val iconId = context.resources.getIdentifier(identifier, "drawable", context.packageName)
            return if(iconId == ICON_ID_NOT_FOUND) {
                null
            } else {
                ContextCompat.getDrawable(context, iconId)
            }
        }
    }

}