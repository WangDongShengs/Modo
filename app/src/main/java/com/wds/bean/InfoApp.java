package com.wds.bean;

import android.graphics.drawable.Drawable;

public class InfoApp {
    private String AppName;//标 签
    private Drawable AppIcon;//图标
    private String packageName;//包名

    public InfoApp(String appName, Drawable appIcon, String packageName) {
        AppName = appName;
        AppIcon = appIcon;
        this.packageName = packageName;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public Drawable getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        AppIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
