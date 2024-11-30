package com.example.pointvisualizer.ui.enterpoints.state

import com.example.pointvisualizer.features.points.entities.PointsList

interface EnteredPointsEvent {
    data class NavigateToGraphFragment(val points: PointsList): EnteredPointsEvent
}
