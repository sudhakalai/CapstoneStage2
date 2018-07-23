package com.example.android.medicinereminder.Util;

import android.content.Context;

import com.example.android.medicinereminder.R;

/**
 * This is the Util class which has methods that take in an integer an return the corresponding shape, color or measure value
 */

public class FromintegerUtils {

    public static String getMedicineTypeString(int number, Context context){
        String [] typeArray = context.getResources().getStringArray(R.array.medicine_type);
        switch (number){
            case 1:
                return typeArray[0];
            case 2:
                return typeArray[1];
            case 3:
                return typeArray[2];
            case 4:
                return typeArray[3];
            case 5:
                return typeArray[4];
            case 6:
                return typeArray[5];
            case 7:
                return typeArray[6];
            case 8:
                return typeArray[7];
            case 9:
                return typeArray[8];
            default:
                return typeArray[9];
        }
    }

    public static String getMedicineColorString(int number, Context context){
        String[] colorArray = context.getResources().getStringArray(R.array.color_type);
        switch (number){
            case 1:
                return colorArray[0];
            case 2:
                return colorArray[1];
            case 3:
                return colorArray[2];
            case 4:
                return colorArray[3];
            case 5:
                return colorArray[4];
            case 6:
                return colorArray[5];
            case 7:
                return colorArray[6];
            case 8:
                return colorArray[7];
            case 9:
                return colorArray[8];
            case 10:
                return colorArray[9];
            case 11:
                return colorArray[10];
            case 12:
                return colorArray[11];
            case 13:
                return colorArray[12];
            case 14:
                return colorArray[13];
            case 15:
                return colorArray[14];
            case 16:
                return colorArray[15];
            case 17:
                return colorArray[16];
            default:
                return colorArray[17];
        }
    }

    public static String getMedicineShapeString(int number, Context context){
        String [] shapeArray = context.getResources().getStringArray(R.array.shape_type);
        switch (number) {
            case 1:
                return shapeArray[0];
            case 2:
                return shapeArray[1];
            case 3:
                return shapeArray[2];
            case 4:
                return shapeArray[3];
            case 5:
                return shapeArray[4];
            case 6:
                return shapeArray[5];
            default:
                return shapeArray[6];
        }
    }

    public static String getMedicineMeasureString(int number, Context context){
        String [] measureArray = context.getResources().getStringArray(R.array.measure_type);
        switch (number) {
            case 1:
                return measureArray[0];
            case 2:
                return measureArray[1];
            case 3:
                return measureArray[2];
            case 4:
                return measureArray[3];
            case 5:
                return measureArray[4];
            default:
                return measureArray[5];
        }
    }
}
