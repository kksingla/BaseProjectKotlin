package com.appringer.common.helper;

import android.location.Location;

public interface AppLocationListener {
        void onLocationChanged(Location location, double speed);
    }