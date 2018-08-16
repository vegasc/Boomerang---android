package com.kotlin.alekseyrobul.englishquiz.activities

import android.app.Fragment
import android.view.Menu
import com.kotlin.alekseyrobul.englishquiz.R
import com.kotlin.alekseyrobul.englishquiz.fragments.BoomerangFragment
import com.kotlin.alekseyrobul.englishquiz.fragments.boomerangView
import com.kotlin.alekseyrobul.englishquiz.helpers.BaseActivity
import org.jetbrains.anko.constraint.layout.constraintLayout

class MainActivity : BaseActivity() {

    private lateinit var activeFragment: android.support.v4.app.Fragment

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_effect_options, menu)
        return true
    }

    override fun setupUI() {
        activeFragment = BoomerangFragment()
        constraintLayout {
            id = R.id.act_main_constraint
            supportFragmentManager.beginTransaction().replace(R.id.act_main_constraint, activeFragment).commit()
        }
    }
}
