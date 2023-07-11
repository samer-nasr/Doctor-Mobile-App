package com.example.labfinals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class ShowAppointments extends Activity {
    TableLayout appointmentTable;
    DBHandler dbHandler;
    Button edit;
    String id , patientName , patientId , appointmentDate , appointmentTime , doctorName , clinicName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointments);

        id = "";
        patientName = "";
        patientId = "";
        appointmentDate = "";
        appointmentTime = "";
        doctorName = "";
        clinicName = "";

        appointmentTable = (TableLayout) findViewById(R.id.appointment_table);
        dbHandler = new DBHandler(ShowAppointments.this);
        edit = (Button) findViewById(R.id.edit_a_btn);
        edit.setBackground(getDrawable(R.color.gray));

        List<List> appointmentsData = dbHandler.getAllAppointmentsData();

        List<String> appointmentIds = appointmentsData.get(0);
        List<String> patientsIds = appointmentsData.get(1);
        List<String> patientNames = appointmentsData.get(2);
        List<String> dates = appointmentsData.get(3);
        List<String> times = appointmentsData.get(4);
        List<String> doctors = appointmentsData.get(5);
        List<String> clinics = appointmentsData.get(6);

        for (int i = 0 ; i < appointmentIds.size() ; i++ ){
            TableRow tableRow = new TableRow(this);

            TextView appointment_id = new TextView(ShowAppointments.this);
            appointment_id.setText(appointmentIds.get(i));
            appointment_id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            appointment_id.setHeight(100);
            tableRow.addView(appointment_id);

            TextView patient_id = new TextView(ShowAppointments.this);
            patient_id.setText(patientsIds.get(i));
            patient_id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(patient_id);

            TextView patient_name = new TextView(ShowAppointments.this);
            patient_name.setText(patientNames.get(i));
            patient_name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(patient_name);

            TextView date = new TextView(ShowAppointments.this);
            date.setText(dates.get(i));
            date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(date);

            TextView time = new TextView(ShowAppointments.this);
            time.setText(times.get(i));
            time.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(time);

            TextView doctor = new TextView(ShowAppointments.this);
            doctor.setText(doctors.get(i));
            doctor.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(doctor);

            TextView clinic = new TextView(ShowAppointments.this);
            clinic.setText(clinics.get(i));
            clinic.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(clinic);

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j <= appointmentIds.size(); j++) {
                        appointmentTable.getChildAt(j).setBackground(getDrawable(R.color.white));
                    }

                    edit.setBackground(getDrawable(R.color.green));
                    tableRow.setBackground(getDrawable(R.color.gray));

                    id = appointment_id.getText().toString();
                    patientId = patient_id.getText().toString();
                    patientName = patient_name.getText().toString();
                    appointmentDate = date.getText().toString();
                    appointmentTime = time.getText().toString();
                    doctorName = doctor.getText().toString();
                    clinicName = clinic.getText().toString();

                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ShowAppointments.this, EditAppointmentsData.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id);
                    bundle.putString("patientId",patientId);
                    bundle.putString("patientName",patientName);
                    bundle.putString("date",appointmentDate);
                    bundle.putString("time",appointmentTime);
                    bundle.putString("doctor",doctorName);
                    bundle.putString("clinic",clinicName);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            appointmentTable.addView(tableRow);
        }
    }
}