package com.example.labfinals;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "doctorapplication.db";

    private static final int DB_VERSION = 1;

    //Patients Table name //
    private static final String PATIENT_TABLE = "Patient";
    //Patients Columns //
    private static final String PATIENT_ID_COL = "P_ID";
    private static final String PATIENT_FIRST_NAME_COL = "Firstname";
    private static final String PATIENT_LAST_NAME_COL = "Lastname";
    private static final String PATIENT_PHONE_COL = "Phone";
    private static final String PATIENT_DOB_COL = "Dob";
    private static final String PATIENT_GENDER_COL = "Gender";
    private static final String PATIENT_ADDRESS_COL = "Address";

    //Appointments Table name //
    private static final String Appointment_TABLE = "Appointment";
    //Appointments Columns //
    private static final String Appointment_ID_COL = "A_ID";
    private static final String Appointment_Patient_Id_COL = "PatientId";
    private static final String Appointment_PATIENT_NAME_COL = "PatientName";
    private static final String Appointment_Date_COL = "Date";
    private static final String Appointment_Time_COL = "Time";
    private static final String Doctor_COL = "Doctor";
    private static final String Clinic_COL = "Clinic";

    //Doctors Table Name//
    private static final String Doctor_TABLE = "Doctor";
    //Doctors Columns //
    private static final String Doctor_ID_COL = "D_ID";
    private static final String Doctor_NAME_COL = "Name";
    private static final String Doctor_PHONE_COL = "Phone";
    private static final String Doctor_DOB_COL = "Dob";
    private static final String Doctor_GENDER_COL = "Gender";
    private static final String Doctor_Speciality_COL = "Speciality";

    //Authorize Table Name //
    private static final String Authorize_Table = "Authorize";
    //Authorize Columns //
    private static final String Authorize_ID_COL = "ID";
    private static final String Authorize_username_COL = "UserName";
    private static final String Authorize_Password_COL = "Password";

    //Clinic Table Name //
    private static final String Clinic_Table = "Clinic";
    //Clinic Columns //
    private static final String Clinic_ID_COL = "ID";
    private static final String Clinic_Name_COL = "ClinicName";

    //constructor //
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Query to create patients table and add it to database //
        String patientQuery = "CREATE TABLE " + PATIENT_TABLE + " ("
                + PATIENT_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PATIENT_FIRST_NAME_COL + " TEXT,"
                + PATIENT_LAST_NAME_COL + " TEXT,"
                + PATIENT_PHONE_COL + " TEXT,"
                + PATIENT_DOB_COL + " TEXT,"
                + PATIENT_GENDER_COL + " TEXT, "
                + PATIENT_ADDRESS_COL + " TEXT)";
        // at last we are calling a exec sql
        // method to execute above sql patientTable
        db.execSQL(patientQuery);

        //create Query to create Doctors table and add it to database //
        String doctorQuery = "CREATE TABLE " + Doctor_TABLE + " ("
                + Doctor_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Doctor_NAME_COL + " TEXT,"
                + Doctor_PHONE_COL + " TEXT,"
                + Doctor_DOB_COL + " TEXT,"
                + Doctor_GENDER_COL + " TEXT,"
                + Doctor_Speciality_COL + " TEXT)";

        db.execSQL(doctorQuery);

        //create Query to create Authorize table and add it to database //
        String authorizeQuery = "CREATE TABLE " + Authorize_Table + " ("
                + Authorize_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Authorize_username_COL + " TEXT,"
                + Authorize_Password_COL + " TEXT)";

        db.execSQL(authorizeQuery);

        //create Query to create Clinic table and add it to database //
        String clinicQuery = " CREATE TABLE " + Clinic_Table + "("
                + Clinic_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Clinic_Name_COL + " TEXT)";

        db.execSQL(clinicQuery);

        String appointmentQuery = "CREATE TABLE " + Appointment_TABLE + " ("
                + Appointment_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Appointment_Patient_Id_COL + " TEXT,"
                + Appointment_PATIENT_NAME_COL + " TEXT,"
                + Appointment_Date_COL + " TEXT,"
                + Appointment_Time_COL + " TEXT,"
                + Doctor_COL + " TEXT,"
                + Clinic_COL + " TEXT)";

        db.execSQL(appointmentQuery);

    }

    public boolean validate(String username , String password){
        SQLiteDatabase db = this.getReadableDatabase();


        String sql = "SELECT * FROM " + Authorize_Table;
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                int usernameId = cursor.getColumnIndex(Authorize_username_COL);
                String usernameValue = cursor.getString(usernameId);


                int passwordId = cursor.getColumnIndex(Authorize_Password_COL);
                String passwordValue = cursor.getString(passwordId);

                if(usernameValue.equals(username) && passwordValue.equals(password)){
                    return true;
                }

            }while (cursor.moveToNext());
        }
       return false;
    }

    // Patients Functions //
    public void addNewPatient(String patient_f_name, String patient_l_name, String patient_phone, String patient_dob, String patient_gender, String patient_address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
            values.put(PATIENT_FIRST_NAME_COL, patient_f_name);
            values.put(PATIENT_LAST_NAME_COL, patient_l_name);
            values.put(PATIENT_PHONE_COL, patient_phone);
            values.put(PATIENT_DOB_COL, patient_dob);
            values.put(PATIENT_GENDER_COL, patient_gender);
            values.put(PATIENT_ADDRESS_COL, patient_address);

        db.insert(PATIENT_TABLE, null, values);
        db.close();
    }

    public List<List> getAllPatientsData() {
        List<List> dataList = new ArrayList<>();

        List<String> ids = new ArrayList<>();
        List<String> fNames = new ArrayList<>();
        List<String> lNames = new ArrayList<>();
        List<String> phones = new ArrayList<>();
        List<String> dobs = new ArrayList<>();
        List<String> genders = new ArrayList<>();
        List<String> addresses = new ArrayList<>();

        dataList.add(ids);
        dataList.add(fNames);
        dataList.add(lNames);
        dataList.add(phones);
        dataList.add(dobs);
        dataList.add(genders);
        dataList.add(addresses);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + PATIENT_TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(PATIENT_ID_COL));

                @SuppressLint("Range") String fName = cursor.getString(cursor.getColumnIndex(PATIENT_FIRST_NAME_COL));

                @SuppressLint("Range") String lName = cursor.getString(cursor.getColumnIndex(PATIENT_LAST_NAME_COL));

                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(PATIENT_PHONE_COL));

                @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex(PATIENT_DOB_COL));

                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(PATIENT_GENDER_COL));

                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(PATIENT_ADDRESS_COL));

                ids.add(id);
                fNames.add(fName);
                lNames.add(lName);
                phones.add(phone);
                dobs.add(dob);
                genders.add(gender);
                addresses.add(address);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataList;
    }

    public int lastPatientId(){
        List<List> patientData = getAllPatientsData();

        List<String> ids = patientData.get(0);
        int lastId = Integer.parseInt(ids.get(ids.size()-1));
        return lastId;
    }

    public int updatePatientData(String id , String fName , String lName , String phone , String dob , String gender , String address){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_FIRST_NAME_COL, fName);
        values.put(PATIENT_LAST_NAME_COL, lName);
        values.put(PATIENT_PHONE_COL, phone);
        values.put(PATIENT_DOB_COL, dob);
        values.put(PATIENT_GENDER_COL, gender);
        values.put(PATIENT_ADDRESS_COL, address);

        String whereClause = "P_ID = ?";
        String[] whereArgs = { id };

        int rowsAffected = db.update(PATIENT_TABLE, values, whereClause, whereArgs);

        db.close();
        return rowsAffected;
    }

    // Doctor Functions //
    public void addNewDoctor(String doctor_name, String doctor_phone, String doctor_dob, String doctor_gender, String doctor_speciality) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Doctor_NAME_COL, doctor_name);
        values.put(Doctor_PHONE_COL, doctor_phone);
        values.put(Doctor_DOB_COL, doctor_dob);
        values.put(Doctor_GENDER_COL, doctor_gender);
        values.put(Doctor_Speciality_COL, doctor_speciality);

        db.insert(Doctor_TABLE, null, values);
        db.close();
    }

    public List<List> getAllDoctorsData() {
        List<List> dataList = new ArrayList<>();

        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> phones = new ArrayList<>();
        List<String> dobs = new ArrayList<>();
        List<String> genders = new ArrayList<>();
        List<String> specialties = new ArrayList<>();

        dataList.add(ids);
        dataList.add(names);
        dataList.add(phones);
        dataList.add(dobs);
        dataList.add(genders);
        dataList.add(specialties);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Doctor_TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Doctor_ID_COL));

                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Doctor_NAME_COL));

                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(Doctor_PHONE_COL));

                @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex(Doctor_DOB_COL));

                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(Doctor_GENDER_COL));

                @SuppressLint("Range") String specialty = cursor.getString(cursor.getColumnIndex(Doctor_Speciality_COL));

                ids.add(id);
                names.add(name);
                phones.add(phone);
                dobs.add(dob);
                genders.add(gender);
                specialties.add(specialty);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataList;
    }

    public int lastDoctorId(){
        List<List> doctorData = getAllDoctorsData();

        List<String> ids = doctorData.get(0);
        int lastId = Integer.parseInt(ids.get(ids.size()-1));
        return lastId;
    }

    public int updateDoctorData(String id , String name , String phone , String dob , String gender , String specialty){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Doctor_NAME_COL, name);
        values.put(Doctor_PHONE_COL, phone);
        values.put(Doctor_DOB_COL, dob);
        values.put(Doctor_GENDER_COL, gender);
        values.put(Doctor_Speciality_COL, specialty);

        String whereClause = "D_ID = ?";
        String[] whereArgs = { id };

        int rowsAffected = db.update(Doctor_TABLE, values, whereClause, whereArgs);

        db.close();
        return rowsAffected;
    }


    //Clinic Functions //
    public void addNewClinic(String clinic_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Clinic_Name_COL, clinic_name);

        db.insert(Clinic_Table, null, values);
        db.close();
    }

    public List<List> getAllClinicData() {
        List<List> dataList = new ArrayList<>();

        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

        dataList.add(ids);
        dataList.add(names);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Clinic_Table;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Clinic_ID_COL));

                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Clinic_Name_COL));

                ids.add(id);
                names.add(name);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataList;
    }

    public int lastClinicId(){
        List<List> clinicData = getAllClinicData();

        List<String> ids = clinicData.get(0);
        int lastId = Integer.parseInt(ids.get(ids.size()-1));
        return lastId;
    }

    public int updateClinicData(String id , String name ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Clinic_Name_COL, name);

        String whereClause = "ID = ?";
        String[] whereArgs = { id };

        int rowsAffected = db.update(Clinic_Table, values, whereClause, whereArgs);

        db.close();
        return rowsAffected;
    }

    //Appointment Functions//
    public void addNewAppointment(String patient_id, String patient_name, String date, String time, String doctor, String clinic) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Appointment_Patient_Id_COL, patient_id);
        values.put(Appointment_PATIENT_NAME_COL, patient_name);
        values.put(Appointment_Date_COL, date);
        values.put(Appointment_Time_COL, time);
        values.put(Doctor_COL, doctor);
        values.put(Clinic_COL, clinic);

        db.insert(Appointment_TABLE, null, values);
        db.close();
    }

    public List<List> getAllAppointmentsData() {
        List<List> dataList = new ArrayList<>();

        List<String> ids = new ArrayList<>();
        List<String> patient_id = new ArrayList<>();
        List<String> patient_name = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> doctors = new ArrayList<>();
        List<String> clinics = new ArrayList<>();

        dataList.add(ids);
        dataList.add(patient_id);
        dataList.add(patient_name);
        dataList.add(dates);
        dataList.add(times);
        dataList.add(doctors);
        dataList.add(clinics);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Appointment_TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Appointment_ID_COL));

                @SuppressLint("Range") String p_id = cursor.getString(cursor.getColumnIndex(Appointment_Patient_Id_COL));

                @SuppressLint("Range") String p_name = cursor.getString(cursor.getColumnIndex(Appointment_PATIENT_NAME_COL));

                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(Appointment_Date_COL));

                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(Appointment_Time_COL));

                @SuppressLint("Range") String doctor = cursor.getString(cursor.getColumnIndex(Doctor_COL));

                @SuppressLint("Range") String clinic = cursor.getString(cursor.getColumnIndex(Clinic_COL));

                ids.add(id);
                patient_id.add(p_id);
                patient_name.add(p_name);
                dates.add(date);
                times.add(time);
                doctors.add(doctor);
                clinics.add(clinic);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataList;
    }

    public int lastAppointmentId(){
        List<List> appointmentData = getAllAppointmentsData();

        List<String> ids = appointmentData.get(0);
        int lastId = Integer.parseInt(ids.get(ids.size()-1));
        return lastId;
    }

    public int updateAppointmentData(String id , String pId , String pName , String date , String time , String doctor , String clinic){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Appointment_Patient_Id_COL, pId);
        values.put(Appointment_PATIENT_NAME_COL, pName);
        values.put(Appointment_Date_COL, date);
        values.put(Appointment_Time_COL, time);
        values.put(Doctor_COL, doctor);
        values.put(Clinic_COL, clinic);

        String whereClause = "A_ID = ?";
        String[] whereArgs = { id };

        int rowsAffected = db.update(Appointment_TABLE, values, whereClause, whereArgs);

        db.close();
        return rowsAffected;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
        onCreate(db);
    }
}