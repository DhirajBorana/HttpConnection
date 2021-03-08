package com.example.httpconn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.httpconn.databinding.ItemEmployeeBinding

class EmployeeAdapter(private val listener: Callback) :
    ListAdapter<Employee, EmployeeAdapter.EmployeeViewHolder>(EmployeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(layoutInflater, parent, false)
        val viewHolder = EmployeeViewHolder(binding)
        binding.apply {
            root.setOnClickListener { listener.onClicked(viewHolder.adapterPosition) }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EmployeeViewHolder(private val binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Employee) {
            binding.apply {
                employee = item
                executePendingBindings()
            }
        }
    }

    class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }

    interface Callback {
        fun onClicked(position: Int)
    }
}
