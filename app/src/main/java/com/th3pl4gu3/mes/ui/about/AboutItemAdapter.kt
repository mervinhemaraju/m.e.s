package com.th3pl4gu3.mes.ui.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.th3pl4gu3.mes.databinding.CustomRecyclerviewAboutSectionBinding
import com.th3pl4gu3.mes.ui.utils.listeners.AboutItemListener


class AboutItemAdapter(
    private val aboutItemListener: AboutItemListener
) : ListAdapter<AboutItem, AboutItemAdapter.ViewHolder>(
    AboutItemDiffCallback()
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(aboutItemListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: CustomRecyclerviewAboutSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            aboutItemListener: AboutItemListener,
            aboutItem: AboutItem
        ) {
            binding.aboutItem = aboutItem
            binding.aboutItemListener = aboutItemListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    CustomRecyclerviewAboutSectionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}

class AboutItemDiffCallback : DiffUtil.ItemCallback<AboutItem>() {

    override fun areItemsTheSame(oldItem: AboutItem, newItem: AboutItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: AboutItem, newItem: AboutItem): Boolean {
        return oldItem == newItem
    }

}

data class AboutItem(
    var title: String,
    var description: String,
    var icon: Int,
    var item: Item
) {
    enum class Item { DEV_RATE_US, DEV_REPORT_BUG, DEV_SUGGESTION, DEV_SHARE, OTHER_VERSION, OTHER_DEVELOPER, OTHER_LICENSES, OTHER_POLICY }
}
