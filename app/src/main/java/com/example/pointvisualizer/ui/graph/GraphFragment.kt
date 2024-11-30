package com.example.pointvisualizer.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointvisualizer.R
import com.example.pointvisualizer.databinding.FragmentGraphBinding
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.ui.graph.state.GraphViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch


class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<GraphViewModel>()

    private val adapter by lazy {
        PointsAdapter(emptyList())
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

        binding.pointsTable.adapter = adapter
        binding.pointsTable.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(binding.pointsTable.context, DividerItemDecoration.VERTICAL)
        binding.pointsTable.addItemDecoration(dividerItemDecoration)

        binding.lineChart.isDragEnabled = true
        binding.lineChart.description.isEnabled = false
        binding.lineChart.legend.isEnabled = false
        binding.lineChart.xAxis.textColor =
            ContextCompat.getColor(requireContext(), R.color.onBackground)
        binding.lineChart.axisLeft.textColor =
            ContextCompat.getColor(requireContext(), R.color.onBackground)
        binding.lineChart.axisRight.textColor =
            ContextCompat.getColor(requireContext(), R.color.onBackground)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.points.collect { state ->
                    // todo ListAdapter
                    adapter.updateData(state.points)
                    setUpGraph(state.points)
                }
            }
        }
    }

    private fun setUpGraph(pointsList: List<Point>) {
        val entries = pointsList.map { point ->
            Entry(point.x, point.y)
        }
        val dataSet = LineDataSet(entries, "Points")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.accentOnBackground)
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 2f
        dataSet.valueTextSize = 0f
        binding.lineChart.data = LineData(dataSet)
        binding.lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}