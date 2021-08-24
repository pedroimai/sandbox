package com.pedroimai.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedroimai.characters.CharacterListAdapter.CharacterViewHolder
import com.pedroimai.characters.databinding.CharacterViewHolderBinding

class CharacterListAdapter(private val onClick: (CharacterModel) -> Unit) :
    ListAdapter<CharacterModel, CharacterViewHolder>(CharacterListAdapter) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            CharacterViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    private companion object : DiffUtil.ItemCallback<CharacterModel>() {

        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class CharacterViewHolder(private val binding: CharacterViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CharacterModel, onClick: (CharacterModel) -> Unit) {
            binding.root.setOnClickListener { onClick.invoke(model) }
            binding.txtId.text = model.id
            binding.txtName.text = model.name
        }

    }
}



