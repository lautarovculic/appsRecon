package com.lautaro.appsrecon

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.pm.PackageManager
import android.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (checkSelfPermission(Manifest.permission.QUERY_ALL_PACKAGES) == PackageManager.PERMISSION_GRANTED) {
            val packageManager = packageManager
            val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)

            val packageNames = mutableListOf<String>()
            for (packageInfo in installedPackages) {
                val packageName = packageInfo.packageName
                println("Package: $packageName")
                packageNames.add(packageName)
            }

            val sendPackages = SendPackages(this)
            sendPackages.sendPackages(packageNames)
        } else {
            requestPermissions(arrayOf(Manifest.permission.QUERY_ALL_PACKAGES), REQUET_CODE)
        }
    }
    companion object {
        private const val REQUET_CODE = 123
    }
}