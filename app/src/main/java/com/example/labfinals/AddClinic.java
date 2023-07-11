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

public class AddClinic extends Activity {
    TextView show_clinic_data , clinic_id;
    EditText clinic_name;
    Button add_clinic , back_home;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);

        show_clinic_data = (TextView) findViewById(R.id.show_clinic_data);
        clinic_id = (TextView) findViewById(R.id.clinic_id_text);
        clinic_name = (EditText) findViewById(R.id.editTextClinicName);
        add_clinic = (Button) findViewById(R.id.add_clinic_button);
        back_home = (Button) findViewById(R.id.back_from_clinic_button);
        dbHandler = new DBHandler(AddClinic.this);


        // add Clinic id to TextView //
        try{
            clinic_id.setText(dbHandler.lastClinicId()+1+"");
        } catch(Exception e){
            clinic_id.setText("1");
        }

        show_clinic_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClinic.this , ShowClinics.class);
                startActivity(intent);
            }
        });


        add_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = clinic_name.getText().toString();
                if(name.equals("")){
                    Toast.makeText(AddClinic.this,"Please Fill In All Blanks!!",Toast.LENGTH_LONG).show();
                }else{
                    dbHandler.addNewClinic(name);
                    Toast.makeText(AddClinic.this,"Clinic Added successfully!!",Toast.LENGTH_LONG).show();

                    clinic_id.setText(dbHandler.lastClinicId()+1+"");
                    clinic_name.setText("");
                }
            }
        });


        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClinic.this , HomePage.class);
                startActivity(intent);
            }
        });
    }
}