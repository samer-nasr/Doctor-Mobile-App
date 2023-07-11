package com.example.labfinals;


import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class HomePage extends Activity {
    ImageView addAppointment_btn , appointmentList_btn , doctor_btn , patient_btn , clinic_btn;
    DBHandler dbHandler;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        addAppointment_btn = (ImageView) findViewById(R.id.add_appointment_from_home);
        appointmentList_btn = (ImageView) findViewById(R.id.appointment_list);
        doctor_btn = (ImageView) findViewById(R.id.doctor);
        patient_btn = (ImageView) findViewById(R.id.patient);
        clinic_btn = (ImageView) findViewById(R.id.clinic);
        logout = (Button) findViewById(R.id.logout_btn);

        dbHandler = new DBHandler(HomePage.this);

        patient_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, AddPatient.class);
                startActivity(intent);
            }
        });

        addAppointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, AddAppointment.class);
                startActivity(intent);
            }
        });

        doctor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, AddDoctor.class);
                startActivity(intent);
            }
        });

        clinic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this , AddClinic.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this , Login.class);
                startActivity(intent);
            }
        });

        appointmentList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this , ShowAppointments.class);
                startActivity(intent);
            }
        });
    }
}
