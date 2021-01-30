package dev.simecek.routines.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.simecek.routines.R
import dev.simecek.routines.constant.DayPhase
import dev.simecek.routines.constant.IconPickerSelectedType
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.ViewRoutineBinding
import dev.simecek.routines.databinding.ViewTitleBinding
import dev.simecek.routines.model.RoutineListItem
import dev.simecek.routines.model.RoutineListItem.Title
import java.util.*
import kotlin.collections.ArrayList

class RoutineListAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var routines: List<Routine> = ArrayList()
        set(value) {
            field = value
            list = getListWithTitles()
            notifyDataSetChanged()
        }

    var list: ArrayList<RoutineListItem> = ArrayList()
        private set

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
                val binding = ViewRoutineBinding.inflate(LayoutInflater.from(context), parent, false)
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
                val icon = getDrawableIcon(routine.icon)
                routineViewHolder.binding.icon.background = icon
            }
            TITLE_VIEW_TYPE -> {
                val nextRoutine: Routine = (list[position+1] as RoutineListItem.RoutineItem).routine
                val titleViewHolder = holder as TitleViewHolder
                val title = getTitleByDatePhase(nextRoutine.getDayPhase())
                titleViewHolder.binding.text.text = title.text
                titleViewHolder.binding.icon.background = title.icon
            }
        }
    }

    private fun getDrawableIcon(icon: IconPickerSelectedType): Drawable? {
        val id = context.resources.getIdentifier(icon.toString().toLowerCase(Locale.getDefault()), "drawable", context.packageName)
        return ContextCompat.getDrawable(context, id)
    }

    private fun getTitleByDatePhase(dayPhase: DayPhase): Title {
        return when(dayPhase) {
            DayPhase.MORNING -> Title(context.getString(R.string.morning), ContextCompat.getDrawable(context, R.drawable.ic_morning))
            DayPhase.DAY -> Title(context.getString(R.string.noon), ContextCompat.getDrawable(context, R.drawable.ic_sun))
            DayPhase.EVENING -> Title(context.getString(R.string.evening), ContextCompat.getDrawable(context, R.drawable.ic_cloud))
            DayPhase.NIGHT -> Title(context.getString(R.string.night), ContextCompat.getDrawable(context, R.drawable.ic_night))
        }
    }

    private fun getListWithTitles(): ArrayList<RoutineListItem> {
        val result = ArrayList<RoutineListItem>()
        routines.forEachIndexed { index, routine ->
            if (index == 0 || routine.getDayPhase() != routines[index - 1].getDayPhase()) {
                result.add(getTitleByDatePhase(routine.getDayPhase()))
                result.add(RoutineListItem.RoutineItem(routine))
            } else {
                result.add(RoutineListItem.RoutineItem(routine))
            }
        }
        return result
    }
}