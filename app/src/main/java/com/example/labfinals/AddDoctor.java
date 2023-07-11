package com.example.labfinals;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddDoctor extends Activity {
        TextView doctorId , doctorDob , show_doctor_data;
        EditText doctorFirstname, doctorLastname , doctorPhone ;
        Button addDoctor , backHome;
        Spinner specialtySpinner;
        DBHandler dbHandler;
        RadioGroup radioGroupGender;
        RadioButton male , female , other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        doctorId = (TextView) findViewById(R.id.doctor_id_text);
        show_doctor_data = (TextView) findViewById(R.id.show_doctor_data);
        doctorFirstname = (EditText) findViewById(R.id.editTextDoctorFirstname);
        doctorLastname = (EditText) findViewById(R.id.editTextDoctorLastname);
        doctorPhone = (EditText) findViewById(R.id.editTextDoctorPhone);
        doctorDob = (TextView) findViewById(R.id.editTextDoctorDob);
        addDoctor = (Button) findViewById(R.id.add_doctor_button);
        backHome = (Button) findViewById(R.id.back_from_doctor_button);
        specialtySpinner = (Spinner) findViewById(R.id.specialty_spinner);
        radioGroupGender = (RadioGroup) findViewById(R.id.DoctorRadioGroupGender);
        male = (RadioButton) findViewById(R.id.DoctorRadiobuttonMale);
        female = (RadioButton) findViewById(R.id.DoctorRadiobuttonFemale);
        other = (RadioButton) findViewById(R.id.DoctorRadiobuttonOther);
        dbHandler = new DBHandler(AddDoctor.this);

        // add doctor id to textview //
        try{
            doctorId.setText(dbHandler.lastDoctorId()+1+"");
        } catch(Exception e){
            doctorId.setText("1");
        }

        //Fill In Specialties Spinner//
        List<String> specialty = Arrays.asList(getResources().getStringArray(R.array.specialty));

        ArrayAdapter<String> specialtyAdapter = new ArrayAdapter<>(AddDoctor.this,android.R.layout.simple_spinner_item,specialty);
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtySpinner.setAdapter(specialtyAdapter);

        show_doctor_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDoctor.this, ShowDoctors.class);
                startActivity(intent);
            }
        });

        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "DR."+doctorFirstname.getText().toString()+" "+doctorLastname.getText().toString();
                String phone = doctorPhone.getText().toString();
                String dob = doctorDob.getText().toString();

                String gender = "";
                if(male.isChecked() || female.isChecked() || other.isChecked()){
                    int selectedRadioButton = radioGroupGender.getCheckedRadioButtonId();
                    RadioButton checkedRadiobutton = findViewById(selectedRadioButton);
                    gender = checkedRadiobutton.getText().toString();
                }else{
                    Toast.makeText(AddDoctor.this,"Select a Gender!!",Toast.LENGTH_LONG).show();
                }

                String specialty = specialtySpinner.getSelectedItem().toString();

                if(doctorFirstname.getText().toString().equals("") || doctorPhone.getText().toString().equals("") ||
                   phone.equals("") || dob.equals("") || gender.equals("") || specialty.equals("")){
                    Toast.makeText(AddDoctor.this,"Please Fill In All blanks!!",Toast.LENGTH_LONG).show();
                }else{
                    dbHandler.addNewDoctor(name,phone,dob,gender,specialty);
                    Toast.makeText(AddDoctor.this,"Doctor Added Successfully!!",Toast.LENGTH_LONG).show();
                }

                //reset controls //
                doctorFirstname.setText("");
                doctorLastname.setText("");
                doctorPhone.setText("");
                doctorId.setText(dbHandler.lastDoctorId()+1+"");
                radioGroupGender.clearCheck();

            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDoctor.this, HomePage.class);
                startActivity(intent);
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
            doctorDob.setText(selectedDate);
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}