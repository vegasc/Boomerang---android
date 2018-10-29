package com.alekseyrobul.boomerang.helpers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open abstract class BaseFragment: android.support.v4.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return updateUI()
    }

    /**
     * Gets called after onCreate method. The only purpose is update ui.
     */
    abstract fun updateUI(): View
}