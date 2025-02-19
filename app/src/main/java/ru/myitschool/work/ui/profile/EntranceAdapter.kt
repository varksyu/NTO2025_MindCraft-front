package ru.sicampus.bootcamp2025.ui.centerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.work.databinding.OneEntryListViewBinding
import ru.myitschool.work.domain.user.EntranceEntity


class EntranceAdapter(
    //private val onCenterClick: (CenterEntity) -> Unit
) : ListAdapter<EntranceEntity, EntranceAdapter.ViewHolder>(EntranceDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneEntryListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: OneEntryListViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : EntranceEntity) {
            binding.time.text = item.enteredAt
            binding.type.text = item.enterType
            binding.entry.text = item.name
        }
    }

    object EntranceDiff : DiffUtil.ItemCallback<EntranceEntity>() {
        override fun areItemsTheSame(oldItem: EntranceEntity, newItem: EntranceEntity): Boolean {
            return oldItem.enteredAt == newItem.enteredAt
        }

        override fun areContentsTheSame(oldItem: EntranceEntity, newItem: EntranceEntity): Boolean {
            return oldItem == newItem
        }

    }


}