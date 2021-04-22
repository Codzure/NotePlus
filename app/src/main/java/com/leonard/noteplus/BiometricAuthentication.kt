package com.leonard.noteplus

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_biometric_authentication.*
import java.util.*

class BiometricAuthentication : AppCompatActivity() {

    private lateinit var biometricManager: BiometricManager
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric_authentication)
        biometricManager = BiometricManager.from(this)
        val executor = ContextCompat.getMainExecutor(this)
        checkBiometricStatus(biometricManager)

        biometricPrompt = BiometricPrompt(
                this,
                executor,
                object : BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        showToast("Authentication error $errString")
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        goToHomeFragment()
                    }

                    override fun onAuthenticationFailed(){
                        super.onAuthenticationFailed()
                        showToast("Authentication Failed")
                    }
                }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Register for Biometric.")
                .setDescription("Use device biometric authentication for to secure your account.")
                .setConfirmationRequired(false)
                .setNegativeButtonText("Cancel")
                .build()

        fingerPrint.setOnClickListener{
            val canAuthenticate = BiometricManager.from(this).canAuthenticate()
            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                biometricPrompt.authenticate(promptInfo)
            }else
                conditionCheck()
        }


    }

    private fun goToHomeFragment() {
        val intent = Intent(this@BiometricAuthentication, DashboardActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("LogNotTimber")
    private fun checkBiometricStatus(biometricManager: BiometricManager){
        when(biometricManager.canAuthenticate()){
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.e(TAG,"checkBiometricStatus: App can use biometric authenticate")

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e(TAG,"checkBiometricStatus: No biometric features available in this device")

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->
                Log.e(TAG,"checkBiometricStatus: Biometric features currently unavailable")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->
                Log.e(TAG,"checkBiometricStatus: The user hasn't enrolled with any biometric configurations in this device")
        }
    }


    private fun conditionCheck() {
        dialog = Dialog(this)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.cant_authenticate)

        val window: Window = Objects.requireNonNull<Window>(dialog.window)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        (dialog.findViewById(R.id.loanStatus) as TextView).text = "Biometric Authentication!"
        (dialog.findViewById(R.id.errorDescription) as TextView).text =
                "Login using inbuilt Biometric Authentication!"
        dialog.show()

        val existing: Button = dialog.findViewById(R.id.primaryButton)

        existing.setOnClickListener {
            val intent = Intent(this@BiometricAuthentication, DashboardActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
    }
}