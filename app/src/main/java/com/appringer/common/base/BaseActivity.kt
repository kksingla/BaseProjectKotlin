package com.appringer.common.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.appringer.common.helper.LoggerHelper
import com.appringer.common.utils.UIUtils
import com.appringer.wedeliver.activities.IntroActivity
import com.appringer.wedeliver.activities.LoginActivity
import com.appringer.wedeliver.activities.MainActivity
import com.appringer.wedeliver.enums.AnalyticsEnum

abstract class BaseActivity : AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null

    abstract fun initVM()

    open fun showProgressBar() {
        try {
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage("Please wait...")
            progressDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun hideProgressBar() {
        try {
            progressDialog?.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun makeToast(msg: String) {
        UIUtils.makeToast(this, msg)
    }

    fun showSnackBar(view: View, msg: String) {
        UIUtils.makeToast(this, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoggerHelper.log(AnalyticsEnum.OPEN_PAGE.toString(), javaClass.simpleName)
        initVM()
        supportActionBar?.elevation=0f
    }

    protected fun openMainActivity(data: String?) {
        val i = Intent(this, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }

    protected fun openOnBoardingActivity() {
        startActivity(Intent(this, IntroActivity::class.java))
    }

    protected fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}