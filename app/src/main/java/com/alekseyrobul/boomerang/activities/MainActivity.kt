package com.alekseyrobul.boomerang.activities

import android.view.Menu
import android.view.MenuItem
import com.alekseyrobul.boomerang.R
import com.alekseyrobul.boomerang.fragments.Images.ImagesFragment
import com.alekseyrobul.boomerang.fragments.boomerang.BoomerangFragment
import com.alekseyrobul.boomerang.fragments.gif.GifFragment
import com.alekseyrobul.boomerang.helpers.BaseActivity
import org.jetbrains.anko.constraint.layout.constraintLayout

class MainActivity : BaseActivity() {

    private lateinit var activeFragment: android.support.v4.app.Fragment

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_effect_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) { return false }
        when(item.itemId) {
            R.id.menu_item_boomerang -> { showBoomerang() }
            R.id.menu_item_gif       -> { showGif() }
            R.id.menu_item_images    -> { showImages() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBoomerang() {
        setTitle(R.string.menu_item_boomerang)
        activeFragment = BoomerangFragment()
        supportFragmentManager.beginTransaction().replace(R.id.act_main_constraint, activeFragment).commit()
    }

    private fun showGif() {
        setTitle(R.string.menu_item_gif)
        activeFragment = GifFragment()
        supportFragmentManager.beginTransaction().replace(R.id.act_main_constraint, activeFragment).commit()
    }

    private fun showImages() {
        setTitle(R.string.menu_images)
        activeFragment = ImagesFragment()
        supportFragmentManager.beginTransaction().replace(R.id.act_main_constraint, activeFragment).commit()
    }

    override fun setupUI() {
        constraintLayout { id = R.id.act_main_constraint }
        showBoomerang()
    }
}
