package com.example.labfinals;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShowDoctors extends Activity {
    TableLayout doctorsTable;
    DBHandler dbHandler;
    Button edit;
    String idNumber , firstName , lastName , phoneNumber , dobb , genderType , specialtyType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctors);

        idNumber = "" ;
        firstName = "";
        lastName = "";
        phoneNumber ="";
        dobb ="";
        genderType ="";
        specialtyType ="";

        doctorsTable = (TableLayout) findViewById(R.id.doctor_table);
        dbHandler = new DBHandler(ShowDoctors.this);
        edit = (Button) findViewById(R.id.edit_btn);
        edit.setBackground(getDrawable(R.color.gray));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowDoctors.this, EditDoctorsData.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", idNumber);
                bundle.putString("firstName", firstName);
                bundle.putString("lastName", lastName);
                bundle.putString("phone", phoneNumber);
                bundle.putString("dob", dobb);
                bundle.putString("gender", genderType);
                bundle.putString("specialty", specialtyType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        List<List> doctorsData = dbHandler.getAllDoctorsData();
        List<String> ids = doctorsData.get(0);
        List<String> names = doctorsData.get(1);
        List<String> phones = doctorsData.get(2);
        List<String> dobs = doctorsData.get(3);
        List<String> genders = doctorsData.get(4);
        List<String> specialties = doctorsData.get(5);

        for (int i = 0 ; i < ids.size() ; i++ ){
            TableRow tableRow = new TableRow(this);

            TextView id = new TextView(ShowDoctors.this);
            id.setText(ids.get(i));
            id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            id.setHeight(100);
            tableRow.addView(id);

            TextView name = new TextView(ShowDoctors.this);
            name.setText(names.get(i));
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(name);

            TextView phone = new TextView(ShowDoctors.this);
            phone.setText(phones.get(i));
            phone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(phone);

            TextView dob = new TextView(ShowDoctors.this);
            dob.setText(dobs.get(i));
            dob.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(dob);

            TextView gender = new TextView(ShowDoctors.this);
            gender.setText(genders.get(i));
            gender.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(gender);

            TextView specialty = new TextView(ShowDoctors.this);
            specialty.setText(specialties.get(i));
            specialty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(specialty);

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j <= ids.size(); j++) {
                        doctorsTable.getChildAt(j).setBackground(getDrawable(R.color.white));
                    }

                    edit.setBackground(getDrawable(R.color.green));
                    tableRow.setBackground(getDrawable(R.color.gray));

                    idNumber = id.getText().toString();
                    firstName = "";
                    lastName = "";
                    String separatedName = name.getText().toString().substring(3);
                    for(int i = 0 ; i < separatedName.length() ; i++){
                        String temp = separatedName.charAt(i)+"";
                        if(temp.equals(" ")){
                            lastName = separatedName.substring(i);
                            break;
                        }else{
                            firstName+=temp;
                        }
                    }

                    phoneNumber = phone.getText().toString();
                    dobb = dob.getText().toString();
                    genderType = gender.getText().toString();
                    specialtyType = specialty.getText().toString();
                }
            });

            doctorsTable.addView(tableRow);
        }
    }
}