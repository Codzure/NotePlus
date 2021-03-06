package com.leonard.noteplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leonard.noteplus.utils.CustomSnackBar
import id.voela.actrans.AcTrans
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
            AcTrans.Builder(this).performFade()
            startActivity(intent)
        }

        scanCardView.setOnClickListener{
            CustomSnackBar(this, "Coming soon")
           /* val intent = Intent(this@DashboardActivity, MainActivity::class.java)
            AcTrans.Builder(this).performFade()
            startActivity(intent)*/
        }

    }
}