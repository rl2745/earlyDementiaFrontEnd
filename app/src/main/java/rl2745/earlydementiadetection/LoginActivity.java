package rl2745.earlydementiadetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    public final static String USER_NAME = "rl2745.earlydementiadetection.MY_NAME";
    public final static String DOCTOR_NAME = "rl2745.earlydementiadetection.DOC_NAME";
    public final static String DOB = "rl2745.earlydementiadetection.USER_DOB";
    public final static String DOCTOR_NUMBER = "rl2745.earlydementiadetection.DOC_NUMBER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Called when the user clicks the Send button
     */
    public void sendLogin(View view) throws Exception {
        //Intent to the next view
        Intent submitLogin = new Intent(this, ConfirmationActivity.class);


        //Get the text fields and store them as variables
        EditText userNameField = (EditText) findViewById(R.id.user_name_field);
        EditText userDOBField = (EditText) findViewById(R.id.user_dob_field);
        EditText doctorNameField = (EditText) findViewById(R.id.doctor_name_field);
        EditText doctorNumberField = (EditText) findViewById(R.id.doctor_number_field);
        String userName = userNameField != null ? userNameField.getText().toString() : null;
        String userDOB = userDOBField != null ? userDOBField.getText().toString() : null;
        String doctorName = doctorNameField != null ? doctorNameField.getText().toString() : null;
        String doctorNumber = doctorNumberField != null ? doctorNumberField.getText().toString() : null;

        
        submitLogin.putExtra(USER_NAME, userName);
        submitLogin.putExtra(DOCTOR_NAME, doctorName);
        submitLogin.putExtra(DOB, userDOB);
        submitLogin.putExtra(DOCTOR_NUMBER, doctorNumber);

        //Go to Confirmation Page
        startActivity(submitLogin);
        finish();
    }
}
