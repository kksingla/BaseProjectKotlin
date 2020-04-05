package com.appringer.common.service;

import com.appringer.common.utils.AndroidUtils;
import com.appringer.wedeliver.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kushaal singla on 28-Dec-18.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NotNull String token) {
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
//        DataProviderImp.INSTANCE.setFCMToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = getString(R.string.app_name);
        String msg = "";
        String url = "";
        String ImgUrl = "";

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            try {
                if (jsonObject.has("title"))
                    title = jsonObject.getString("title");
                if (jsonObject.has("msg"))
                    msg = jsonObject.getString("msg");
                if (jsonObject.has("imgUrl"))
                    ImgUrl = jsonObject.getString("imgUrl");
                if (jsonObject.has("landingUrl"))
                    url = jsonObject.getString("landingUrl");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AndroidUtils.INSTANCE.postNotification(this, title, msg, url, ImgUrl);
    }


}
