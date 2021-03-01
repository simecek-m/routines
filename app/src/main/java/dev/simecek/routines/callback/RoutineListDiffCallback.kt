package dev.simecek.routines.callback

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import dev.simecek.routines.model.RoutineListItem
import dev.simecek.routines.model.RoutineListItem.RoutineItem
import dev.simecek.routines.model.RoutineListItem.TitleItem


class RoutineListDiffCallback(private val oldList: List<RoutineListItem>, private val newList: List<RoutineListItem>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if(oldItem is RoutineItem && newItem is RoutineItem) {
            oldItem.routine.id == newItem.routine.id
        } else if(oldItem is TitleItem && newItem is TitleItem) {
            oldItem.text == newItem.text
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if(oldItem is RoutineItem && newItem is RoutineItem) {
            oldItem.routine == newItem.routine
        } else if(oldItem is TitleItem && newItem is TitleItem) {
            oldItem.text == newItem.text
        } else {
            false
        }
    }

}