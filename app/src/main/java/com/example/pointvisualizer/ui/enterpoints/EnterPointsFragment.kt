package com.example.pointvisualizer.ui.enterpoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pointvisualizer.databinding.FragmentEnterPointsBinding
import com.example.pointvisualizer.ui.enterpoints.state.EnterPointsRequestState
import com.example.pointvisualizer.ui.enterpoints.state.EnterPointsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EnterPointsFragment : Fragment() {

    private var _binding: FragmentEnterPointsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<EnterPointsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEnterPointsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pointsInput.doAfterTextChanged {
            viewModel.onCountTextChanged(binding.pointsInput.text.toString())
        }

        binding.goButton.setOnClickListener {
            viewModel.getPoints(binding.pointsInput.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { screenState ->
                    binding.goButton.isEnabled = screenState.validInput &&
                            screenState.enterPointsState !is EnterPointsRequestState.Loading

                    binding.loadingIndicator.isVisible =
                        screenState.enterPointsState is EnterPointsRequestState.Loading
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}