package com.appringer.common.base

import android.content.Intent
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker

abstract class BaseLocationActivity : BaseActivity() {
    private val PLACE_PICKER_REQUEST = 1
    protected abstract fun onLocationChanged(address: Place?)
    var builder = PlacePicker.IntentBuilder()

    protected fun showPlacePicker() {
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                    val place = PlacePicker.getPlace(this, data)
                    onLocationChanged(place)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

}