package com.example.labfinals;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditPatientsData extends Activity {
    private TextView patientId , dob ,showData , title;
    private EditText firstName , lastName , phone , address;
    private RadioGroup radioGroupGender;
    private Button save ,backHome;
    private DBHandler dbHandler;
    private RadioButton male , female , other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        patientId = (TextView) findViewById(R.id.doctor_id_text);
        firstName = (EditText) findViewById(R.id.editTextDoctorFirstname);
        lastName = (EditText) findViewById(R.id.editTextDoctorLastname);
        phone = (EditText) findViewById(R.id.editTextDoctorPhone);
        dob = (TextView) findViewById(R.id.editTextDoctorDob);
        title = (TextView) findViewById(R.id.add_patient_title);
        radioGroupGender = (RadioGroup) findViewById(R.id.DoctorRadioGroupGender);
        male = (RadioButton) findViewById(R.id.DoctorRadiobuttonMale);
        female = (RadioButton) findViewById(R.id.DoctorRadiobuttonFemale);
        other = (RadioButton) findViewById(R.id.DoctorRadiobuttonOther);
        address = (EditText) findViewById(R.id.editTextPatientAddress);
        save = (Button) findViewById(R.id.add_doctor_button);
        backHome = (Button) findViewById(R.id.back_from_doctor_button);
        showData = (TextView) findViewById(R.id.show_patient_data);
        dbHandler = new DBHandler(EditPatientsData.this);

        title.setText("Edit Patient");
        save.setText("Save");

        // add data to the form //
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        patientId.setText(id);

        String fName = intent.getStringExtra("firstName");
        firstName.setText(fName);

        String lName = intent.getStringExtra("lastName");
        lastName.setText(lName);

        String phoneNumber = intent.getStringExtra("phone");
        phone.setText(phoneNumber);

        String DOB = intent.getStringExtra("dob");
        dob.setText(DOB);

        String genderType = intent.getStringExtra("gender");
        if(genderType.equals("Male")){
            male.setChecked(true);
        }else if(genderType.equals("Female")){
            female.setChecked(true);
        }else if(genderType.equals("Other")){
            other.setChecked(true);
        }

        String pAddress = intent.getStringExtra("address");
        address.setText(pAddress);

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPatientsData.this , ShowPatients.class);
                startActivity(intent);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPatientsData.this , HomePage.class);
                startActivity(intent);
            }
        });

       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String id = patientId.getText().toString();
               String fName = firstName.getText().toString();
               String lName = lastName.getText().toString();
               String phoneNumber = phone.getText().toString();
               String DOB = dob.getText().toString();

               String newGender = "";
               if(male.isChecked() || female.isChecked() || other.isChecked()){
                   int selectedRadioButton = radioGroupGender.getCheckedRadioButtonId();
                   RadioButton checkedRadiobutton = findViewById(selectedRadioButton);
                   newGender = checkedRadiobutton.getText().toString();
               }else{
                   Toast.makeText(EditPatientsData.this,"Select a Gender!!",Toast.LENGTH_LONG).show();
               }

               String pAddress = address.getText().toString();

               if(fName.equals("") || lName.equals("") || phoneNumber.equals("") || DOB.equals("") || newGender.equals("") || pAddress.equals("")){
                   Toast.makeText(EditPatientsData.this,"Please Fill In All blanks!!",Toast.LENGTH_LONG).show();
               }else{
                    dbHandler.updatePatientData(id,fName,lName,phoneNumber,DOB,newGender,pAddress);
                   Toast.makeText(EditPatientsData.this,"Patient Edited Successfully!!",Toast.LENGTH_LONG).show();
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
            dob.setText(selectedDate);
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}