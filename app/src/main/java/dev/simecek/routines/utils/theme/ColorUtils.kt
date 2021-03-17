package dev.simecek.routines.utils.theme

import android.content.Context
import android.util.TypedValue
import dev.simecek.routines.R

class ColorUtils {

    companion object {

        @JvmStatic
        fun getPrimaryColor(context: Context): Int {
            val value = TypedValue()
            context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
            return value.data
        }

        @JvmStatic
        fun getOnSurfaceColor(context: Context): Int {
            val value = TypedValue()
            context.theme.resolveAttribute(R.attr.colorOnSurface, value, true)
            return value.data
        }
    }

}
