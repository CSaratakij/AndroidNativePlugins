package com.luvikung.nativeandroid;

public interface PluginCallback {
    public void OnAlertDialog(int type);

    public void OnPickDate(int year, int month, int day);

    public void OnPickTime(int hour, int minute);
}
