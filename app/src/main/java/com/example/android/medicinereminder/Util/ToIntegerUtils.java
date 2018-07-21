package com.example.android.medicinereminder.Util;

import android.content.Context;
import android.widget.Spinner;

import com.example.android.medicinereminder.R;

/**
 * This Util class has methods that return integer value of the selected item in the spinner.
 */

public  class ToIntegerUtils {

    public static int getMedicineType(Spinner medicineType, Context context){
        String medType = medicineType.getSelectedItem().toString().trim();
        int typeInt;
        String[] typeArray = context.getResources().getStringArray(R.array.medicine_type);
        if(medType.equalsIgnoreCase(typeArray[0])){
            typeInt = 1;
        }else if ((medType.equalsIgnoreCase(typeArray[1]))){
            typeInt = 2;
        }else if ((medType.equalsIgnoreCase(typeArray[2]))){
            typeInt = 3;
        }else if ((medType.equalsIgnoreCase(typeArray[3]))){
            typeInt = 4;
        }else if ((medType.equalsIgnoreCase(typeArray[4]))){
            typeInt = 5;
        }else if ((medType.equalsIgnoreCase(typeArray[5]))){
            typeInt = 6;
        }else if ((medType.equalsIgnoreCase(typeArray[6]))){
            typeInt = 7;
        }else if ((medType.equalsIgnoreCase(typeArray[7]))){
            typeInt = 8;
        }else if ((medType.equalsIgnoreCase(typeArray[8]))){
            typeInt = 9;
        }else {
            typeInt = 10;
        }

        return typeInt;
    }

    public static int getMedicineColor(Spinner medicineColor, Context context){

        String medColor = medicineColor.getSelectedItem().toString().trim();
        String [] colorArray = context.getResources().getStringArray(R.array.color_type);
        int colorInt;
        if(medColor.equalsIgnoreCase(colorArray[0])){
            colorInt =1;
        }else if(medColor.equalsIgnoreCase(colorArray[1])){
            colorInt = 2;
        }else if(medColor.equalsIgnoreCase(colorArray[2])){
            colorInt = 3;
        }else if(medColor.equalsIgnoreCase(colorArray[3])){
            colorInt = 4;
        }else if(medColor.equalsIgnoreCase(colorArray[4])){
            colorInt = 5;
        }else if(medColor.equalsIgnoreCase(colorArray[5])){
            colorInt = 6;
        }else if(medColor.equalsIgnoreCase(colorArray[6])){
            colorInt = 7;
        }else if(medColor.equalsIgnoreCase(colorArray[7])){
            colorInt = 8;
        }else if(medColor.equalsIgnoreCase(colorArray[8])){
            colorInt = 9;
        }else if(medColor.equalsIgnoreCase(colorArray[9])){
            colorInt = 10;
        }else if(medColor.equalsIgnoreCase(colorArray[10])){
            colorInt = 11;
        }else if(medColor.equalsIgnoreCase(colorArray[11])){
            colorInt = 12;
        }else if(medColor.equalsIgnoreCase(colorArray[12])){
            colorInt = 13;
        }else if(medColor.equalsIgnoreCase(colorArray[13])){
            colorInt = 14;
        }else if(medColor.equalsIgnoreCase(colorArray[14])){
            colorInt = 15;
        }else if(medColor.equalsIgnoreCase(colorArray[15])){
            colorInt = 16;
        }else if(medColor.equalsIgnoreCase(colorArray[16])){
            colorInt = 17;
        }else {
            colorInt = 18;
        }

        return colorInt;
    }

    public static int getMedicineShape(Spinner medicineShape, Context context){
        String medShape = medicineShape.getSelectedItem().toString().trim();
        String[] shapeArray = context.getResources().getStringArray(R.array.shape_type);
        int shapeInt;
        if(medShape.equalsIgnoreCase(shapeArray[0])){
            shapeInt = 1;
        }else if(medShape.equalsIgnoreCase(shapeArray[1])){
            shapeInt = 2;
        }else if(medShape.equalsIgnoreCase(shapeArray[2])){
            shapeInt = 3;
        }else if(medShape.equalsIgnoreCase(shapeArray[3])){
            shapeInt = 4;
        }else if(medShape.equalsIgnoreCase(shapeArray[4])){
            shapeInt = 5;
        }else if(medShape.equalsIgnoreCase(shapeArray[5])){
            shapeInt = 6;
        }else {
            shapeInt = 7;
        }
        return shapeInt;
    }

    public static int getMedicineMeasure(Spinner measure, Context context){
        String medMeasure = measure.getSelectedItem().toString().trim();
        String [] measureArray = context.getResources().getStringArray(R.array.measure_type);
        int measureInt;
        if(medMeasure.equalsIgnoreCase(measureArray[0])){
            measureInt = 1;
        }else if(medMeasure.equalsIgnoreCase(measureArray[1])){
            measureInt = 2;
        }else if(medMeasure.equalsIgnoreCase(measureArray[2])){
            measureInt = 3;
        }else if(medMeasure.equalsIgnoreCase(measureArray[3])){
            measureInt = 4;
        }else if(medMeasure.equalsIgnoreCase(measureArray[4])){
            measureInt = 5;
        }else {
            measureInt = 6;
        }
        return measureInt;
    }

}
