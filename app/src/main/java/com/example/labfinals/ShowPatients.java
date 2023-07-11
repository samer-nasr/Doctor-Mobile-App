package com.example.labfinals;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShowPatients extends Activity {
    TableLayout patientsTable;
    DBHandler dbHandler;
    Button edit;
    String idNumber , fName , lName , phoneNumber , DOB , genderType , pAddress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patients);

        idNumber = "" ;
        fName = "";
        lName = "";
        phoneNumber ="";
        DOB ="";
        genderType ="";
        pAddress ="";

        patientsTable = (TableLayout) findViewById(R.id.patient_table);
        dbHandler = new DBHandler(ShowPatients.this);
        edit = (Button) findViewById(R.id.edit_p_btn);
        edit.setBackground(getDrawable(R.color.gray));


        List<List> patientsData = dbHandler.getAllPatientsData();
        List<String> ids = patientsData.get(0);
        List<String> firstNames = patientsData.get(1);
        List<String> lastNames = patientsData.get(2);
        List<String> phones = patientsData.get(3);
        List<String> dobs = patientsData.get(4);
        List<String> genders = patientsData.get(5);
        List<String> addresses = patientsData.get(6);

        for(int i = 0 ; i < ids.size() ; i++){
            TableRow tableRow = new TableRow(this);

            TextView id = new TextView(ShowPatients.this);
            id.setText(ids.get(i));
            id.setHeight(100);
            id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(id);

            TextView firstName = new TextView(ShowPatients.this);
            firstName.setText(firstNames.get(i));
            firstName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(firstName);

            TextView lastName = new TextView(ShowPatients.this);
            lastName.setText(lastNames.get(i));
            lastName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(lastName);

            TextView phone = new TextView(ShowPatients.this);
            phone.setText(phones.get(i));
            phone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(phone);

            TextView dob = new TextView(ShowPatients.this);
            dob.setText(dobs.get(i));
            dob.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(dob);

            TextView gender = new TextView(ShowPatients.this);
            gender.setText(genders.get(i));
            gender.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(gender);

            TextView address = new TextView(ShowPatients.this);
            address.setText(addresses.get(i));
            address.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(address);

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j <= ids.size(); j++) {
                        patientsTable.getChildAt(j).setBackground(getDrawable(R.color.white));
                    }

                    edit.setBackground(getDrawable(R.color.green));
                    tableRow.setBackground(getDrawable(R.color.gray));
                    idNumber = id.getText().toString();
                    fName = firstName.getText().toString();
                    lName = lastName.getText().toString();
                    phoneNumber = phone.getText().toString();
                    DOB = dob.getText().toString();
                    genderType = gender.getText().toString();
                    pAddress = address.getText().toString();

                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ShowPatients.this,EditPatientsData.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",idNumber);
                    bundle.putString("firstName",fName);
                    bundle.putString("lastName",lName);
                    bundle.putString("phone",phoneNumber);
                    bundle.putString("dob",DOB);
                    bundle.putString("gender",genderType);
                    bundle.putString("address",pAddress);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            patientsTable.addView(tableRow);
        }
    }
}