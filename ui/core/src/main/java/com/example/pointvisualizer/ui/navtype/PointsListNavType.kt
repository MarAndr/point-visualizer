package com.example.pointvisualizer.ui.navtype

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.pointvisualizer.features.points.entities.PointsList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PointsListNavType : NavType<PointsList>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: PointsList) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): PointsList? {
        return bundle.getParcelable(key)
    }

    override fun serializeAsValue(value: PointsList): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun parseValue(value: String): PointsList {
        return Json.decodeFromString(value)
    }
}
