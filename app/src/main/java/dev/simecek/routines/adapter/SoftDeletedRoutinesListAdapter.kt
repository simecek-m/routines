package dev.simecek.routines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.ViewRoutineCardBinding
import dev.simecek.routines.listener.ClickRoutineListener
import timber.log.Timber
import javax.inject.Inject

class SoftDeletedRoutinesListAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var list: ArrayList<Routine> = ArrayList()
    var clickListener: ClickRoutineListener? = null

    class RoutineCardViewHolder(val binding: ViewRoutineCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewRoutineCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val routine = list[position]
        val routineCardViewHolder = holder as RoutineCardViewHolder
        routineCardViewHolder.binding.routine = routine
        routineCardViewHolder.itemView.setOnClickListener {
            Timber.i("Clicked on routine card!")
            clickListener?.onClick(routine)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}