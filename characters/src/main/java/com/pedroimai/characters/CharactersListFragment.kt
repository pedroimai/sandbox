package com.pedroimai.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pedroimai.characters.databinding.CharactersListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        binding.btnRetry.setOnClickListener { viewModel.fetchOrRetry() }
        fetch()
    }

    private fun fetch() {
        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is Result.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.btnRetry.visibility = View.GONE
                        }
                        is Result.Failed -> {
                            binding.btnRetry.visibility = View.VISIBLE
                            binding.loading.visibility = View.GONE
                            Toast.makeText(context, uiState.error.message, Toast.LENGTH_SHORT)
                                .show()

                        }
                        is Result.Success -> {
                            adapter.submitList(uiState.data.characters)
                            binding.loading.visibility = View.GONE
                            binding.btnRetry.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}