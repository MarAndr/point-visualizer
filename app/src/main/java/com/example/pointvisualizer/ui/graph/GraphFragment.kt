package com.example.pointvisualizer.ui.graph

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointvisualizer.R
import com.example.pointvisualizer.core.loading.ErrorType
import com.example.pointvisualizer.core.loading.LoadingState
import com.example.pointvisualizer.databinding.FragmentGraphBinding
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.ui.graph.state.GraphScreenEvent
import com.example.pointvisualizer.ui.graph.state.GraphViewModel
import com.example.pointvisualizer.ui.utils.showSnackbar
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<GraphViewModel>()

    private val adapter by lazy {
        PointsTableAdapter()
    }

    private val fileSaveResult = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            return@registerForActivityResult
        }

        val uri = result.data?.data
            ?: return@registerForActivityResult
        saveGraphToFile(uri)
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

        setUpAdapter()

        binding.saveToFile.setOnClickListener {
            createFile()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { state ->
                    adapter.submitList(state.points)
                    setUpGraph(state.points)

                    binding.saveToFile.isEnabled =
                        state.savingLoadingState !is LoadingState.Loading
                    binding.loadingIndicator.isVisible =
                        state.savingLoadingState is LoadingState.Loading
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenEventFlow.collect { event ->
                    when (event) {
                        is GraphScreenEvent.FileSaveSuccess -> {
                            showSnackbar(requireView(), getString(R.string.file_save_success))
                        }

                        is GraphScreenEvent.FileSaveFailure -> {
                            val errorMessage = if (event.errorType is ErrorType.Unexpected){
                                getString(R.string.file_save_fail, "") // todo
                            } else{
                                getString(R.string.error_unexpected)
                            }
                            showSnackbar(
                                requireView(),
                                getString(R.string.file_save_fail, errorMessage)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        with(binding) {
            pointsTable.adapter = adapter
            pointsTable.layoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration =
                DividerItemDecoration(pointsTable.context, DividerItemDecoration.VERTICAL)
            pointsTable.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun saveGraphToFile(uri: Uri) {
        val bitmap = Bitmap.createBitmap(
            binding.lineChart.measuredWidth,
            binding.lineChart.measuredHeight,
            Bitmap.Config.ARGB_8888
        ).also {
            Canvas(it).apply {
                binding.lineChart.background?.draw(this)
                binding.lineChart.draw(this)
            }
        }

        viewModel.saveGraph(uri, bitmap)
    }

    private fun createFile() {
        val fileName = "graph_${System.currentTimeMillis()}.png"
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/png"
            putExtra(Intent.EXTRA_TITLE, fileName)
        }
        fileSaveResult.launch(intent)
    }

    private fun setUpGraph(pointsList: List<Point>) {
        val entries = pointsList.map { point ->
            Entry(point.x, point.y)
        }
        val dataSet = LineDataSet(entries, "").apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = ContextCompat.getColor(requireContext(), R.color.accentOnBackground)
            setDrawCircles(true)
            lineWidth = 2f
            valueTextSize = 0f
            circleRadius = 2f
        }
        with(binding.lineChart) {
            isDragEnabled = true
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.textColor =
                ContextCompat.getColor(requireContext(), R.color.onBackground)
            axisLeft.textColor =
                ContextCompat.getColor(requireContext(), R.color.onBackground)
            axisRight.textColor =
                ContextCompat.getColor(requireContext(), R.color.onBackground)
            data = LineData(dataSet)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}