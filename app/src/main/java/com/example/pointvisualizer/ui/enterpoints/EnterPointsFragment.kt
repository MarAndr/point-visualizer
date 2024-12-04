package com.example.pointvisualizer.ui.enterpoints

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pointvisualizer.R
import com.example.pointvisualizer.core.loading.ErrorType
import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.databinding.FragmentEnterPointsBinding
import com.example.pointvisualizer.ui.enterpoints.state.EnterPointsScreenState
import com.example.pointvisualizer.ui.enterpoints.state.EnterPointsViewModel
import com.example.pointvisualizer.ui.enterpoints.state.EnteredPointsEvent
import com.example.pointvisualizer.ui.utils.showSnackbar
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
            viewModel.getPoints()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { screenState ->
                    updateUI(screenState)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.enteredPointsEvent.collect { event ->
                    when (event) {
                        is EnteredPointsEvent.Error -> {
                            hideKeyboard()
                            val errorMessage = when (val errorType = event.errorType) {
                                is ErrorType.Network -> getString(R.string.error_network)
                                is ErrorType.Server -> {
                                    val serverMessage =
                                        errorType.message ?: getString(R.string.error_unexpected)
                                    getString(R.string.error_server, serverMessage)
                                }

                                is ErrorType.Unexpected -> getString(R.string.error_unexpected)
                                else -> getString(R.string.error_unexpected)
                            }
                            showSnackbar(binding.root, errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = requireActivity().currentFocus
        inputMethodManager?.hideSoftInputFromWindow(
            view?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun updateUI(screenState: EnterPointsScreenState) {
        binding.goButton.isEnabled = screenState.validInput.isValid &&
                screenState.enterPointsState !is LoadingState.Loading
        binding.pointsInputLayout.error = when {
            !screenState.validInput.isNotEmpty -> {
                null
            }

            !screenState.validInput.isLessThanMax -> {
                getString(R.string.more_than_1000_points_error)
            }

            !screenState.validInput.isMoreThanMin -> {
                getString(R.string.less_than_one_point_error)
            }

            else -> {
                null
            }
        }
        binding.loadingIndicator.isVisible =
            screenState.enterPointsState is LoadingState.Loading

        binding.pointsInput.isEnabled =
            screenState.enterPointsState !is LoadingState.Loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}