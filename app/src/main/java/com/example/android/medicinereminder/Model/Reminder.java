package com.example.android.medicinereminder.Model;

/**
 * This is the Object class
 */

public class Reminder {

    private String mMedicineName;
    private String mType;
    private int mDosage;
    private String mMeasure;
    private int mQuantity;
    private String mMedicineColor;
    private String mShape;
    private int[] mTimes;
    private int mFromDate;
    private int mToDate;
    private int mStock;
    private String mNotes;
    private int mState;

    public Reminder(String medicineName, String type, int dosage, String measure, int quantity, String medicineColor, String shape, int[] times, int fromDate, int toDate, int stock, String notes, int state){
        mMedicineName = medicineName;
        mType =type;
        mDosage = dosage;
        mMeasure = measure;
        mQuantity = quantity;
        mMedicineColor = medicineColor;
        mShape = shape;
        mTimes = times;
        mFromDate= fromDate;
        mToDate = toDate;
        mStock = stock;
        mNotes = notes;
        mState = state;
    }

    public String getMedicineName() { return mMedicineName; }
    public String getType() { return mType; }
    public int getDosage() { return mDosage; }
    public String getMeasure() { return mMeasure; }
    public int getQuantity() { return mQuantity; }
    public String getMedicineColor() { return mMedicineColor; }
    public String getShape() { return mShape; }
    public int[] getTimes() { return mTimes; }
    public int getFromDate() { return mFromDate; }
    public int getToDate() { return mToDate; }
    public int getStock() { return mStock; }
    public String getNotes() { return mNotes; }
    public int getState() { return mState; }
}
