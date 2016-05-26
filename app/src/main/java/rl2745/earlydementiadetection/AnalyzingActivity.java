package rl2745.earlydementiadetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Richard Lopez on 5/26/2016.
 */
public class AnalyzingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_analyzing);

        Intent intent = getIntent();
        String standardMessage = "Analyzing Habits - Run App in Background";
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(standardMessage);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.standardMessage);
        layout.addView(textView);
        backEndProcesses();


    }

    public void backEndProcesses(){
        //connect to backend here

    }

    /**Sends SMS message to doctor*/
    public void sendSMS(){
        //http://www.mkyong.com/android/how-to-send-sms-message-in-android/

        Intent intent = getIntent();
        String userName = intent.getStringExtra(LoginActivity.USER_NAME);
        String userDOB = intent.getStringExtra(LoginActivity.DOB);
        String doctorName = intent.getStringExtra(LoginActivity.DOCTOR_NAME);
        String doctorNumber = intent.getStringExtra(LoginActivity.DOCTOR_NUMBER);
        String sms = "Hello Dr. " + doctorName + ", your patient " + userName + " " +
                "whose date of birth is " + userDOB + " is suspected of having dementia " +
                "based on the anomalous behavior tracked on the Early Detection Dementia App.";


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(doctorNumber, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    /**
     * Called when the Back-End spots an anomaly
     */
    public void sendAnalyze(View view) throws Exception {

        //Intent to the next view
        Intent submitAnalyze = new Intent(this, NotificationActivity.class);
        startActivity(submitAnalyze);

    }
}