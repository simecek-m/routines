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
import dev.simecek.routines.databinding.RoutineListItemBinding
import dev.simecek.routines.databinding.RoutineListItemWithTitleBinding
import dev.simecek.routines.model.Title
import java.util.*
import kotlin.collections.ArrayList

class RoutineListAdapter(var list: ArrayList<Routine> = ArrayList()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ROUTINE_VIEW_TYPE = 1
        const val ROUTINE_WITH_TITLE_VIEW_TYPE = 2
    }

    class RoutineViewHolder(val binding: RoutineListItemBinding): RecyclerView.ViewHolder(binding.root)
    class RoutineWithTitleViewHolder(val binding: RoutineListItemWithTitleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ROUTINE_WITH_TITLE_VIEW_TYPE -> {
                val binding = RoutineListItemWithTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RoutineWithTitleViewHolder(binding)
            }
            else -> {
                val binding = RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RoutineViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) ROUTINE_WITH_TITLE_VIEW_TYPE
        else if(list[position-1].getDayPhase() != list[position].getDayPhase()) ROUTINE_WITH_TITLE_VIEW_TYPE
        else ROUTINE_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        val routine = list[position]
        when(holder.itemViewType) {
            ROUTINE_VIEW_TYPE -> {
                val routineViewHolder = holder as RoutineViewHolder
                routineViewHolder.binding.routine = routine
                val icon = getDrawableIcon(context, routine.icon)
                routineViewHolder.binding.icon.background = icon
            }
            ROUTINE_WITH_TITLE_VIEW_TYPE -> {
                val routineWithTitleViewHolder = holder as RoutineWithTitleViewHolder
                routineWithTitleViewHolder.binding.routine = routine
                val icon = getDrawableIcon(context, routine.icon)
                routineWithTitleViewHolder.binding.icon.background = icon
                routineWithTitleViewHolder.binding.title = getTitleByDatePhase(context, routine.getDayPhase())
            }
        }
    }

    private fun getDrawableIcon(context: Context, icon: IconPickerSelectedType): Drawable? {
        val id = context.resources.getIdentifier(icon.toString().toLowerCase(Locale.getDefault()), "drawable", context.packageName)
        return ContextCompat.getDrawable(context, id)
    }

    private fun getTitleByDatePhase(context: Context, dayPhase: DayPhase): Title {
        return when(dayPhase) {
            DayPhase.MORNING -> Title(context.getString(R.string.morning), ContextCompat.getDrawable(context, R.drawable.ic_morning))
            DayPhase.DAY -> Title(context.getString(R.string.noon), ContextCompat.getDrawable(context, R.drawable.ic_sun))
            DayPhase.EVENING -> Title(context.getString(R.string.evening), ContextCompat.getDrawable(context, R.drawable.ic_cloud))
            DayPhase.NIGHT -> Title(context.getString(R.string.night), ContextCompat.getDrawable(context, R.drawable.ic_night))
        }
    }
}