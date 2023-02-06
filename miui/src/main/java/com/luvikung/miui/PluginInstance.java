package com.luvikung.miui;

import android.app.Activity;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PluginInstance {
    private static final String LOG_TAG = "UnityPlugins";
    private static final int VERSION = 1;

    public int getPluginVersion() {
        return VERSION;
    }

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static boolean isMIUI() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            java.lang.Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
}