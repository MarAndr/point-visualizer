package com.example.pointvisualizer.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.example.pointvisualizer.R
import com.example.pointvisualizer.databinding.ActivityMainBinding
import com.example.pointvisualizer.features.points.entities.PointsList
import com.example.pointvisualizer.ui.enterpoints.EnterPointsFragment
import com.example.pointvisualizer.ui.graph.GraphFragment
import com.example.pointvisualizer.ui.navigation.EnterPointsScreen
import com.example.pointvisualizer.ui.navigation.GraphScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = EnterPointsScreen
        ) {
            fragment<EnterPointsFragment, EnterPointsScreen> {
                label = "EnterPointsFragment"
            }
            fragment<GraphFragment, GraphScreen>(
                typeMap = mapOf(typeOf<PointsList>() to PointsListNavType)
            ) {
                label = "GraphFragment"
            }
        }
    }
}