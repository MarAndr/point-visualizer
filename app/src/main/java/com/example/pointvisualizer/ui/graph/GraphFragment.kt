package com.example.pointvisualizer.ui.graph

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pointvisualizer.databinding.FragmentGraphBinding
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.ui.enterpoints.EnterPointsFragment
import com.example.pointvisualizer.ui.graph.state.GraphViewModel
import kotlinx.coroutines.launch


class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<GraphViewModel>()

    val points by lazy {
        arguments?.getSerializable(EnterPointsFragment.POINTS_ARGS) as? ArrayList<Point>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.points.collect { state ->
                    Log.d("GraphFragment", "Received points: ${state?.points}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}