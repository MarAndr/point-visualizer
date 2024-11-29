package com.example.pointvisualizer.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pointvisualizer.databinding.FragmentGraphBinding
import com.example.pointvisualizer.features.points.entities.Point
import com.example.pointvisualizer.ui.enterpoints.EnterPointsFragment


class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null

    private val binding get() = _binding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}