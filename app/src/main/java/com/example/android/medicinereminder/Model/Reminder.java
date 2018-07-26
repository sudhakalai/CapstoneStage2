package com.example.android.medicinereminder.Model;

import android.content.Context;

/**
 * This is the Object class
 */

public class Reminder {

    private String mMedicineName;
    private String mType;
    private int mDosage;
    private String mMeasure;
    private String mMedicineColor;
    private String mShape;
    private long mReminderDate;
    private int mStock;
    private String mNotes;
    private long mReminderTime;
    Context mContext;

    public Reminder(String medicineName, String type, int dosage, String measure, String medicineColor, String shape,long reminderDate, int stock, String notes, long reminderTime){
        mMedicineName = medicineName;
        mType =type;
        mDosage = dosage;
        mMeasure = measure;
        mMedicineColor = medicineColor;
        mShape = shape;
        mReminderDate = reminderDate;
        mStock = stock;
        mNotes = notes;
        mReminderTime = reminderTime;
    }

    public String getMedicineName() { return mMedicineName; }
    public String getType() { return mType; }
    public int getDosage() { return mDosage; }
    public String getMeasure() { return mMeasure; }
    public String getMedicineColor() { return mMedicineColor; }
    public String getShape() { return mShape; }
    public long getReminderDate() { return mReminderDate; }
    public int getStock() { return mStock; }
    public String getNotes() { return mNotes; }
    public long getReminderTime(){ return mReminderTime; }
    public void setReminderTime(long reminderTime){ mReminderTime = reminderTime;}



}
