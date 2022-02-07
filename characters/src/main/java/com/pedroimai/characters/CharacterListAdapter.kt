package com.pedroimai.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pedroimai.characters.CharacterListAdapter.CharacterViewHolder
import com.pedroimai.characters.databinding.CharacterViewHolderBinding
import com.pedroimai.shared.domain.CharactersPayload

class CharacterListAdapter(private val onClick: (CharactersPayload.Characters.CharacterModel) -> Unit) :
    ListAdapter<CharactersPayload.Characters.CharacterModel, CharacterViewHolder>(
        CharacterListAdapter
    ) {
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

    private companion object :
        DiffUtil.ItemCallback<CharactersPayload.Characters.CharacterModel>() {

        override fun areItemsTheSame(
            oldItem: CharactersPayload.Characters.CharacterModel,
            newItem: CharactersPayload.Characters.CharacterModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharactersPayload.Characters.CharacterModel,
            newItem: CharactersPayload.Characters.CharacterModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class CharacterViewHolder(private val binding: CharacterViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: CharactersPayload.Characters.CharacterModel,
            onClick: (CharactersPayload.Characters.CharacterModel) -> Unit
        ) {
            binding.root.setOnClickListener { onClick.invoke(model) }
            binding.txtId.text = model.id
            binding.txtName.text = model.name
        }
    }
}
