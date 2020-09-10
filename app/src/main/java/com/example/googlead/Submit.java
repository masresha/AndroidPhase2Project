package com.example.googlead;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Submit extends AppCompatActivity {
    public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse";
    //input element ids found from the live form page
    public static final String First_NameKEY="entry.1877115667";
    public static final String Last_NameKEY="entry.2006916086";
    public static final String Email_AddressKEY="entry.1824927963";
    public static final String Github_LinkKEY="entry.284483984";
    AlertDialog.Builder builder;

    private EditText first_name;
    private EditText last_name;
    private EditText email_address;
    private EditText github_link;
    Dialog showDialog;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://docs.google.com/forms/d/e/")
            .build();
    final QuestionsSpreadsheetWebService spreadsheetWebService = retrofit.create(QuestionsSpreadsheetWebService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        showDialog = new Dialog(this);


        Button sendButton = (Button)findViewById(R.id.form_submit);
        Button confirmedButton = (Button)findViewById(R.id.confirmed_btn);
        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText)findViewById(R.id.last_name);
        email_address = (EditText)findViewById(R.id.email_address);
        github_link = (EditText)findViewById(R.id.github_link);
        builder = new AlertDialog.Builder(this);
        sendButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       showConfirmation();
                    }
                });
    }
    private void showConfirmation() {
        showDialog.setContentView(R.layout.custom_popup_one);
        showDialog.show();
    }

    private void showError() {
        showDialog.setContentView(R.layout.custom_layoutthree);
        showDialog.show();
    }
    public void confirmedButton(View view) {
        String firstName = first_name.getText().toString();
        String lastName = last_name.getText().toString();
        String emailAddress = email_address.getText().toString();
        String githubLink = github_link.getText().toString();
        Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeQuestionnaire(firstName, lastName,emailAddress, githubLink);
        completeQuestionnaireCall.enqueue(callCallback);
        finish();
        showDialog.setContentView(R.layout.custom_popuptwo);
        showDialog.show();
    }

    private final Callback<Void> callCallback = new Callback<Void>() {

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
          //  showConfirmation();
            Log.d("XXX", "Submitted. " + response);

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

            Log.e("XXX", "Failed", t);
        }

    };



}
