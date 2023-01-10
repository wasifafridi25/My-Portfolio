package com.example.myportfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnCreatePortfolio;
    private Button save;
    private EditText setName;
    private EditText setEmail;
    private EditText setProfessionalSummary;
    private EditText setWorkExperience;
    private EditText setEducation;
    private EditText volunteer;
    private EditText activity;
    private EditText language;
    private EditText skill;


    final DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settingUp();

    }

    private void settingUp(){
        setName = (EditText)findViewById(R.id.setName);
        setEmail = (EditText)findViewById(R.id.setEmail);
        setProfessionalSummary = (EditText)findViewById(R.id.professionalSummary);
        setWorkExperience = (EditText)findViewById(R.id.workExperience);
        setEducation = (EditText)findViewById(R.id.education);
        volunteer = (EditText)findViewById(R.id.volunteeringExperience);
        activity = (EditText)findViewById(R.id.extraCurricularActivities);
        skill = (EditText)findViewById(R.id.skills);
        language = (EditText)findViewById(R.id.languageProficiency);


        btnCreatePortfolio = (Button)findViewById(R.id.createPortfolio);

        btnCreatePortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!setName.getText().toString().isEmpty() && !setEmail.getText().toString().isEmpty() && !setProfessionalSummary.getText().toString().isEmpty()
                && !setEducation.getText().toString().isEmpty()) {
                    if (helper.insert(setName.getText().toString(), setEmail.getText().toString(),
                            setProfessionalSummary.getText().toString(),setWorkExperience.getText().toString(),setEducation.getText().toString(),
                            volunteer.getText().toString(),activity.getText().toString(),skill.getText().toString(),language.getText().toString() )) {
                        Toast.makeText(MainActivity.this, "Inserted to database", Toast.LENGTH_SHORT).show();
                        Intent portfolioIntent = new Intent(MainActivity.this, PortfolioActivity.class);
                        startActivity(portfolioIntent);
                    } else {
                        Toast.makeText(MainActivity.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    setName.setError("Enter Name");
                    setEmail.setError("Enter Email");
                    setProfessionalSummary.setError("Write about Professional Summary");
                    setEducation.setError("Mention your Education");

                }
                //Intent portfolioIntent = new Intent(MainActivity.this, PortfolioActivity.class);
                //startActivity(portfolioIntent);



                /*name = setName.getText().toString();
                email = setEmail.getText().toString();
                professionalSummary = setProfessionalSummary.getText().toString();
                workExperience = setWorkExperience.getText().toString();

                portfolioIntent.putExtra("name", name);
                portfolioIntent.putExtra("email", email);
                portfolioIntent.putExtra("profession",professionalSummary );
                portfolioIntent.putExtra("work", workExperience );*/


            }
        });
    }

}