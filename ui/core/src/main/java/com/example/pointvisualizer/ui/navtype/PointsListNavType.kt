package com.example.pointvisualizer.ui.navtype

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.pointvisualizer.features.points.api.entities.PointsList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PointsListNavType : NavType<PointsList>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: PointsList) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun get(bundle: Bundle, key: String): PointsList {
        return Json.decodeFromString(bundle.getString(key)!!)
    }

    override fun serializeAsValue(value: PointsList): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun parseValue(value: String): PointsList {
        return Json.decodeFromString(value)
    }
}
