package com.example.pointvisualizer.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.example.pointvisualizer.R
import com.example.pointvisualizer.databinding.ActivityMainBinding
import com.example.pointvisualizer.features.points.entities.PointsList
import com.example.pointvisualizer.ui.common.navigation.NavigationEvent
import com.example.pointvisualizer.ui.common.navigation.NavigationEventProvider
import com.example.pointvisualizer.ui.common.navigation.NavigationTarget
import com.example.pointvisualizer.ui.enterpoints.EnterPointsFragment
import com.example.pointvisualizer.ui.graph.GraphFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navEventProvider: NavigationEventProvider

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        observeNavigationEvents()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph = navController.createGraph(
            startDestination = NavigationTarget.EnterPointsScreen
        ) {
            fragment<EnterPointsFragment, NavigationTarget.EnterPointsScreen> {
                label = "EnterPointsFragment"
            }
            fragment<GraphFragment, NavigationTarget.GraphScreen>(
                typeMap = mapOf(typeOf<PointsList>() to PointsListNavType)
            ) {
                label = "GraphFragment"
            }
        }
    }

    private fun observeNavigationEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navEventProvider.eventsFlow.collect { navEvent ->
                    when (navEvent) {
                        is NavigationEvent.NavigateTo -> {
                            getNavController().navigate(route = navEvent.target)
                        }

                        NavigationEvent.PopBackStack -> {
                            getNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun getNavController() = findNavController(R.id.nav_host_fragment_content_main)
}