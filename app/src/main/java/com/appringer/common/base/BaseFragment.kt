package com.appringer.common.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.appringer.common.utils.UIUtils
import com.appringer.wedeliver.activities.LoginActivity
import com.appringer.wedeliver.activities.WebActivity
import com.appringer.wedeliver.constants.IntentConstant

/*
 * Created by Kushaal Singla on 23-Nov-17.
 */
abstract class BaseFragment : Fragment() {
    abstract fun initVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
    }

    protected fun switchFragment(containerId: Int, fragment: Fragment) {
        val manager = this.childFragmentManager
        val oldFragment = manager.findFragmentById(containerId)
        if (oldFragment == null || oldFragment.javaClass != fragment.javaClass) {
            val ft = manager.beginTransaction()
            ft.replace(containerId, fragment)
            ft.commit()
        }
    }

    protected fun openLoginActivity() {
        val i = Intent(context, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }

    protected fun openWebView(url: String?) {
        val intent = Intent(context, WebActivity::class.java)
        intent.putExtra(IntentConstant.URL, url)
        startActivity(intent)
    }

    fun makeToast(msg: String) {
        UIUtils.makeToast(context, msg)
    }
}