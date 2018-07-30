package com.example.android.medicinereminder.Model;

public class ReminderEdit {

    private String medName;
    private String mAppearance;
    private String mDates;
    private String mTimes;
    private int mStock;

    public ReminderEdit(String name, String app, String dates, String times, int stock){
        medName = name;
        mAppearance = app;
        mDates = dates;
        mTimes = times;
        mStock = stock;
    }

    public String getAppearance() {
        return mAppearance;
    }

    public String getMedName() {
        return medName;
    }

    public String getDates() {
        return mDates;
    }

    public String getTimes() {
        return mTimes;
    }

    public int getStock() {
        return mStock;
    }
}
