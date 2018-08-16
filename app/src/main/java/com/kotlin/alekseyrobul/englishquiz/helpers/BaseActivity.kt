package com.kotlin.alekseyrobul.englishquiz.helpers

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

open abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    /**
     * Gets called after onCreate method. The only purpose is update ui.
     */
    abstract fun setupUI()
}