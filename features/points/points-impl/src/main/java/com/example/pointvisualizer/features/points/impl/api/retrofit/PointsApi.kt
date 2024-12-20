package com.example.pointvisualizer.features.points.impl.api.retrofit

import com.example.pointvisualizer.features.points.impl.api.models.PointResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {
    @GET("api/test/points")
    suspend fun getPoints(@Query("count") postId: Int): PointResponseDto
}