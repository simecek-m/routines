package dev.simecek.routines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.simecek.routines.callback.RoutineListDiffCallback
import dev.simecek.routines.databinding.ViewListButtonBinding
import dev.simecek.routines.databinding.ViewRoutineCardBinding
import dev.simecek.routines.databinding.ViewTitleBinding
import dev.simecek.routines.listener.FinishRoutineListener
import dev.simecek.routines.utils.model.RoutineListItem
import javax.inject.Inject

class RoutineListAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TITLE_VIEW_TYPE = 1
        const val ROUTINE_VIEW_TYPE = 2
    }

    val list: ArrayList<RoutineListItem> = ArrayList()

    fun updateList(updatedList: ArrayList<RoutineListItem>) {
        val diffCallback = RoutineListDiffCallback(list, updatedList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(updatedList)
        diffResult.dispatchUpdatesTo(this)
    }

    var finishRoutineListener: FinishRoutineListener? = null

    class RoutineCardViewHolder(val binding: ViewRoutineCardBinding): RecyclerView.ViewHolder(binding.root)
    class TitleViewHolder(val binding: ViewTitleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TITLE_VIEW_TYPE -> {
                val binding = ViewTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding)
            }
            else -> {
                val binding = ViewRoutineCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RoutineCardViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            list[position] is RoutineListItem.RoutineItem -> ROUTINE_VIEW_TYPE
            else -> TITLE_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            ROUTINE_VIEW_TYPE -> {
                val routine = (list[position] as RoutineListItem.RoutineItem).routine
                val routineViewHolder = holder as RoutineCardViewHolder
                routineViewHolder.binding.routine = routine
                routineViewHolder.itemView.setOnClickListener {
                    finishRoutineListener?.onFinishRoutine(routine.id)
                }
            }
            TITLE_VIEW_TYPE -> {
                val title: RoutineListItem.TitleItem = list[position] as RoutineListItem.TitleItem
                val titleViewHolder = holder as TitleViewHolder
                titleViewHolder.binding.text.text = title.text
                titleViewHolder.binding.icon.background = title.icon
            }
        }
    }

}