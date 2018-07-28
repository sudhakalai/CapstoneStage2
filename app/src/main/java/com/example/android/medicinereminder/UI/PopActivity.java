package com.example.android.medicinereminder.UI;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.android.medicinereminder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.7), (int)(height * 0.4));

        Button saveButton = findViewById(R.id.save);
        Button cancelButton = findViewById(R.id.cancel);
        RadioButton done = findViewById(R.id.medicine_taken);
        RadioButton notDone = findViewById(R.id.medicine_no);
        RadioButton none = findViewById(R.id.none);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        if(getIntent() != null){
            if(getIntent().hasExtra("reminderKey")){
                String key = getIntent().getStringExtra("reminderKey");

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(done.isChecked()){
                            databaseReference.child("reminders").child(key).child("state").setValue(2);
                        }else if(notDone.isChecked()){
                            databaseReference.child("reminders").child(key).child("state").setValue(3);
                        }else if (none.isChecked()){
                            databaseReference.child("reminders").child(key).child("state").setValue(1);
                        }
                        finish();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }


    }
}
