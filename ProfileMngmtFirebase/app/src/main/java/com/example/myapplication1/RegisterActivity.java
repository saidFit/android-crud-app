package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullName,email,phone,password,cofrmPassword;
    private ImageView buttonDatePicker;
    private Button register;
    private TextView login,datePickerSelected;
    private String datePicker,selectedValueOfRadio;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ProgressBar progressBar = findViewById(R.id.myProgressBar);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
        // EditTexts
        fullName = findViewById(R.id.fullNameEditText);
        email = findViewById(R.id.emailEditText);
        phone = findViewById(R.id.phoneEditText);
        password = findViewById(R.id.passwordEditText);
        cofrmPassword = findViewById(R.id.cnfmPasswordEditText);
        buttonDatePicker = findViewById(R.id.datePickerButton);
        datePickerSelected = findViewById(R.id.datePickerSelected);
        radioGroup = findViewById(R.id.radioGroup);

        // Buttons;
        register = findViewById(R.id.registerBtn);
        login    = findViewById(R.id.loginBtn);


         register.setOnClickListener(v -> {

             if(IsValidFullName(fullName.getText().toString()) && IsEmailValid(email.getText().toString()) && !datePicker.isEmpty() && !selectedValueOfRadio.isEmpty()
                && IsPasswordMatch(password.getText().toString(),cofrmPassword.getText().toString())
             ){
                 Toast.makeText(this, "successfully", Toast.LENGTH_SHORT).show();
                 Log.d("data", fullName.getText().toString() + email.getText().toString() + datePicker + selectedValueOfRadio + password.getText().toString() + phone.getText().toString());
             }else {
                 Toast.makeText(this,"something was warning!",Toast.LENGTH_SHORT).show();
             }
         });

         buttonDatePicker.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // Get the current date;
                 final Calendar calendar = Calendar.getInstance();
                 int year = calendar.get(Calendar.YEAR);
                 int month = calendar.get(Calendar.MONTH);
                 int dayOfMonth   = calendar.get(Calendar.DAY_OF_MONTH);

                 //Create a date pickerDialog;

                 DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener(){
                     @Override

                     public void onDateSet(DatePicker view,int selectedYear,int selectedMonth,int selectedDayOfMonth){
                         // Handle the selected date;
                         String selectedDate = selectedYear+"-"+(selectedMonth + 1)+"-"+selectedDayOfMonth;
                         datePickerSelected.setText(selectedDate);
                         datePicker = selectedDate;
                     }
                 },year,month,dayOfMonth);

                 // Show the DatePickerDialog
                 datePickerDialog.show();
             }
         });

         radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId){
                 RadioButton selectedButton = findViewById(checkedId);
                 if(selectedButton != null){
                     String selectedValue = selectedButton.getText().toString();
                     selectedValueOfRadio = selectedValue;
                 }
             }
         });

    }

    boolean IsValidFullName(String fullN){

        if(fullN != null && !fullN.isEmpty() && (fullN.length() >= 3 && fullN.length() <= 16)){

            for(char c : fullN.toCharArray()){

                if(!Character.isLetterOrDigit(c) && c != '_'){
                    fullName.setError("the full name should divide by underscore(_) and should have letter or digit !");
                    fullName.requestFocus();
                    return false;
                }

            }
            fullName.setError(null);
            fullName.requestFocus();
            return true;

        }
        fullName.setError("fullN shouldn't be empty and length grand than 3 and lees than 8 !");
        return false;

    }

    public boolean IsEmailValid(String emailChecked){

        if(emailChecked == null){
            email.setError("email shouldn't be empty !");
            email.requestFocus();
            return false;
        }

        int atSymbolOne = 0;

        for(int i = 0 ; i < emailChecked.length() ; i++){

            if(emailChecked.charAt(i) == '@'){
                atSymbolOne++;
            }

        }

        if(atSymbolOne != 1){
            if(atSymbolOne == 0) email.setError("email should have a @");
            else email.setError("email shouldn't have two or more @");
            email.requestFocus();
            return false;
        }

        boolean dotAfterSymbol = emailChecked.indexOf('@') >= emailChecked.length()-3;

        Log.d("length",dotAfterSymbol+ String.valueOf(emailChecked.charAt(emailChecked.length()-3)));

        if(dotAfterSymbol || emailChecked.startsWith(".") || emailChecked.startsWith("@") || emailChecked.endsWith("@") || emailChecked.endsWith(".")){
            email.setError("email shouldn't have at start '.' or '@'");
            email.requestFocus();
            return false;
        }
        return true;

    }

    public boolean IsPasswordMatch(String passwordChecked,String confmPassword){

        if(passwordChecked.length() <=3 && passwordChecked.length() >=10){
            password.setError("password should be grand of 3 and less than 10 !");
            password.requestFocus();
            return false;
        }
        if(!passwordChecked.matches(confmPassword)){
            cofrmPassword.setError("confirm password not match !");
            cofrmPassword.requestFocus();
            return false;
        }
        cofrmPassword.setError(null);
        cofrmPassword.requestFocus();
        return true;

    }
}