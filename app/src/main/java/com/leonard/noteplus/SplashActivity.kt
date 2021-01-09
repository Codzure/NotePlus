package com.leonard.noteplus

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler.postDelayed(
            {
                val intent = Intent(this@SplashActivity, BiometricAuthentication::class.java)
                startActivity(intent)
            }, 2500
        )

//        val connectionManager: ConnectivityManager =
//            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
//        val isActive: Boolean = activeNetwork?.isConnectedOrConnecting == true
//
//        when {
//            isActive -> {
//                handler = Handler()
//                handler.postDelayed(
//                    {
//                        val intent = Intent(this@SplashActivity, BiometricAuthentication::class.java)
//                        startActivity(intent)
//                    }, 2500
//                )
//
//            }
//            else ->{
//            //No internet
//            }
//        }

    }
}