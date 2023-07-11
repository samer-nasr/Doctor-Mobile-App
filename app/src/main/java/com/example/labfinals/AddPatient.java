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

public class AddPatient extends Activity {
    private TextView patientId , dob ,showData;
    private EditText firstName , lastName , phone , address;
    private RadioGroup radioGroupGender;
    private Button addPatientButton ,backHome;
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
        radioGroupGender = (RadioGroup) findViewById(R.id.DoctorRadioGroupGender);
        male = (RadioButton) findViewById(R.id.DoctorRadiobuttonMale);
        female = (RadioButton) findViewById(R.id.DoctorRadiobuttonFemale);
        other = (RadioButton) findViewById(R.id.DoctorRadiobuttonOther);
        address = (EditText) findViewById(R.id.editTextPatientAddress);
        addPatientButton = (Button) findViewById(R.id.add_doctor_button);
        backHome = (Button) findViewById(R.id.back_from_doctor_button);
        showData = (TextView) findViewById(R.id.show_patient_data);
        dbHandler = new DBHandler(AddPatient.this);

        //Add the last id to the form //
        try{
            patientId.setText(dbHandler.lastPatientId()+1+"");
        } catch(Exception e){
            patientId.setText("1");
        }

        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String phoneNumber = phone.getText().toString();
                String dateOfBirth = dob.getText().toString();

                String gender = "";
                if(male.isChecked() || female.isChecked() || other.isChecked()){
                    int selectedRadioButton = radioGroupGender.getCheckedRadioButtonId();
                    RadioButton checkedRadiobutton = findViewById(selectedRadioButton);
                    gender = checkedRadiobutton.getText().toString();
                }else{
                    Toast.makeText(AddPatient.this,"Select a Gender!!",Toast.LENGTH_LONG).show();
                }

                String patientAddress = address.getText().toString();

                if(fName.equals("") || lName.equals("") || phoneNumber.equals("") || gender.equals("") || dateOfBirth.equals("")  || patientAddress.equals("")){
                    Toast.makeText(AddPatient.this,"Please Fill In All Blanks!!",Toast.LENGTH_LONG).show();
                }else{
                    dbHandler.addNewPatient(fName,lName,phoneNumber,dateOfBirth,gender,patientAddress);
                    Toast.makeText(AddPatient.this,"Patient Added Successfully!!",Toast.LENGTH_LONG).show();

                    //reset controls //
                    firstName.setText("");
                    lastName.setText("");
                    phone.setText("");
                    dob.setText("");
                    radioGroupGender.clearCheck();
                    address.setText("");
                    patientId.setText(dbHandler.lastPatientId()+1+"");
                }
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPatient.this , ShowPatients.class);
                startActivity(intent);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPatient.this , HomePage.class);
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
            dob.setText(selectedDate);
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
