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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emale_et ;
    EditText pass_et ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);
        emale_et = findViewById(R.id.email_signin_et);
        pass_et = findViewById(R.id.pass_signin_et);
        mAuth = FirebaseAuth.getInstance();
    }

    public void openSignUpActivity(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
        Animatoo.animateZoom(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideLeft(this);
    }

    public void signIn(View view) {
        String email = emale_et.getText().toString();
        String password = pass_et.getText().toString();
        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.e("Sign in done", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("Sign in failed", "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            Toast.makeText(SignInActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
