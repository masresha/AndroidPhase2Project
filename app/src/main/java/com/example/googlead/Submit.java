package com.example.googlead;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/")
                .build();
        final QuestionsSpreadsheetWebService spreadsheetWebService = retrofit.create(QuestionsSpreadsheetWebService.class);

        Button sendButton = (Button)findViewById(R.id.form_submit);
        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText)findViewById(R.id.last_name);
        email_address = (EditText)findViewById(R.id.email_address);
        github_link = (EditText)findViewById(R.id.github_link);
        builder = new AlertDialog.Builder(this);
        sendButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Uncomment the below code to Set the message and title from the strings.xml file
                     //   builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                        //Setting message manually and performing action on button click
                        builder.setMessage("Do you want to close this application ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String firstName = first_name.getText().toString();
                                        String lastName = last_name.getText().toString();
                                        String emailAddress = email_address.getText().toString();
                                        String githubLink = github_link.getText().toString();

                                        Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeQuestionnaire(firstName, lastName,emailAddress, githubLink);
                                        completeQuestionnaireCall.enqueue(callCallback);

                                        finish();
                                       // Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        //        Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("AlertDialogExample");
                        alert.show();
                    }


                });

    }

    private final Callback<Void> callCallback = new Callback<Void>() {

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Log.d("XXX", "Submitted. " + response);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e("XXX", "Failed", t);
        }

    };



}
