package dev.simecek.routines.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.paris.extensions.style
import dev.simecek.routines.R
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.ViewRoutineBinding
import dev.simecek.routines.databinding.ViewTitleBinding
import dev.simecek.routines.listener.FinishRoutineListener
import dev.simecek.routines.model.RoutineListItem

class RoutineListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list: ArrayList<RoutineListItem> = ArrayList()

    fun updateList(updatedList: ArrayList<RoutineListItem>) {
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }

    var finishRoutineListener: FinishRoutineListener? = null

    companion object {
        const val TITLE_VIEW_TYPE = 1
        const val ROUTINE_VIEW_TYPE = 2
    }

    class RoutineViewHolder(val binding: ViewRoutineBinding): RecyclerView.ViewHolder(binding.root)
    class TitleViewHolder(val binding: ViewTitleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TITLE_VIEW_TYPE -> {
                val binding = ViewTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding)
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
            else -> TITLE_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            ROUTINE_VIEW_TYPE -> {
                val routine = (list[position] as RoutineListItem.RoutineItem).routine
                val routineViewHolder = holder as RoutineViewHolder
                routineViewHolder.binding.routine = routine
                setCorrectIconStyle(routineViewHolder, routine)
                routineViewHolder.itemView.setOnClickListener {
                    finishRoutineListener?.onFinishRoutine(routine)
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

    private fun setCorrectIconStyle(routineViewHolder: RoutineViewHolder, routine: Routine) {
        if(routine.isFinished()) {
            routineViewHolder.binding.icon.style(R.style.RoutineWidgetIcon_Finished)
        } else {
            routineViewHolder.binding.icon.style(R.style.RoutineWidgetIcon)
        }
    }

}