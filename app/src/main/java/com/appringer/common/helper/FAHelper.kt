package com.appringer.common.helper

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.appringer.wedeliver.BuildConfig
import com.appringer.wedeliver.config.AppConfig
import com.appringer.wedeliver.config.MyApplication
import com.appringer.wedeliver.network.nosql.UserTrackingDO
import com.appringer.wedeliver.repo.DataProviderImp
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by kushaal singla on 09-Aug-18.
 */
object FAHelper {
    private val mFA: FirebaseAnalytics = FirebaseAnalytics.getInstance(MyApplication.context())

    fun log(itemId: String?, itemNameVar: String, ste: StackTraceElement, vararg strings: String?) {
        var itemName = ""
        try {
            val className = ste.className
            val methodName = ste.methodName
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId)
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemNameVar)
            if (!TextUtils.isEmpty(itemNameVar)) {
                val count = itemNameVar.length
                itemName = itemNameVar.substring(0, 99.coerceAtMost(count))
            }
            //Get Current Logged In User
            val userId = DataProviderImp.getUser()?.id ?: "NOT_LOGGED_IN"
            val userName = DataProviderImp.getUser()?.name ?: "NOT_LOGGED_IN"

            var offerId: String? = ""
            var storeId: String? = ""
            var storeName: String? = ""

            if (strings.isNotEmpty()) offerId = strings[0]
            if (strings.size >= 2) storeId = strings[1]
            if (strings.size >= 3) storeName = strings[2]

            mFA.setUserProperty("storeId", storeId)
            mFA.setUserProperty("storeName", storeName)
            mFA.setUserProperty("userId", userId)
            mFA.setUserProperty("offerId", offerId)
            mFA.setUserProperty("userName", userName)
            mFA.setUserId(userId)
            mFA.logEvent(itemId!!, bundle)
            //            switch (itemId) {
//                case REDEEM_CLICK:
//                    Answers.getInstance().logAddToCart(new AddToCartEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putCurrency(Currency.getInstance("INR"))
//                            .putItemPrice(BigDecimal.ONE)
//                            .putCustomAttribute(className, methodName));
//
//                    Answers.getInstance().logStartCheckout(new StartCheckoutEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putCurrency(Currency.getInstance("INR"))
//                            .putItemCount(1)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.TXN_SUCCESS:
//                    Answers.getInstance().logPurchase(new PurchaseEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putCurrency(Currency.getInstance("INR"))
//                            .putItemPrice(BigDecimal.valueOf(1))
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.OFFER_CLICK:
//                    Answers.getInstance().logContentView(new ContentViewEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putContentName("Offer")
//                            .putContentType(itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.SHARE:
//                    Answers.getInstance().logShare(new ShareEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.INVITE:
//                    Answers.getInstance().logInvite(new InviteEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.LOGIN:
//                    Answers.getInstance().logLogin(new LoginEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.REGISTER:
//                    Answers.getInstance().logSignUp(new SignUpEvent()
//                            .putCustomAttribute(itemId, itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.SEARCH_KEY:
//                    Answers.getInstance().logSearch(new SearchEvent()
//                            .putQuery(itemName)
//                            .putCustomAttribute(itemId, itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                case FAConstants.REWARD_SUCCESS:
//                    int score;
//                    try {
//                        score = Integer.parseInt(itemName.replaceAll(AppConfig.NON_DIGIT_REGEX, ""));
//                    } catch (Exception ex) {
//                        score = 0;
//                    }
//                    Answers.getInstance().logLevelEnd(new LevelEndEvent()
//                            .putLevelName(itemName)
//                            .putScore(score)
//                            .putCustomAttribute(itemId, itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//                default:
//                    Answers.getInstance().logCustom(new CustomEvent(itemId)
//                            .putCustomAttribute(itemId, itemName)
//                            .putCustomAttribute(className, methodName));
//                    break;
//            }
            dataLogOnRTD(itemId, itemName, userId, userName, ste, offerId, storeId, storeName)
        } catch (ex: Exception) {
            try { //                Crashlytics.logException(ex);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun dataLogOnRTD(itemId: String?, itemName: String, userId: String?, userName: String?, e: StackTraceElement, offerId: String?, storeId: String?, storeName: String?) {
        if (AppConfig.IGNORE_EVENTS.contains(itemId)) {
            Log.d(itemId, "Skipped")
            return
        }
        val userTrackingDO = UserTrackingDO()
        userTrackingDO.actionName = itemName
        userTrackingDO.actionName = itemId
        userTrackingDO.createdBy = userId
        userTrackingDO.tempUserName = userName
        userTrackingDO.tempStoreName = storeName
        //StackTrace
        userTrackingDO.fileName = e.fileName
        userTrackingDO.lineNumber = e.lineNumber
        userTrackingDO.methodName = e.methodName
        if (!BuildConfig.DEBUG) DataProviderImp.addUserTrackingEvent(userTrackingDO)
    }

    init {
        mFA.setAnalyticsCollectionEnabled(true)
    }
}