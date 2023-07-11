package com.example.labfinals;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditClinicsData extends Activity {
    TextView show_clinic_data , clinic_id ,title;
    EditText clinic_name ;
    Button save , back_home;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);

        show_clinic_data = (TextView) findViewById(R.id.show_clinic_data);
        clinic_id = (TextView) findViewById(R.id.clinic_id_text);
        title = findViewById(R.id.clinic_title);
        clinic_name = (EditText) findViewById(R.id.editTextClinicName);
        save = (Button) findViewById(R.id.add_clinic_button);
        back_home = (Button) findViewById(R.id.back_from_clinic_button);
        dbHandler = new DBHandler(EditClinicsData.this);

        save.setText("Save");
        title.setText("Edit Clinic");

        clinic_id.setText(getIntent().getStringExtra("id"));
        clinic_name.setText(getIntent().getStringExtra("name"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = clinic_id.getText().toString();
                String name = clinic_name.getText().toString();

                if (id.equals("") || name.equals("")){
                    Toast.makeText(EditClinicsData.this,"Please Fill All Blanks",Toast.LENGTH_LONG).show();
                }else{
                    dbHandler.updateClinicData(id,name);
                    Toast.makeText(EditClinicsData.this,"Clinic Edited Successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

        show_clinic_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditClinicsData.this , ShowClinics.class);
                startActivity(intent);
            }
        });

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditClinicsData.this , HomePage.class);
                startActivity(intent);
            }
        });
    }
}