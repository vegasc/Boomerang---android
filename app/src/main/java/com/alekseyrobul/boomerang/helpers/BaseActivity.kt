package com.alekseyrobul.boomerang.helpers

import android.os.Bundle
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