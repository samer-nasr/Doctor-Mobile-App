package com.example.labfinals;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddAppointment extends Activity {
    Button backHome , add_appointment;
    Spinner patientId_spinner, time_spinner , doctor_spinner , clinic_spinner , patientName;
    TextView date , appointment_id ;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        appointment_id = (TextView) findViewById(R.id.app_id_value);
        patientId_spinner = (Spinner) findViewById(R.id.patient_id_spinner);
        date = (TextView) findViewById(R.id.editTextDate);
        time_spinner = (Spinner) findViewById(R.id.time_spinner);
        doctor_spinner = (Spinner) findViewById(R.id.doctor_spinner);
        clinic_spinner = (Spinner) findViewById(R.id.editTextClinic) ;
        patientName = (Spinner) findViewById(R.id.PatientNameTextView);
        backHome = (Button) findViewById(R.id.back_from_appointment_button);
        add_appointment = (Button) findViewById(R.id.add_appointment_button);
        dbHandler = new DBHandler(AddAppointment.this);

        //Add appointment id value //
        try{
            appointment_id.setText(dbHandler.lastAppointmentId()+1+"");
        } catch(Exception e){
            appointment_id.setText("1");
        }

        //Create List for appointments time //
        List<String> times = Arrays.asList(getResources().getStringArray(R.array.availableTime));

        //add list to Time spinner //
        ArrayAdapter<String> timesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(timesAdapter);

        //Create list for patients data //
        List<List> patientsData = dbHandler.getAllPatientsData();

        //Patients id//
        List<String> patientsIds = patientsData.get(0);

        //Patients names//
        List<String> patientsNames = patientsData.get(1);

        // Add list to patient names spinner //
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,patientsNames);
        namesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientName.setAdapter(namesAdapter);

        // Add list to patient ids spinner //
        ArrayAdapter<String> idsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, patientsIds);
        idsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientId_spinner.setAdapter(idsAdapter);

        //add patient name using patient id //
        patientId_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String patientId = patientId_spinner.getSelectedItem().toString();
                int indexForId = patientsIds.indexOf(patientId);
                patientName.setSelection(indexForId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //add patient id using patient name //
        patientName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int indexForName = patientsNames.indexOf(patientName.getSelectedItem().toString());
                patientId_spinner.setSelection(indexForName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //create list of clinics//
        List<String> clinics = dbHandler.getAllClinicData().get(1);
        //add list to clinic spinner//
        ArrayAdapter<String> clinic_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,clinics);
        clinic_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clinic_spinner.setAdapter(clinic_adapter);

        //add doctors spinner //
        // get names form database //
        List<List> doctorsData = dbHandler.getAllDoctorsData();
        List<String> doctorsName = doctorsData.get(1);

        ArrayAdapter<String> doctorNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,doctorsName);
        doctorNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctor_spinner.setAdapter(doctorNameAdapter);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAppointment.this,HomePage.class);
                startActivity(intent);
            }
        });

        add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String patient_id = patientId_spinner.getSelectedItem().toString();
               String patient_name = patientName.getSelectedItem().toString();
               String appointment_date = date.getText().toString();
               String appointment_time = time_spinner.getSelectedItem().toString();
               String doctor = doctor_spinner.getSelectedItem().toString();
               String clinic = clinic_spinner.getSelectedItem().toString();

               if(patient_id.equals("") || patient_name.equals("") || appointment_date.equals("") || appointment_time.equals("") || doctor.equals("") || clinic.equals("")){
                   Toast.makeText(AddAppointment.this,"Please Fill In All Blanks!!",Toast.LENGTH_LONG).show();
               }else{
                   dbHandler.addNewAppointment(patient_id,patient_name,appointment_date,appointment_time,doctor,clinic);
                   Toast.makeText(AddAppointment.this,"Appointment Added Successfully!!",Toast.LENGTH_LONG).show();
                   appointment_id.setText(dbHandler.lastAppointmentId()+1+"");
               }
            }
        });
    }

    public void showDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener) (datePicker, selectedYear, selectedMonth, selectedDay) -> {
            // Update the EditText with the selected date
            String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            date.setText(selectedDate);
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}