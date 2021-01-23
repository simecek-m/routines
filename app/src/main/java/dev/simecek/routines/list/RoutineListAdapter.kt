package dev.simecek.routines.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.simecek.routines.constant.IconPickerSelectedType
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.RoutineListItemBinding
import java.util.*
import kotlin.collections.ArrayList

class RoutineListAdapter(var list: List<Routine> = ArrayList()): RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder>() {

    class RoutineViewHolder(val binding: RoutineListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val binding = RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val context = holder.itemView.context
        val routine = list[position]
        holder.binding.routine = routine
        val icon = getDrawableIcon(context, routine.icon)
        holder.binding.icon.background = icon
    }

    private fun getDrawableIcon(context: Context, icon: IconPickerSelectedType): Drawable? {
        val id = context.resources.getIdentifier(icon.toString().toLowerCase(Locale.getDefault()), "drawable", context.packageName)
        return ContextCompat.getDrawable(context, id)
    }
}