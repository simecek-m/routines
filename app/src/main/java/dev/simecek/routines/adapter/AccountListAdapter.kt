package dev.simecek.routines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.simecek.routines.database.entity.User
import dev.simecek.routines.databinding.ViewAccountCardBinding
import dev.simecek.routines.listener.SelectAccountListener
import javax.inject.Inject

class AccountListAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: ArrayList<User> = ArrayList()
    var selectAccountListener: SelectAccountListener? = null

    class AccountCardViewHolder(val binding: ViewAccountCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewAccountCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = list[position]
        val accountCardViewHolder = holder as AccountCardViewHolder
        accountCardViewHolder.binding.user = user
        accountCardViewHolder.itemView.setOnClickListener {
            selectAccountListener?.onSelect(user)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}