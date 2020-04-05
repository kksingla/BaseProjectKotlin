package com.appringer.common.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object UIUtils {
    fun makeToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun makeToast(context: Context?, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View?, message: String?, actionName: String?, action: View.OnClickListener?) {
//        Snackbar.make(view, message,)
    }

}
