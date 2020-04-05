package com.appringer.common.base

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.appringer.common.utils.UIUtils

/*
 * Created by Kushaal Singla on 23-Nov-17.
 */
abstract class BaseDialogFragment : DialogFragment() {
    protected var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = context
    }

    override fun onResume() { // Set the width of the dialog proportional to 90% of the screen width
        try {
            val window = dialog!!.window
            val size = Point()
            val display = window!!.windowManager.defaultDisplay
            display.getSize(size)
            window.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        super.onResume()
    }

    protected fun makeToast(message: String?) {
        UIUtils.makeToast(context, message)
    }

    protected fun makeToast(message: Int) {
        UIUtils.makeToast(context, message)
    }


    protected fun showSnackBar(view: View?, message: String?) {
//        UIUtils.showSnackBar(view, message)
    }

    protected fun showSnackBar(view: View?, message: String?, actionName: String?, action: View.OnClickListener?) {
        UIUtils.showSnackBar(view, message, actionName, action)
    }

//    protected fun hideSnackBar() {
//        UIUtils.hideSnackBar()
//    }
//
//    fun hideSoftKeyBoard(view: View?) {
//        UIUtils.hideSoftKeyBoard(context, view)
//    }
}