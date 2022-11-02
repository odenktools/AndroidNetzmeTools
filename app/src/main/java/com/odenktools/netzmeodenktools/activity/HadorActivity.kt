package com.odenktools.netzmeodenktools.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.odenktools.netzmeodenktools.databinding.ActivityHadorBinding

class HadorActivity : AppCompatActivity() {
    lateinit var binding: ActivityHadorBinding
    lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHadorBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        binding.btnKtTestAction.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@HadorActivity, "sssss", Toast.LENGTH_SHORT).show()
        })
    }
}
