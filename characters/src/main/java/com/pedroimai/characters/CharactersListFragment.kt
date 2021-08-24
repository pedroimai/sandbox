package com.pedroimai.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pedroimai.characters.databinding.CharactersListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersListFragment : Fragment() {
    private lateinit var binding: CharactersListFragmentBinding
    private val adapter by lazy {
        CharacterListAdapter {
            Toast.makeText(
                context,
                "$it clicked",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private val viewModel: CharactersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.adapter = adapter
        adapter.submitList(viewModel.getCharacters())
    }
}