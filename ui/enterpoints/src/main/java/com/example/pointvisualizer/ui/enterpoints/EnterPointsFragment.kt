package com.example.pointvisualizer.ui.enterpoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pointvisualizer.core.loading.ErrorType
import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.features.points.api.entities.PointsList
import com.example.pointvisualizer.ui.enterpoints.databinding.FragmentEnterPointsBinding
import com.example.pointvisualizer.ui.enterpoints.state.EnterPointsScreenState
import com.example.pointvisualizer.ui.enterpoints.state.EnterPointsViewModel
import com.example.pointvisualizer.ui.enterpoints.state.EnteredPointsEvent
import com.example.pointvisualizer.ui.utils.collectWithStarted
import com.example.pointvisualizer.ui.utils.hideKeyboard
import com.example.pointvisualizer.ui.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import com.example.pointvisualizer.ui.core.R as CoreR

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

        viewModel.screenState.collectWithStarted(viewLifecycleOwner) { screenState ->
            onScreenState(screenState)
        }

        viewModel.enteredPointsEvent.collectWithStarted(viewLifecycleOwner) { event ->
            onScreenEvent(event)
        }
    }

    private fun onScreenEvent(event: EnteredPointsEvent) {
        if (event is EnteredPointsEvent.Error){
            handleError(event.errorType)
        }
    }

    private fun handleError(errorType: ErrorType) {
        hideKeyboard(requireActivity())

        val errorMessage = when (errorType) {
            is ErrorType.Network -> getString(CoreR.string.error_network)
            is ErrorType.Server -> formatServerError(errorType)
            is ErrorType.Unexpected -> getString(CoreR.string.error_unexpected)
            else -> getString(CoreR.string.error_unexpected)
        }

        showSnackbar(binding.root, errorMessage)
    }

    private fun formatServerError(errorType: ErrorType.Server): String {
        val serverMessage = errorType.message ?: getString(CoreR.string.error_unexpected)
        return getString(CoreR.string.error_server, serverMessage)
    }

    private fun onScreenState(screenState: EnterPointsScreenState) {
        updateGoButtonState(screenState)
        updatePointsInputError(screenState)
        updateLoadingState(screenState.enterPointsState)
    }

    private fun updateGoButtonState(screenState: EnterPointsScreenState) {
        binding.goButton.isEnabled = screenState.validInput.isValid &&
                screenState.enterPointsState !is LoadingState.Loading
    }

    private fun updatePointsInputError(screenState: EnterPointsScreenState) {
        binding.pointsInputLayout.error = when {
            !screenState.validInput.isNotEmpty -> null
            !screenState.validInput.isLessThanMax -> getString(CoreR.string.more_than_1000_points_error)
            !screenState.validInput.isMoreThanMin -> getString(CoreR.string.less_than_one_point_error)
            else -> null
        }
    }

    private fun updateLoadingState(loadingState: LoadingState<PointsList>) {
        val isLoading = loadingState is LoadingState.Loading
        binding.loadingIndicator.isVisible = isLoading
        binding.pointsInput.isEnabled = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}