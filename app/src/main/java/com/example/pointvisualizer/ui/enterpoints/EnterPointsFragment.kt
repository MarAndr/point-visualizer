package com.example.pointvisualizer.ui.enterpoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pointvisualizer.R
import com.example.pointvisualizer.databinding.FragmentEnterPointsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EnterPointsFragment : Fragment() {

    private var _binding: FragmentEnterPointsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEnterPointsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goButton.setOnClickListener {
            findNavController().navigate(R.id.action_EnterPointsFragment_to_GraphFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}