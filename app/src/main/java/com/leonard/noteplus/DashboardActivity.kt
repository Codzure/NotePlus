package com.leonard.noteplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setOnclickListener()

    }

    private fun setOnclickListener() {

        cardViewTodo.setOnClickListener{
            val intent = Intent(this@DashboardActivity, MainActivity::class.java)
            startActivity(intent)
        }

        scanCardView.setOnClickListener{
            val intent = Intent(this@DashboardActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}