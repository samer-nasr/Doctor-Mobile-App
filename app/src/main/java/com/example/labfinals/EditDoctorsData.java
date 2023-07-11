package com.example.labfinals;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EditDoctorsData extends Activity {
    TextView doctorId , doctorDob , show_doctor_data ,title;
    EditText doctorFirstname, doctorLastname , doctorPhone ;
    Button save , backHome;
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
        title = (TextView) findViewById(R.id.add_doctor_title) ;
        save = (Button) findViewById(R.id.add_doctor_button);
        backHome = (Button) findViewById(R.id.back_from_doctor_button);
        specialtySpinner = (Spinner) findViewById(R.id.specialty_spinner);
        radioGroupGender = (RadioGroup) findViewById(R.id.DoctorRadioGroupGender);
        male = (RadioButton) findViewById(R.id.DoctorRadiobuttonMale);
        female = (RadioButton) findViewById(R.id.DoctorRadiobuttonFemale);
        other = (RadioButton) findViewById(R.id.DoctorRadiobuttonOther);
        dbHandler = new DBHandler(EditDoctorsData.this);

        title.setText("Edit Doctor");
        save.setText("Save");

        //Add data to the form //
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        doctorId.setText(id);

        String firstName = intent.getStringExtra("firstName");
        doctorFirstname.setText(firstName);

        String lastName = intent.getStringExtra("lastName");
        doctorLastname.setText(lastName);

        String phone = intent.getStringExtra("phone");
        doctorPhone.setText(phone);

        String dob = intent.getStringExtra("dob");
        doctorDob.setText(dob);

        String gender = intent.getStringExtra("gender");
        if(gender.equals("Male")){
            male.setChecked(true);
        }else if(gender.equals("Female")){
            female.setChecked(true);
        }else if(gender.equals("Other")){
            other.setChecked(true);
        }

        String specialties = intent.getStringExtra("specialty");
        List<String> specialty = Arrays.asList(getResources().getStringArray(R.array.specialty));

        ArrayAdapter<String> specialtyAdapter = new ArrayAdapter<>(EditDoctorsData.this,android.R.layout.simple_spinner_item,specialty);
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtySpinner.setAdapter(specialtyAdapter);

        for (int i = 0; i < specialty.size(); i++) {
            if (specialty.get(i).equals(specialties)){
                specialtySpinner.setSelection(i);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "DR."+doctorFirstname.getText().toString()+""+doctorLastname.getText().toString();
                String phone = doctorPhone.getText().toString();
                String dob = doctorDob.getText().toString();

                String newGender = "";
                if(male.isChecked() || female.isChecked() || other.isChecked()){
                    int selectedRadioButton = radioGroupGender.getCheckedRadioButtonId();
                    RadioButton checkedRadiobutton = findViewById(selectedRadioButton);
                    newGender = checkedRadiobutton.getText().toString();
                }else{
                    Toast.makeText(EditDoctorsData.this,"Select a Gender!!",Toast.LENGTH_LONG).show();
                }

                String specialty = specialtySpinner.getSelectedItem().toString();

                if(doctorFirstname.getText().toString().equals("") || doctorPhone.getText().toString().equals("") ||
                        phone.equals("") || dob.equals("") || gender.equals("") || specialty.equals("")){
                    Toast.makeText(EditDoctorsData.this,"Please Fill In All blanks!!",Toast.LENGTH_LONG).show();
                }else{
                    dbHandler.updateDoctorData(id,name,phone,dob,newGender,specialty);
                    Toast.makeText(EditDoctorsData.this,"Doctor Edited Successfully!!",Toast.LENGTH_LONG).show();
                }

            }
        });

        show_doctor_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDoctorsData.this, ShowDoctors.class);
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