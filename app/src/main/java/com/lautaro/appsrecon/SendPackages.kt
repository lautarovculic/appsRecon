package com.lautaro.appsrecon

import android.content.Context
import android.provider.Settings
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SendPackages(context: Context) {
    private val retrofit: Retrofit
    private val deviceId: String

    init {
        val gson: Gson = GsonBuilder().setLenient().create()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.124:1337/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun sendPackages(packageNames: List<String>) {
        val packageService = retrofit.create(PackageService::class.java)
        val payload = DeviceData(deviceId, packageNames)

        println("📦 Enviando ${packageNames.size} packages con ID: $deviceId")

        val call = packageService.sendPackages(payload)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    println("✅ Packages sent successfully")
                } else {
                    println("❌ Error sending packages: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("💥 Error sending packages: ${t.message}")
            }
        })
    }
}
