package com.example.pharmacy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pharmacy.Model.User;
import com.example.pharmacy.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    private Button joinNowButton, loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterAcitivity.class);
                startActivity(intent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey!= "" && UserPasswordKey!= "" ){


            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){

                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Already logged In");
                loadingBar.setMessage("Please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }
    }


    private  void AllowAccess(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("User").child(phone).exists()){

                    User userData = dataSnapshot.child("Users").child(phone).getValue(User.class);

                    if(userData.getPhone().equals(phone)){


                        if(userData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "logged in Sucessfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();


                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentonlineUser = userData;
                            startActivity(intent);
                        }
                        else{

                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else{

                    Toast.makeText(MainActivity.this, "Account with this "+ phone + " don't exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
