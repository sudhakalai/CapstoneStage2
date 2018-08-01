package com.example.android.medicinereminder.Model;

/**
 * This is the Reminder Object class
 */

public class Reminder {

    private String mMedicineName;
    private String mType;
    private int mDosage;
    private String mMeasure;
    private String mMedicineColor;
    private String mShape;
    private long mReminderDate;
    private String mNotes;
    private long mReminderTime;
    private int mState;
    private int mReminderId;
    private int mAlarmId;

    public Reminder(String medicineName, String type, int dosage, String measure, String medicineColor, String shape,long reminderDate, String notes, long reminderTime, int state, int reminderId, int alarmId){
        mMedicineName = medicineName;
        mType =type;
        mDosage = dosage;
        mMeasure = measure;
        mMedicineColor = medicineColor;
        mShape = shape;
        mReminderDate = reminderDate;
        mNotes = notes;
        mReminderTime = reminderTime;
        mState = state;
        mReminderId = reminderId;
        mAlarmId = alarmId;
    }

    public Reminder(Reminder reminder){
        mMedicineName = reminder.getMedicineName();
        mType =reminder.getType();
        mDosage = reminder.getDosage();
        mMeasure = reminder.getMeasure();
        mMedicineColor = reminder.getMedicineColor();
        mShape = reminder.getShape();
        mReminderDate = reminder.getReminderDate();
        mNotes = reminder.getNotes();
        mReminderTime = reminder.getReminderTime();
        mState = reminder.getState();
        mReminderId = reminder.getReminderId();
        mAlarmId = reminder.getAlarmId();
    }

    public Reminder(){}
    public String getMedicineName() { return mMedicineName; }
    public String getType() { return mType; }
    public int getDosage() { return mDosage; }
    public String getMeasure() { return mMeasure; }
    public String getMedicineColor() { return mMedicineColor; }
    public String getShape() { return mShape; }
    public long getReminderDate() { return mReminderDate; }
    public String getNotes() { return mNotes; }
    public long getReminderTime(){ return mReminderTime; }
    public int getState(){ return mState; }
    public int getReminderId(){ return mReminderId;}
    public int getAlarmId() { return mAlarmId; }

    public void setMedicineName(String medName){ mMedicineName = medName;}
    public void setType(String type) { mType=type; }
    public void setDosage(int dosage) { mDosage= dosage; }
    public void setMeasure(String measure) {  mMeasure = measure; }
    public void setMedicineColor(String color) { mMedicineColor =  color; }
    public void setShape(String shape) { mShape= shape; }
    public void setReminderDate(long date) {  mReminderDate = date; }
    public void setNotes(String notes) { mNotes = notes; }
    public void setReminderTime(long reminderTime){ mReminderTime = reminderTime;}
    public void setState(int state){ mState= state; }
    public void setReminderId(int id){ mReminderId = id;}
    public void setAlarmId(int alarmId) { mAlarmId = alarmId; }
}
