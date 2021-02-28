package dev.simecek.routines.model

import android.graphics.drawable.Drawable
import dev.simecek.routines.database.entity.Routine

sealed class RoutineListItem {

    data class RoutineItem(val routine: Routine): RoutineListItem()
    data class TitleItem(var text: String, var icon: Drawable?): RoutineListItem()

}