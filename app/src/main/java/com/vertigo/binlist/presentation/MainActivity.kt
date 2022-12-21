package com.vertigo.binlist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vertigo.binlist.R
import com.vertigo.binlist.presentation.fragments.FragmentBinInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, FragmentBinInfo())
                .commit()
        }
    }
}