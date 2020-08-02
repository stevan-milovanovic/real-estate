package com.example.realestate.ui.property

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.realestate.AppExecutors
import com.example.realestate.R
import com.example.realestate.databinding.PropertyItemBinding
import com.example.realestate.model.Property
import com.example.realestate.ui.common.DataBoundListAdapter

class PropertyListAdapter(
    appExecutors: AppExecutors,
    private val favouriteClickHandler: ((Property) -> Unit)?,
    private val cardClickHandler: ((Property) -> Unit)?
) : DataBoundListAdapter<Property, PropertyItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property) =
            oldItem.advertisementId == newItem.advertisementId

        override fun areContentsTheSame(oldItem: Property, newItem: Property) =
            oldItem.advertisementId == newItem.advertisementId &&
                    oldItem.title == newItem.title &&
                    oldItem.favourite == newItem.favourite &&
                    oldItem.street == newItem.street &&
                    oldItem.city == newItem.city &&
                    oldItem.price == newItem.price &&
                    oldItem.currency == newItem.currency &&
                    oldItem.picFilename1Small == newItem.picFilename1Small &&
                    oldItem.favourite == newItem.favourite
    }
) {
    override fun createBinding(parent: ViewGroup): PropertyItemBinding {
        val binding = DataBindingUtil.inflate<PropertyItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.property_item,
            parent,
            false
        )
        binding.root.setOnClickListener {
            binding.property?.let { property ->
                cardClickHandler?.invoke(property)
            }
        }
        binding.favouriteImageView.setOnClickListener {
            binding.property?.let { property ->
                favouriteClickHandler?.invoke(property)
            }
        }
        return binding
    }

    override fun bind(binding: PropertyItemBinding, item: Property) {
        binding.property = item
    }

}