package dev.simecek.routines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.simecek.routines.callback.RoutineListDiffCallback
import dev.simecek.routines.databinding.ViewListButtonBinding
import dev.simecek.routines.databinding.ViewRoutineBinding
import dev.simecek.routines.databinding.ViewTitleBinding
import dev.simecek.routines.listener.FinishRoutineListener
import dev.simecek.routines.utils.model.RoutineListItem
import javax.inject.Inject

class RoutineListAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list: ArrayList<RoutineListItem> = ArrayList()

    fun updateList(updatedList: ArrayList<RoutineListItem>) {
        val diffCallback = RoutineListDiffCallback(list, updatedList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(updatedList)
        diffResult.dispatchUpdatesTo(this)
    }

    var finishRoutineListener: FinishRoutineListener? = null

    companion object {
        const val TITLE_VIEW_TYPE = 1
        const val ROUTINE_VIEW_TYPE = 2
        const val BUTTON_VIEW_TYPE = 3
    }

    class RoutineViewHolder(val binding: ViewRoutineBinding): RecyclerView.ViewHolder(binding.root)
    class TitleViewHolder(val binding: ViewTitleBinding): RecyclerView.ViewHolder(binding.root)
    class ButtonViewHolder(val binding: ViewListButtonBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TITLE_VIEW_TYPE -> {
                val binding = ViewTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding)
            }
            BUTTON_VIEW_TYPE -> {
                val binding =  ViewListButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ButtonViewHolder(binding)
            }
            else -> {
                val binding = ViewRoutineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RoutineViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            list[position] is RoutineListItem.RoutineItem -> ROUTINE_VIEW_TYPE
            list[position] is RoutineListItem.ButtonItem -> BUTTON_VIEW_TYPE
            else -> TITLE_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            ROUTINE_VIEW_TYPE -> {
                val routine = (list[position] as RoutineListItem.RoutineItem).routine
                val routineViewHolder = holder as RoutineViewHolder
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
            BUTTON_VIEW_TYPE -> {
                val button: RoutineListItem.ButtonItem = list[position] as RoutineListItem.ButtonItem
                val buttonViewHolder = holder as ButtonViewHolder
                val buttonView = buttonViewHolder.binding.button
                buttonView.text = button.text
                buttonView.icon = button.icon
                buttonView.setOnClickListener {
                    button.onClickListener.onClick()
                }
            }
        }
    }

}