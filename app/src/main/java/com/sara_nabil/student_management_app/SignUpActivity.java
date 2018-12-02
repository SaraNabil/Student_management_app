
package com.sara_nabil.student_management_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText name_et, email_et, pass_et, phone_et;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name_et = findViewById(R.id.name_signup_et);
        email_et = findViewById(R.id.email_signup_et);
        pass_et = findViewById(R.id.pass_signup_et);
        phone_et = findViewById(R.id.phone_signup_et);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void createNewAccount(View view) {
        final String email = email_et.getText().toString();
        final String password = pass_et.getText().toString();
        final String name = name_et.getText().toString();
        final String phone = phone_et.getText().toString();
        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(name)
                || !TextUtils.isEmpty(phone)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.e("create", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String id = user.getUid();
                                addUser(id, email, name, Integer.parseInt(phone));
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("create failed", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();

        }
    }

    private void addUser(String id, String email, String name, int phone) {
        UserDetailes userDetailes = new UserDetailes(name, email, phone);
        myRef.child(id).setValue(userDetailes);
    }
}
