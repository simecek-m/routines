package dev.simecek.routines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.simecek.routines.database.entity.Routine
import dev.simecek.routines.databinding.ViewRoutineCardBinding
import javax.inject.Inject

class SoftDeletedRoutinesListAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var list: ArrayList<Routine> = ArrayList()

    class RoutineCardViewHolder(val binding: ViewRoutineCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewRoutineCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoutineCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val routine = list[position]
        val routineCardViewHolder = holder as RoutineCardViewHolder
        routineCardViewHolder.binding.routine = routine
    }

    override fun getItemCount(): Int {
        return list.size
    }
}