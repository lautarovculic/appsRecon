package com.lautaro.appsrecon

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class DeviceData(
    val id: String,
    val packages: List<String>
)

interface PackageService {
    @POST("packages")
    fun sendPackages(@Body deviceData: DeviceData): Call<Void>
}