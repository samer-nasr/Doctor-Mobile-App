package com.example.labfinals;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class ShowClinics extends Activity {
    TableLayout clinicsTable;
    DBHandler dbHandler;
    Button edit;
    String clinicName , clinicId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clinics);
        clinicName = "";
        clinicId = "";

        clinicsTable = (TableLayout) findViewById(R.id.clinic_table);
        dbHandler = new DBHandler(ShowClinics.this);
        edit = (Button) findViewById(R.id.edit_c_btn);
        edit.setBackground(getDrawable(R.color.gray));

        List<List> clinicsData = dbHandler.getAllClinicData();
        List<String> ids = clinicsData.get(0);
        List<String> names = clinicsData.get(1);

        for (int i = 0 ; i < ids.size() ; i++ ) {
            TableRow tableRow = new TableRow(this);

            TextView id = new TextView(ShowClinics.this);
            id.setText(ids.get(i));
            id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            id.setHeight(100);
            tableRow.addView(id);

            TextView name = new TextView(ShowClinics.this);
            name.setText(names.get(i));
            name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(name);

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j <= ids.size(); j++) {
                        clinicsTable.getChildAt(j).setBackground(getDrawable(R.color.white));
                    }
                    edit.setBackground(getDrawable(R.color.green));
                    tableRow.setBackground(getDrawable(R.color.gray));

                    clinicId = id.getText().toString();
                    clinicName = name.getText().toString();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ShowClinics.this,EditClinicsData.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id" , clinicId);
                    bundle.putString("name",clinicName);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            clinicsTable.addView(tableRow);
        }
    }
}