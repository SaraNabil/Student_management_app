package com.sara_nabil.student_management_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText id_editText;
    EditText name_editText;
    EditText grade_editText;
    DB_helper db;

    FirebaseUser user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            startActivity(new Intent(MainActivity.this,SignInActivity.class));
        }
        setContentView(R.layout.activity_main);
        id_editText = (EditText) findViewById(R.id.student_no_editText);
        name_editText = (EditText) findViewById(R.id.student_name_editText);
        grade_editText = (EditText) findViewById(R.id.student_grade_editText);
        db = new DB_helper(this);
    }

    public void add_student(View view) {
        String id = id_editText.getText().toString();
        String name = name_editText.getText().toString();
        String grade = grade_editText.getText().toString();
        if (!TextUtils.isEmpty(id) || !TextUtils.isEmpty(name) || !TextUtils.isEmpty(grade)) {
            boolean isInserted = db.insert(Integer.parseInt(id), name, Integer.parseInt(grade));
            if (isInserted == true) {
                showMessage("The Management Application", "Inserted done");
                clear();
            }

            if (isInserted == false) {
                showMessage("The Management Application", "error");

            }
        } else {
            showMessage("Error", "please fill all fields");
        }


    }

    public void delete_student(View view) {
        String id = id_editText.getText().toString();
        if (!TextUtils.isEmpty(id)) {
            db.delete(Integer.parseInt(id));
            id_editText.setText("");
        } else {
            showMessage("Error", "please enter the student's id");
        }

    }

    public void modify_student(View view) {
        String id = id_editText.getText().toString();
        String name = name_editText.getText().toString();
        String grade = grade_editText.getText().toString();
        if (!TextUtils.isEmpty(id) || !TextUtils.isEmpty(name) || !TextUtils.isEmpty(grade)) {
            db.update(Integer.parseInt(id), name, Integer.parseInt(grade));
            clear();
        } else {
            showMessage("Error", "please fill all fields");
        }

    }

    public void view_student(View view) {
        String id = id_editText.getText().toString();
        if (!TextUtils.isEmpty(id)) {
            Cursor cursor = db.getData(Integer.parseInt(id_editText.getText().toString()));
            if (cursor.moveToFirst()) {
                name_editText.setText(cursor.getString(1));
                grade_editText.setText(cursor.getString(2));
            }
        } else {
            showMessage("Error", "please enter the student's id");

        }

    }

    public void view_all(View view) {
        Cursor cursor = db.getAllData();
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append("ID : " + cursor.getString(0) + " - ");
            buffer.append("Name : " + cursor.getString(1) + " - ");
            buffer.append("Grade : " + cursor.getString(2) + " \n ");

        }
        showMessage("Student details", buffer.toString());

    }

    public void more(View view) {
        showMessage("Student Management Application", "Developed By Sara Nabil");
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clear() {
        id_editText.setText("");
        name_editText.setText("");
        grade_editText.setText("");
    }

    public void Sign_Out(View view) {
        startActivity(new Intent(MainActivity.this,SignInActivity.class));
    }
}
