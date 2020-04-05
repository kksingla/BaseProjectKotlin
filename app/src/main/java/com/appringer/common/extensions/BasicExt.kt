package com.appringer.common.extensions

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment

fun AppCompatActivity.makeToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.switchFragment(containerId: Int, fragment: Fragment) {
    val manager = this.supportFragmentManager
    val oldFragment = manager.findFragmentById(containerId)
    if (oldFragment == null || oldFragment.javaClass != fragment.javaClass) {
        val ft = manager.beginTransaction()
        ft.replace(containerId, fragment)
        ft.commit()
    }
}

fun AppCompatActivity.setBackButton(isYes: Boolean) {
    supportActionBar?.setDisplayHomeAsUpEnabled(isYes)
}

fun AppCompatActivity.setToolbarText(title: String) {
    supportActionBar?.title = title
}

fun View.changeColor(color: Int) {
    if (background != null) {
        val wrappedDrawable = DrawableCompat.wrap(background!!)
        DrawableCompat.setTint(wrappedDrawable, color)
        background = wrappedDrawable
    }
}
