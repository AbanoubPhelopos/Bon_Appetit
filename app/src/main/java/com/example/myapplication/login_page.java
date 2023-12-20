package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Classes.LoginHandler;
import com.example.myapplication.Classes.admin;
import com.example.myapplication.Classes.user;
import com.example.myapplication.fileParsers.adminRead;
import com.example.myapplication.fileParsers.adminWrite;
import com.example.myapplication.fileParsers.usersRead;
import com.example.myapplication.fileParsers.usersWrite;
import com.google.android.material.snackbar.Snackbar;
import com.example.myapplication.Database.database;

public class login_page extends AppCompatActivity implements View.OnClickListener {
    EditText emailEdit ,passwordEdit;
    TextView signupLink;
    Button btn;
    public void onClick(View v){
        if(v.getId()== R.id.buttonLogIn){
            Login();
        } else if(v.getId()==R.id.signupLink){
            Intent intent = new Intent(login_page.this, UserSignUpActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        btn = findViewById(R.id.buttonLogIn);
        btn.setOnClickListener(this);
        signupLink = findViewById(R.id.signupLink);
        signupLink.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        usersRead.parser(this);
        adminRead.parser(this);
    }
    public void Login(){
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        View parentLayout = findViewById(android.R.id.content);

        if(password.isEmpty() || email.isEmpty()){
            Snackbar.make(parentLayout, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
        }
        else{
            if(admin.Login(email,password)){
//                to know that an admin logged in to system
               admin tmpAdmin = database.adminSearch(email);
                LoginHandler.LOGIN(tmpAdmin);
                int index = -1;
                for( admin a: database.adminList){

                    if (email.equals(a.getEmail())){
                        index = database.adminList.indexOf(a);
                    }
                }
                adminWrite.addLoggedInAdmin(this,email);
                Intent intent = new Intent(login_page.this, AdminActivity.class);
                intent.putExtra("index",index);
                startActivity(intent);


            }
            else{

                if(user.Login(email,password)){
                    user tmpUser = database.userSearch(email);
                    LoginHandler.LOGIN(tmpUser);
                    usersWrite.addLoggedInUser(this,email);
                    Intent intent = new Intent(login_page.this, UserActivity.class);
                    intent.putExtra("UserId",tmpUser.getId());
                    startActivity(intent);

                }else{
                    Snackbar.make(parentLayout, "invalid credentials", Snackbar.LENGTH_SHORT).show();
                }
            }
        }

    }
}
