package com.luvikung.miuiautostart;

import android.app.Activity;
import android.util.Log;

import xyz.kumaraswamy.autostart.Autostart;

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

    public void startActivity() {
        try {
            Autostart autostart = new Autostart(activity);
            Autostart.State state = autostart.getAutoStartState();
            switch (state) {
                case ENABLED:
                    Log.i(LOG_TAG, "Xiaomi device Autostart Enabled");
                    break;
                case DISABLED:
                    Log.i(LOG_TAG, "Xiaomi device Autostart Disabled");
                    break;
                case NO_INFO:
                    Log.i(LOG_TAG, "Xiaomi device Autostart No Info");
                    break;
                case UNEXPECTED_RESULT:
                    Log.i(LOG_TAG, "Xiaomi device Autostart Unexpected Result");
                    break;
            }
        } catch (Exception e) {
            Log.i(LOG_TAG, "Not a Xiaomi device");
        }
    }
}