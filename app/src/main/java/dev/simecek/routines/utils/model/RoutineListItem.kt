package dev.simecek.routines.utils.model

import android.graphics.drawable.Drawable
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.listener.OnClickListener

sealed class RoutineListItem {

    data class RoutineItem(val routine: Routine): RoutineListItem()
    data class TitleItem(var text: String, var icon: Drawable?): RoutineListItem()
    data class ButtonItem(val text: String, val icon: Drawable?, val onClickListener: OnClickListener): RoutineListItem()

}