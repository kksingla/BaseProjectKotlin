package com.appringer.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {
    private static final String TAG = "PermissionHelper";
    private String[] permissions;
    private int REQUEST_CODE;
    private PermissionCallback mPermissionCallback;
    private Fragment fragment;
    private AppCompatActivity activity;
    private boolean showRational;

    private PermissionUtils(Builder builder) {
        this.permissions = builder.permissions;
        this.REQUEST_CODE = builder.REQUEST_CODE;
        this.mPermissionCallback = builder.callback;
        if (builder.activity == null && builder.fragment == null) {
            throw new RuntimeException("Set Activity or set fragment for Instance");
        } else {
            this.fragment = builder.fragment;
            this.activity = builder.activity;
            this.checkIfPermissionPresentInAndroidManifest();
        }
    }

    private void checkIfPermissionPresentInAndroidManifest() {
        String[] var1 = this.permissions;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String permission = var1[var3];
            if (!this.hasPermission(permission)) {
                throw new RuntimeException("Permission (" + permission + ") Not Declared in manifest");
            }
        }

    }

    public void request() {
        if (!this.checkSelfPermission(this.permissions)) {
            this.showRational = this.shouldShowRational(this.permissions);
            if (this.activity != null) {
                ActivityCompat.requestPermissions(this.activity, this.filterNotGrantedPermission(this.permissions), this.REQUEST_CODE);
            } else {
                this.fragment.requestPermissions(this.filterNotGrantedPermission(this.permissions), this.REQUEST_CODE);
            }
        } else {
            if (this.mPermissionCallback != null) {
                this.mPermissionCallback.onPermissionGranted(this.REQUEST_CODE);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == this.REQUEST_CODE) {
            boolean denied = false;
            int i = 0;
            ArrayList<String> grantedPermissions = new ArrayList();
            int[] var7 = grantResults;
            int var8 = grantResults.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                int grantResult = var7[var9];
                if (grantResult != 0) {
                    denied = true;
                } else {
                    grantedPermissions.add(permissions[i]);
                }

                ++i;
            }

            if (denied) {
                boolean currentShowRational = this.shouldShowRational(permissions);
                if (!this.showRational && !currentShowRational) {
                    if (this.mPermissionCallback != null) {
                        this.mPermissionCallback.onPermissionDeniedBySystem(this.REQUEST_CODE);
                    }
                } else {
                    if (!grantedPermissions.isEmpty() && this.mPermissionCallback != null) {
                        this.mPermissionCallback.onIndividualPermissionGranted(this.REQUEST_CODE, (String[])grantedPermissions.toArray(new String[grantedPermissions.size()]));
                    }

                    if (this.mPermissionCallback != null) {
                        this.mPermissionCallback.onPermissionDenied(this.REQUEST_CODE);
                    }
                }
            } else {
                if (this.mPermissionCallback != null) {
                    this.mPermissionCallback.onPermissionGranted(this.REQUEST_CODE);
                }
            }
        }

    }

    private Context getContext() {
        return this.activity != null ? this.activity : this.fragment.getContext();
    }

    private String[] filterNotGrantedPermission(String[] permissions) {
        List<String> notGrantedPermission = new ArrayList();
        String[] var3 = permissions;
        int var4 = permissions.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String permission = var3[var5];
            if (ContextCompat.checkSelfPermission(this.getContext(), permission) != 0) {
                notGrantedPermission.add(permission);
            }
        }

        return (String[])notGrantedPermission.toArray(new String[notGrantedPermission.size()]);
    }

    public boolean checkSelfPermission(String[] permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String permission = var2[var4];
            if (ContextCompat.checkSelfPermission(this.getContext(), permission) != 0) {
                return false;
            }
        }

        return true;
    }

    private boolean shouldShowRational(String[] permissions) {
        boolean currentShowRational = false;
        String[] var3 = permissions;
        int var4 = permissions.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String permission = var3[var5];
            if (this.activity != null) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, permission)) {
                    currentShowRational = true;
                    break;
                }
            } else if (this.fragment.shouldShowRequestPermissionRationale(permission)) {
                currentShowRational = true;
                break;
            }
        }

        return currentShowRational;
    }

    public boolean hasPermission(String permission) {
        try {
            Context context = this.activity != null ? this.activity : this.fragment.getActivity();
            PackageInfo info = context.getPackageManager().getPackageInfo(((Context)context).getPackageName(), 4096);
            if (info.requestedPermissions != null) {
                String[] var4 = info.requestedPermissions;
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String p = var4[var6];
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return false;
    }

    public static class Builder {
        private String[] permissions;
        private int REQUEST_CODE;
        private PermissionCallback callback;
        private Fragment fragment;
        private AppCompatActivity activity;

        public Builder(PermissionCallback callback, String[] permissions, int REQUEST_CODE) {
            this.callback = callback;
            this.permissions = permissions;
            this.REQUEST_CODE = REQUEST_CODE;
        }

        public Builder setActivity(AppCompatActivity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setFragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public PermissionUtils build() {
            return new PermissionUtils(this);
        }
    }

    public interface PermissionCallback {
        void onPermissionGranted(int var1);

        void onIndividualPermissionGranted(int var1, String[] var2);

        void onPermissionDenied(int var1);

        void onPermissionDeniedBySystem(int var1);
    }
}
